/**
 * SecurityUtils Class
 *
 * Purpose:
 * Initially used for providing cryptographic utilities within the application, specifically for hashing passwords with SHA-256.
 * This class was crucial for ensuring password security when the application utilized an SQLite database, helping to safeguard
 * user data by preventing passwords from being stored in plaintext.
 *
 * Features:
 * - SHA-256 Hashing: Implements SHA-256 to securely hash passwords, providing a non-reversible, fixed-size hash.
 *   This method is essential for protecting passwords against various security threats, including brute force attacks.
 *
 * Evolution of Security Measures:
 * Although effective during the initial stages of the application, the SecurityUtils class was phased out as the application
 * transitioned to a more secure and robust user authentication system using MySQL and bcrypt. Bcrypt enhances security by
 * being computationally intensive and automatically incorporating salt to defend against rainbow table attacks.
 * This shift was part of a broader enhancement to address growing security needs and implement industry-standard practices.
 *
 * Usage:
 * The class was utilized in earlier versions of the application where security practices necessitated an in-house solution
 * for password management. It is included in the current codebase as a historical reference to demonstrate the application’s
 * commitment to evolving security practices and adapting to enhanced security measures.
 *
 * Author: Jared Semonin
 * Date: 04/21/2024
 * Version: 2.0
 *
package com.semonin.jjwarehouse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SecurityUtils {

    **
     * Hashing Using SHA-256
     *
     * @param passwordToHash The password to hash.
     * @return the hashed password as a hex string, or null if hashing failed
     *
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
 */
