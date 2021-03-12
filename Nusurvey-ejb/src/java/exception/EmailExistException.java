/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Chrisya
 */
public class EmailExistException extends Exception {

    /**
     * Creates a new instance of <code>EmailExistException</code> without detail
     * message.
     */
    public EmailExistException() {
    }

    /**
     * Constructs an instance of <code>EmailExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmailExistException(String msg) {
        super(msg);
    }
}
