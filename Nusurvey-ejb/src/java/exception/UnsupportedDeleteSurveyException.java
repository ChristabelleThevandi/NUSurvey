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
public class UnsupportedDeleteSurveyException extends Exception {

    /**
     * Creates a new instance of <code>UnsupportedDeleteSurveyException</code>
     * without detail message.
     */
    public UnsupportedDeleteSurveyException() {
    }

    /**
     * Constructs an instance of <code>UnsupportedDeleteSurveyException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UnsupportedDeleteSurveyException(String msg) {
        super(msg);
    }
}
