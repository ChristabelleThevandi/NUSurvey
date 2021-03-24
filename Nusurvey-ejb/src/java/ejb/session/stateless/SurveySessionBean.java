/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Option;
import entity.Question;
import entity.Survey;
import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
