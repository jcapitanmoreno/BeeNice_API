package com.github.jcapitanmoreno.beenice_api.config;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final int SALT_LENGTH = 16;

    public static String hashPassword(String password) {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hash = hashWithSalt(password, saltBase64);
        return saltBase64 + ":" + hash;
    }

    public static boolean checkPassword(String password, String stored) {
        String[] parts = stored.split(":");
        if (parts.length != 2) return false;
        String salt = parts[0];
        String hash = parts[1];
        return hashWithSalt(password, salt).equals(hash);
    }

    private static String hashWithSalt(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashed = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}