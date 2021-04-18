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
import enumeration.TransactionType;
import exception.SurveyNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author miche
 */
@Stateless
public class ResponseSessionBean implements ResponseSessionBeanLocal {


    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;
    
    

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;

    
    public ResponseSessionBean() {
    }

    @Override
    public long createResponse(SurveyResponse newResponse) {
        System.out.println("responsetest" + newResponse);
        newResponse.getAnswerWrappers().size();
        List<AnswerWrapper> answerWrappers = newResponse.getAnswerWrappers();
        for (AnswerWrapper a : answerWrappers) {
//            QuestionOption optionPicked = a.getOption();
//            QuestionOption optionPickedPersisted = em.find(QuestionOption.class, optionPicked.getOptionId());
//            a.setOption(optionPickedPersisted);
            if (a.getCheckboxAnswer() != null) {
                em.persist(a.getCheckboxAnswer());
            } else if (a.getMultipleChoiceAnswer() != null) {
                em.persist(a.getMultipleChoiceAnswer());
            } else if (a.getSliderAnswer() != null) {
                em.persist(a.getSliderAnswer());
            } else if (a.getTextAnswer() != null) {
                em.persist(a.getTextAnswer());
            }

            QuestionWrapper questionWrapperPersisted = em.find(QuestionWrapper.class, a.getQuestionWrapperId());
            a.setQuestionWrapper(questionWrapperPersisted);
            em.persist(a);
            questionWrapperPersisted.getAnswerWrappers().add(a);
        }

        Survey survey = newResponse.getSurvey();
        Survey surveyPersisted = em.find(Survey.class, survey.getSurveyId());
        newResponse.setSurvey(surveyPersisted);
        surveyPersisted.getResponses().add(newResponse);

        User surveyee = newResponse.getSurveyee();
        User surveyeePersisted = em.find(User.class, surveyee.getUserId());
        newResponse.setSurveyee(surveyeePersisted);
        surveyeePersisted.getResponses().add(newResponse);
        
        em.persist(newResponse);
        em.flush();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        try 
        {
            transactionSessionBean.createNewTransaction(surveyeePersisted.getCreditCard(), surveyPersisted.getPrice_per_response(), TransactionType.INCOME, surveyPersisted.getSurveyId(),localDate.toString());
        } catch (SurveyNotFoundException exc)
        {
            try {
                throw new SurveyNotFoundException("Cannot find the survey");
            } catch (SurveyNotFoundException ex) {
                Logger.getLogger(ResponseSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return newResponse.getId();
    }
}
