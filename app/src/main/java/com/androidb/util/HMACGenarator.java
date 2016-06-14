package com.androidb.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACGenarator {

    public static String getHMackey(String myKey, String test) {

        try {
            String macKey = "";
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(
                    myKey.getBytes(), "HmacSHA1");
            mac.init(secret);
            byte[] digest = mac.doFinal(test.getBytes());
            for (byte b : digest) {
                macKey += String.format("%02x", b);
            }
            return macKey;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
