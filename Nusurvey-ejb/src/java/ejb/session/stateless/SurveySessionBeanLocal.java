/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Survey;
import entity.User;
import exception.UnsupportedDeleteSurveyException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hp
 */
@Local
public interface SurveySessionBeanLocal {
    public List<Survey> searchSurveysByTitle(String searchString);
    public List<Survey> sortSurveysByPrice();
    public List<Survey> filterSurveysByTags(List<Long> tagIds, String condition);

    public User createSurvey(Survey newSurvey);

    public List<Survey> retrieveMyFilledSurveys(User currUser);

    public List<Survey> retrieveMyCreatedSurveys(User currUser);

    public List<Survey> retrieveAllSurveys();

    public void closeSurvey(Survey survey);

    public void deleteSurvey(Survey survey) throws UnsupportedDeleteSurveyException;
    
}
