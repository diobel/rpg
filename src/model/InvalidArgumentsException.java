/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Custom exception class with parametric constructor
 *
 * @author Michał Szubert
 * @version 1.0
 */
public class InvalidArgumentsException extends Exception {

    /**
     * Default constructor
     */
    public InvalidArgumentsException() {

    }

    /**
     * Parametric constructor
     * @param message - String with error message
     */
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
