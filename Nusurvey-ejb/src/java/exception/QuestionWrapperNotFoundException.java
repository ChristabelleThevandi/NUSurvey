/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author miche
 */
public class QuestionWrapperNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>QuestionWrapperNotFoundException</code>
     * without detail message.
     */
    public QuestionWrapperNotFoundException() {
    }

    /**
     * Constructs an instance of <code>QuestionWrapperNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public QuestionWrapperNotFoundException(String msg) {
        super(msg);
    }
}
