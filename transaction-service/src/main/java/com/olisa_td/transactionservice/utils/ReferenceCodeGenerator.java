package com.olisa_td.transactionservice.utils;

import java.security.SecureRandom;

public class ReferenceCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();


    public static String RefCode(int length) {
        StringBuilder refCode = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            refCode.append(CHARACTERS.charAt(index));
        }
        return refCode.toString();
    }
}
