/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.common;

/**
 *
 * @author Aleksandar
 */
public class BatchException extends Exception {

    public BatchException(Exception e) {
        super(e);
    }

    public BatchException() {

    }
}
