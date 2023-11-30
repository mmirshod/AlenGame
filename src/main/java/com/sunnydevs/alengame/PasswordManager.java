package com.sunnydevs.alengame;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManager {
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append(0);
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm is not available");
        }
    }

    public static boolean checkPassword(String password, String passwordHash) {
        String inputPasswordHash = hashPassword(password);

        // Compare the hashed inputted password with the stored hashed password
        return inputPasswordHash.equals(passwordHash);
    }
}
