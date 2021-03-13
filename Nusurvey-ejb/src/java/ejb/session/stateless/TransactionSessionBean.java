/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Transaction;
import entity.User;
import enumeration.TransactionType;
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
public class TransactionSessionBean implements TransactionSessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;

    public TransactionSessionBean() {
    }

    @Override
    public List<Transaction> retrieveMyIncomeTransaction(User user){
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.user=inUser AND t.type=inType");
        query.setParameter("inUser", user);
        query.setParameter("inType,", TransactionType.INCOME);
        
        return query.getResultList();
    }
    
    public List<Transaction> retrieveMyExpenseTransaction(User user){
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.user=inUser AND t.type=inType");
        query.setParameter("inUser", user);
        query.setParameter("inType,", TransactionType.EXPENSE);
        
        return query.getResultList();
    }
    
}
