package com.moviz.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encode passwords in a secure way using SHA512
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class PasswordEncoder {

    /**
     * Encode a given plain password and return the hashed value as a string
     *
     * @param plainPassword The plain password
     * @return The secure hash
     */
    public String encode(String plainPassword) {
        try {
            MessageDigest encoder = MessageDigest.getInstance("SHA-512");

            encoder.update(plainPassword.getBytes());
            byte[] bytes = encoder.digest();

            StringBuilder builder = new StringBuilder();

            for (byte b : bytes) {
                builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            return plainPassword;
        }
    }

}
