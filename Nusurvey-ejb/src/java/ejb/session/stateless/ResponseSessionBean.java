/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Answer;
import entity.Option;
import entity.Question;
import entity.Response;
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
    
    public long createResponse(Response newResponse) {
        newResponse.getAnswers().size();
        List<Answer> answers = newResponse.getAnswers();
        for (Answer a: answers) {
            Option optionPicked = a.getOption();
            Option optionPickedPersisted = em.find(Option.class, optionPicked.getOptionId());
            a.setOption(optionPickedPersisted);
            
            Question questionPicked = a.getQuestion();
            Question questionPickedPersisted = em.find(Question.class, questionPicked.getQuestionId());
            a.setQuestion(questionPickedPersisted);
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
