package com.semonin.jjwarehouse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SecurityUtils {

    /**
     * Hashing Using SHA-256
     *
     * @param passwordToHash The password to hash.
     * @return the hashed password as a hex string, or null if hashing failed
     */

    public static String hashPassword(String passwordToHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(passwordToHash.getBytes());

            // Convert byte array into sugnum representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; //
        }
    }
}
