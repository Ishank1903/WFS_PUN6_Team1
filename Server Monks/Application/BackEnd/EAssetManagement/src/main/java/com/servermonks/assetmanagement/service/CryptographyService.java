package com.servermonks.assetmanagement.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.servermonks.assetmanagement.exceptions.InternalServerError;

public interface CryptographyService {
	public static String encryptString(String input) throws InternalServerError {
		
		return generateSHA256Hash(generateSHA256Hash(input)); // get the hash value of (hash value of input)
		
	}
	
	
	private static String generateSHA256Hash(String input) throws InternalServerError {
        try {
            // Create a SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] inputBytes = input.getBytes();

            digest.update(inputBytes);

            // Compute the hash
            byte[] hashBytes = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerError("202: Internal Server Error");
            
        }
    }
	
}
