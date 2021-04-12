/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.QuestionWrapper;
import exception.QuestionWrapperNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hp
 */
@Stateless
public class QuestionSessionBean implements QuestionSessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;

    public QuestionSessionBean() {
    }

    @Override
    public QuestionWrapper retrieveQuestionWrapperByTempId(Long qwTempId) throws QuestionWrapperNotFoundException {
        Query query = em.createQuery("SELECT qw FROM QuestionWrapper qw WHERE qw.tempId = :inId");
        query.setParameter("inId", qwTempId);

        try {
            QuestionWrapper questionWrapper = (QuestionWrapper) query.getSingleResult();
            questionWrapper.getCheckbox().size();
            questionWrapper.getMcq().size();
            return questionWrapper;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new QuestionWrapperNotFoundException("Question Wrapper with id " + qwTempId + " does not exist!");
        }
    }

}
