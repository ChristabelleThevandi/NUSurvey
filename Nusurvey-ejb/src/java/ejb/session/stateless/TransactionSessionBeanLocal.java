/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Transaction;
import entity.User;
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
    
}
