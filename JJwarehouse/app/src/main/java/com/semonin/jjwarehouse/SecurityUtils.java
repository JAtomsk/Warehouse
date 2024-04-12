/**
 * SecurityUtils
 *
 * Purpose:
 * Provides cryptographic services, specifically offering a secure method for hashing passwords. This class is essential
 * for ensuring that passwords are handled securely within the application, adhering to industry standards by using the
 * SHA-256 hashing algorithm.
 *
 * Features:
 * - SHA-256 Hashing: Employs SHA-256 to ensure passwords are securely hashed before storage or verification. This
 *   method produces a fixed-size hash (256 bits) which is non-reversible, adding a layer of security against password
 *   cracking attempts.
 *
 * How it Works:
 * - Converts a plaintext password into a byte array.
 * - Uses Java's MessageDigest to apply SHA-256 hashing.
 * - Transforms the resulting hash byte array into a readable hexadecimal format.
 *
 * Meeting Course Outcome:
 * Demonstrates the practical implementation of secure coding practices, specifically in the context of user authentication
 * and data protection. By employing SHA-256, the class meets industry-specific goals of maintaining data integrity and
 * security.
 *
 * Reflecting on the Enhancement Process:
 * The introduction of SHA-256 hashing was crucial for improving the application's security posture. It required an
 * understanding of Java's cryptographic API and careful consideration of how security impacts the overall application
 * architecture. Implementing this function was a key step in ensuring that all user passwords are handled in a manner
 * that prevents direct access to sensitive data, thus upholding best practices for data security.
 *
 * Author: Jared Semonin
 * Date: 04/11/2024
 * Version: 2.0
 */

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
            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Execute the hashing
            byte[] encodedhash = digest.digest(passwordToHash.getBytes());

            // Build a string in hexadecimal format to encode the hash byte array
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();// Exception handling to catch unsupported algorithms
            return null; // Indicates failure in hashing
        }
    }
}
