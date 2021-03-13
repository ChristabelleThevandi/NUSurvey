/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Survey;
import exception.UnsupportedDeleteSurveyException;
import javax.ejb.Local;

/**
 *
 * @author hp
 */
@Local
public interface QuestionSessionBeanLocal {

    public void deleteSurvey(Survey survey) throws UnsupportedDeleteSurveyException;

    public void closeSurvey(Survey survey);
    
}
