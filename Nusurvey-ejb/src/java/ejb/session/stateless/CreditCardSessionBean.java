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
import javax.ejb.EJB;
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
public class CreditCardSessionBean implements CreditCardSessionBeanLocal {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;

    public CreditCardSessionBean() {
    }
    
    @Override
    public CreditCard retrieveCreditCardByCardId(Long cardId) throws CreditCardErrorException{
        Query query = em.createQuery("SELECT c FROM CreidtCard c WHERE c.creditCardId = :inNumber");
        query.setParameter("inNumber", cardId);
        
        try
        {
            return (CreditCard)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CreditCardErrorException("Card with number " + cardId + " does not exist!");
        }
    }
    @Override
    public CreditCard createCreditCard(CreditCard creditCard)
    {
        em.persist(creditCard);
        em.flush();
        
        return creditCard;
    }
    
    @Override
    public User removeCreditCard(User user) throws UserNotFoundException
    {
        try 
        {
            User currentUser = userSessionBean.retrieveUserByEmail(user.getEmail());
            CreditCard creditCard = currentUser.getCreditCard();
            creditCard.setUser(null);
            currentUser.setCreditCard(null);
            //em.remove(creditCard);   
            
            return currentUser;
        }
        catch (UserNotFoundException exc)
        {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }
}
