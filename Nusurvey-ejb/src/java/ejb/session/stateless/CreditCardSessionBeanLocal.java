/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCard;
import entity.User;
import exception.CreditCardErrorException;
import exception.UserNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author hp
 */
@Local
public interface CreditCardSessionBeanLocal {

    public CreditCard createCreditCard(CreditCard creditCard);

    public void removeCreditCard(User user) throws UserNotFoundException;
    public CreditCard retrieveCreditCardByCardId(Long cardId) throws CreditCardErrorException;
    
}
