/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author USER
 */
public class CreditCardErrorException extends Exception {

    /**
     * Creates a new instance of <code>CreditCardErrorException</code> without
     * detail message.
     */
    public CreditCardErrorException() {
    }

    /**
     * Constructs an instance of <code>CreditCardErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreditCardErrorException(String msg) {
        super(msg);
    }
}
