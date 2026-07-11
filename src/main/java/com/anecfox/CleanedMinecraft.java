package com.anecfox;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import static com.anecfox.GenderUtilities.TAG_FEMALE;
import static com.anecfox.GenderUtilities.TAG_MALE;

public class CleanedMinecraft implements ModInitializer {

    public static String MOD_ID = "cleaned-minecraft";

    private static final ResourceKey<LootTable> DESERT_PYRAMID_ARCHAEOLOGY_KEY =
            ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("archaeology/desert_pyramid"));

    @Override
    public void onInitialize() {
        UseEntityCallback.EVENT.register((player, level, hand, entity, _) -> {
            if (level.isClientSide()) {
                return InteractionResult.PASS;
            }

            if (player.getItemInHand(hand).is(Items.COMPASS) && entity instanceof AgeableMob) {
                Component message;

                if (entity.entityTags().contains(TAG_MALE)) {
                    message = Component.translatable("message.cleaned_minecraft.gender_male");
                } else if (entity.entityTags().contains(TAG_FEMALE)) {
                    message = Component.translatable("message.cleaned_minecraft.gender_female");
                } else {
                    message = Component.translatable("message.cleaned_minecraft.gender_tag_not_found");
                }
                player.sendOverlayMessage(message);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });

        UseEntityCallback.EVENT.register((player, level, hand, entity, _) -> {
            if (level.isClientSide()) {
                return InteractionResult.PASS;
            }

            if (!(player.getItemInHand(hand).is(Items.BUCKET) || player.getItemInHand(hand).is(Items.BOWL))) {
                return InteractionResult.PASS;
            }

            if (entity.getType() == EntityTypes.COW || entity.getType() == EntityTypes.GOAT || entity.getType() == EntityTypes.MOOSHROOM) {
                if (entity.entityTags().contains(TAG_MALE)) {
                    var message = Component.translatable("message.cleaned_minecraft.on_trying_get_milk_from_" + (
                            entity.getType() == EntityTypes.GOAT ?
                                    "male_goat" :
                                    "bull"));
                    player.sendOverlayMessage(message);

                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.containerMenu.broadcastChanges();

                        serverPlayer.getInventory().setChanged();
                        serverPlayer.containerMenu.sendAllDataToRemote();
                    }
                    return InteractionResult.FAIL;
                }
            }

            return InteractionResult.PASS;
        });

        LootTableEvents.MODIFY.register((key, tableBuilder, _, _) -> {
            if (DESERT_PYRAMID_ARCHAEOLOGY_KEY.equals(key)) {
                tableBuilder.modifyPools(pool -> {
                    var netheriteUpgradeSmithingTemplateEntry = LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                            .setWeight(2)
                            .build();

                    pool.add(netheriteUpgradeSmithingTemplateEntry);
                });
            }
        });

        BiomeModifications.addFeature(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_HOT_OVERWORLD),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(
                        Registries.PLACED_FEATURE,
                        Identifier.fromNamespaceAndPath(MOD_ID, "ancient_debris_ore")
                )
        );

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            GameRules gameRules = server.getGameRules();

            if (gameRules.get(GameRules.ALLOW_ENTERING_NETHER_USING_PORTALS)) {
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "gamerule allow_entering_nether_using_portals false"
                );
            }
        });
    }
}
