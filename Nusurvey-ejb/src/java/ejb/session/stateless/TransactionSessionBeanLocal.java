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
import exception.SurveyNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hp
 */
@Local
public interface TransactionSessionBeanLocal {

    public List<Transaction> retrieveMyIncomeTransaction(User user);

    public List<Transaction> retrieveMyExpenseTransaction(User user);

    public void paySurvey(User user, Survey survey) throws SurveyNotFoundException;

    public void giveReward(Survey survey) throws SurveyNotFoundException;

    public void receiveIncentive(User user) throws SurveyNotFoundException;

    public void createNewTransaction(CreditCard card, Double amount, TransactionType type, Long surveyId, String date) throws SurveyNotFoundException;

}
