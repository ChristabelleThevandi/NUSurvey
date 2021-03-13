/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Answer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hp
 */
@Stateless
public class AnswerSessionBean implements AnswerSessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;

    public AnswerSessionBean() {
    }
    
    public Long createAnswer(Answer newAnswer) {
        em.persist(newAnswer);
        em.flush();
        return newAnswer.getAnswerId();
    }
    
}
