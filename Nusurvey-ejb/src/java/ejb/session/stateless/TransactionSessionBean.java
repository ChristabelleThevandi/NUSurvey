/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCard;
import entity.Survey;
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
    public void createNewTransaction(CreditCard card, Double amount, TransactionType type, String title) {
        User user = card.getUser();
        Double initialBalance = card.getBalance();
        Double nextBalance = 0.0;
        
        if(type.equals(TransactionType.EXPENSE)) {
            nextBalance = initialBalance - amount;
        } else {
            nextBalance = initialBalance + amount;
        }
        
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCreditCard(card);
        card.getTransactions().add(transaction);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setTitle(title);
        
        card.setBalance(nextBalance);
        em.persist(transaction);
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
    
    @Override
    public void paySurvey(User user, Survey survey) {
        CreditCard card  = user.getCreditCard();
        card.getTransactions().size();
        
        Double amount = survey.getPrice_per_response() * survey.getSurveyees().size();
        amount += survey.getReward() * survey.getSurveyees().size();
        
        String title = "Payment for survey " + survey.getTitle();
        
        createNewTransaction(card, amount, TransactionType.EXPENSE, title);
    }
    
    @Override
    public void giveReward(Survey survey) {
        List<User> surveyees = survey.getSurveyees();
        surveyees.size();
        Double amount = survey.getReward();
        String title = "Reward from filling survey " + survey.getTitle();
        
        for(User surveyee:surveyees) {
            CreditCard card = surveyee.getCreditCard();
            createNewTransaction(card, amount, TransactionType.INCOME, title);
        }
    }
    
    @Override
    public void receiveIncentive(User user) {
        
        List<Transaction> transactions = retrieveMyExpenseTransaction(user);
        Double total = 0.0;
        
        for(Transaction transaction:transactions) {
            total += transaction.getAmount();
        }
        
        if(total >= user.getMilestone()) {
            String title = "Congratulations for reaching the milestone!";
            CreditCard card = user.getCreditCard();
            createNewTransaction(card, user.getIncentive(), TransactionType.INCOME, title);
            
            user.setMilestone(user.getMilestone() + 100.0);
            user.setIncentive(user.getIncentive() + 5.0);
        }
    }
}
