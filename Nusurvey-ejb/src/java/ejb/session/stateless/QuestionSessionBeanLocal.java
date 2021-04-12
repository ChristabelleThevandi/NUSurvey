/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.QuestionWrapper;
import entity.Survey;
import entity.User;
import exception.QuestionWrapperNotFoundException;
import exception.UnsupportedDeleteSurveyException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hp
 */
@Local
public interface QuestionSessionBeanLocal {

    public QuestionWrapper retrieveQuestionWrapperByTempId(Long qwTempId) throws QuestionWrapperNotFoundException;

}
