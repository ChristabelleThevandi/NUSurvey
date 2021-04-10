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
import exception.CreditCardErrorException;
import exception.SurveyNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

    @EJB(name = "CreditCardSessionBeanLocal")
    private CreditCardSessionBeanLocal creditCardSessionBeanLocal;

    @EJB(name = "SurveySessionBeanLocal")
    private SurveySessionBeanLocal surveySessionBeanLocal;

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;

    public TransactionSessionBean() {
    }

    @Override
    public void createNewTransaction(CreditCard card, Double amount, TransactionType transactionType, Long surveyId, String date) throws SurveyNotFoundException {
        try {
            Survey currSurvey = surveySessionBeanLocal.retrieveSurveyBySurveyId(surveyId);
            card = creditCardSessionBeanLocal.retrieveCreditCardByCardId(card.getCreditCardId());
            User user = card.getUser();
            card = user.getCreditCard();
            Double initialBalance = card.getBalance();
            Double nextBalance = 0.0;

            if (transactionType == TransactionType.EXPENSE) {
                System.out.println("INITIAL BALANCE: " + card.getBalance());
                System.out.println("SURVEY ID: " + currSurvey.getSurveyId());
                nextBalance = initialBalance - amount;
            } else {
                nextBalance = initialBalance + amount;
            }

            Transaction transaction = new Transaction();
            transaction.setUser(user);
            transaction.setCreditCard(card);
            card.getTransactions().add(transaction);
            transaction.setType(transactionType);
            transaction.setAmount(amount);
            transaction.setSurvey(currSurvey);
            transaction.setTransaction_date(date);

            card.setBalance(nextBalance);
            em.persist(transaction);

        } catch (SurveyNotFoundException ex) {
            throw ex;
        } catch (CreditCardErrorException ex) {
            Logger.getLogger(TransactionSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Transaction> retrieveMyIncomeTransaction(User user) {
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.user=inUser AND t.type=inType");
        query.setParameter("inUser", user);
        query.setParameter("inType,", TransactionType.INCOME);

        return query.getResultList();
    }

    @Override
    public List<Transaction> retrieveMyExpenseTransaction(User user) {
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.user=inUser AND t.type=inType");
        query.setParameter("inUser", user);
        query.setParameter("inType,", TransactionType.EXPENSE);

        return query.getResultList();
    }

    @Override
    public void paySurvey(User user, Survey survey) throws SurveyNotFoundException {
        LocalDateTime timeLocal = LocalDateTime.now();
        DateTimeFormatter timeLocalFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String finalDate = timeLocal.format(timeLocalFormat);
        
        CreditCard card = user.getCreditCard();
        card.getTransactions().size();

        Double amount = survey.getPrice_per_response() * survey.getSurveyees().size();
        amount += survey.getReward() * survey.getSurveyees().size();

        String title = "Payment for survey " + survey.getTitle();

        try {
            createNewTransaction(card, amount, TransactionType.EXPENSE, survey.getSurveyId(), finalDate);
        } catch (SurveyNotFoundException ex) {
            throw ex;
        }
    }

    @Override
    public void giveReward(Survey survey) throws SurveyNotFoundException {
        LocalDateTime timeLocal = LocalDateTime.now();
        DateTimeFormatter timeLocalFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String finalDate = timeLocal.format(timeLocalFormat);
        
        List<User> surveyees = survey.getSurveyees();
        surveyees.size();
        Double amount = survey.getReward();
        String title = "Reward from filling survey " + survey.getTitle();

        for (User surveyee : surveyees) {
            CreditCard card = surveyee.getCreditCard();
            try {
                createNewTransaction(card, amount, TransactionType.INCOME, survey.getSurveyId(), finalDate);
            } catch (SurveyNotFoundException ex) {
                throw ex;
            }
        }
    }

    @Override
    public void receiveIncentive(User user) throws SurveyNotFoundException {
        LocalDateTime timeLocal = LocalDateTime.now();
        DateTimeFormatter timeLocalFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String finalDate = timeLocal.format(timeLocalFormat);

        List<Transaction> transactions = retrieveMyExpenseTransaction(user);
        Double total = 0.0;

        for (Transaction transaction : transactions) {
            total += transaction.getAmount();
        }

        if (total >= user.getMilestone()) {
//            String title = "Congratulations for reaching the milestone!";
            CreditCard card = user.getCreditCard();
            try {
                createNewTransaction(card, user.getIncentive(), TransactionType.INCOME, -1L, finalDate);
            } catch (SurveyNotFoundException ex) {
                throw ex;
            }

            user.setMilestone(user.getMilestone() + 100.0);
            user.setIncentive(user.getIncentive() + 5.0);
        }
    }
}
