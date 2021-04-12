/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AnswerWrapper;
import entity.Question;
import entity.QuestionWrapper;
import entity.SurveyResponse;
import entity.Survey;
import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author miche
 */
@Stateless
public class ResponseSessionBean implements ResponseSessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;

    public ResponseSessionBean() {
    }
    
    @Override
    public long createResponse(SurveyResponse newResponse) {
        newResponse.getAnswerWrappers().size();
        List<AnswerWrapper> answerWrappers = newResponse.getAnswerWrappers();
        for (AnswerWrapper a: answerWrappers) {
//            QuestionOption optionPicked = a.getOption();
//            QuestionOption optionPickedPersisted = em.find(QuestionOption.class, optionPicked.getOptionId());
//            a.setOption(optionPickedPersisted);
            
            QuestionWrapper questionWrapper = a.getQuestionWrapper();
            QuestionWrapper questionWrapperPersisted = em.find(QuestionWrapper.class, questionWrapper.getId());
            a.setQuestionWrapper(questionWrapperPersisted);
            em.persist(a);
        }
        
        Survey survey = newResponse.getSurvey();
        Survey surveyPersisted = em.find(Survey.class, survey.getSurveyId());
        newResponse.setSurvey(surveyPersisted);
        
        User surveyee = newResponse.getSurveyee();
        User surveyeePersisted = em.find(User.class, surveyee.getUserId());
        newResponse.setSurveyee(surveyeePersisted);
        
        em.persist(newResponse);
        em.flush();
        
        return newResponse.getId();
    }
}
