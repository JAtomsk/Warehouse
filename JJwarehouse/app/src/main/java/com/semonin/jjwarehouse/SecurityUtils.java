/**
 * SecurityUtils
 *
 * Purpose:
 * Provides security-related utility functions, including password hashing. This class is crucial for the secure handling
 * of passwords within the application, ensuring that passwords are not stored in plaintext and are instead hashed using
 * SHA-256, a secure cryptographic hash function.
 *
 * Features:
 * - SHA-256 Hashing: Utilizes the SHA-256 algorithm to convert plain text passwords into a secure hashed format,
 *   making it computationally not capable to reverse the hash back to the original password. This enhances the security
 *   of stored user credentials against potential data breaches.
 *
 * Meeting Course Outcome:
 * This class directly supports the course outcome of developing a security mindset by demonstrating best practices in
 * protecting user data and employing secure coding standards. It showcases:
 * - Implementation of advanced cryptographic techniques for password security.
 * - A proactive approach to mitigating vulnerabilities associated with storing sensitive user information.
 *
 * Reflecting on the Enhancement Process:
 * Incorporating SHA-256 password hashing represented a significant step in enhancing the application's security.
 * This process involved understanding cryptographic principles and the Java security API, demonstrating the ability to
 * apply  knowledge in practical, real-world software development scenarios. The challenge of integrating
 * secure hashing mechanisms emphasized the importance of continuous learning and applying innovative techniques in
 * computing practices to achieve industry-specific goals.
 *
 * Author: [Jared Semonin]
 * Date: [03/31/2024]
 * Version: 1.0
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
            // Perform the hashing
            byte[] encodedhash = digest.digest(passwordToHash.getBytes());

            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Log and handle exceptio
            e.printStackTrace();
            return null; // Return null on hashing failure
        }
    }
}
