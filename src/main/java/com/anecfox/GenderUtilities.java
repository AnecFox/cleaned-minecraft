package com.anecfox;

import java.util.Random;

public class GenderUtilities {

    public static final String TAG_MALE = "male";
    public static final String TAG_FEMALE = "female";

    public static String getRandomGenderForMob() {
        var random = new Random();
        return random.nextBoolean() ? TAG_MALE : TAG_FEMALE;
    }

    public static boolean containsGenderTag(String entityTags) {
        return entityTags.contains(TAG_MALE) || entityTags.contains(TAG_FEMALE);
    }
}
