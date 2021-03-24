/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Option;
import entity.Question;
import entity.Survey;
import entity.Tag;
import entity.User;
import exception.UnsupportedDeleteSurveyException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hp
 */
@Stateless
public class SurveySessionBean implements SurveySessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;

    public SurveySessionBean() {
    } 

    public Long createSurvey(Survey newSurvey) {
        User creator = newSurvey.getCreator();
        User creatorPersisted = em.find(User.class, creator.getUserId());
        newSurvey.setCreator(creatorPersisted);

        newSurvey.getQuestions().size();
        List<Question> questions = newSurvey.getQuestions();
        for (Question q : questions) {
            q.getOptions().size();
            List<Option> options = q.getOptions();
            for (Option o : options) {
                em.persist(o);
            }
            em.persist(q);
        }
        em.persist(newSurvey);
        em.flush();
        return newSurvey.getSurveyId();
    }
    
    public void deleteSurvey(Survey survey) throws UnsupportedDeleteSurveyException {
        survey = em.find(Survey.class, survey.getSurveyId());
        if (!survey.getSurveyees().isEmpty()) {
            throw new UnsupportedDeleteSurveyException("Cannot delete survey that has been answered!");
        }
        
        survey.getQuestions().size();
        List<Question> questions = survey.getQuestions();
        for (Question q: questions) {
            q.getOptions().size();
            List<Option> options = q.getOptions();
            for (Option o: options) {
                em.remove(o);
            }
            em.remove(q);
        }
        
        survey.getTags().size();
        List<Tag> tags = survey.getTags();
        for (Tag t: tags) {
            t.getSurveys().size();
            t.getSurveys().remove(survey);
        }
        
        survey.getTags().size();
        tags = survey.getTags();
        for (Tag t: tags) {
            survey.getTags().remove(t);
        }
        
        em.remove(survey);
    }
    
    public void closeSurvey(Survey survey) {
        survey = em.find(Survey.class, survey.getSurveyId());
        survey.setOpen(false);
    }
    
    public List<Survey> retrieveAllSurveys() {
        Query query = em.createQuery("SELECT s FROM Survey s");
        List<Survey> surveys = query.getResultList();
        
        return surveys;
    }
    
    public List<Survey> retrieveMyCreatedSurveys(User currUser) {
        currUser = em.find(User.class, currUser.getUserId());
        Query query = em.createQuery("SELECT s FROM Survey s WHERE currUser IN s.surveyees");
        List<Survey> surveys = query.getResultList();
        
        return surveys;
    }
    
    public List<Survey> retrieveMyFilledSurveys(User currUser) {
        currUser = em.find(User.class, currUser.getUserId());
        Query query = em.createQuery("SELECT s FROM Survey s WHERE s.creator := cUser");
        query.setParameter("cUser", currUser);
        List<Survey> surveys = query.getResultList();
        
        return surveys;
    }
}
