/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lpro.control;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Nicolas
 */
public class PasswordManagement {
    
    public static String digestPassword(String plainTextPassword) {
        try {
            String hashed = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
            System.out.println(">>>> hashed : " + hashed);
            return hashed;
        } catch (Exception e) {
            throw new RuntimeException("Pb hashing mot de passe", e);
        }
    }
    
}
