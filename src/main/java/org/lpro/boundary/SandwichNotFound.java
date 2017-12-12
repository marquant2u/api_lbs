/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lpro.boundary;

/**
 *
 * @author Nicolas
 */
public class SandwichNotFound  extends RuntimeException {
    public SandwichNotFound(String s) {
        super(s);
    }
}
