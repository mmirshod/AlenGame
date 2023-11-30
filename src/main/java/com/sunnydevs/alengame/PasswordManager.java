package com.sunnydevs.alengame;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for managing passwords.
 */
public class PasswordManager {

    /**
     * Hashes the given password using SHA-256 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password.
     */
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

    /**
     * Checks if the provided password matches the stored hashed password.
     *
     * @param password      The inputted password.
     * @param passwordHash  The stored hashed password.
     * @return True if the passwords match, false otherwise.
     */
    public static boolean checkPassword(String password, String passwordHash) {
        String inputPasswordHash = hashPassword(password);

        // Compare the hashed inputted password with the stored hashed password
        return inputPasswordHash.equals(passwordHash);
    }
}
