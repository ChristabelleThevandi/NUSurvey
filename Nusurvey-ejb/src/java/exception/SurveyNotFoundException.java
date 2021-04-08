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
public class SurveyNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SurveyNotFoundException</code> without
     * detail message.
     */
    public SurveyNotFoundException() {
    }

    /**
     * Constructs an instance of <code>SurveyNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SurveyNotFoundException(String msg) {
        super(msg);
    }
}
