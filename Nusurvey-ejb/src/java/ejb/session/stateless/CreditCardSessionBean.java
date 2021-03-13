/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCard;
import entity.User;
import exception.UserNotFoundException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public CreditCard createCreditCard(CreditCard creditCard)
    {
        em.persist(creditCard);
        em.flush();
        
        return creditCard;
    }
    
    @Override
    public void removeCreditCard(User user) throws UserNotFoundException
    {
        try 
        {
            User currentUser = userSessionBean.retrieveUserByEmail(user.getEmail());
            CreditCard creditCard = currentUser.getCreditCard();
            creditCard.setUser(null);
            currentUser.setCreditCard(null);
            em.remove(creditCard);        
        }
        catch (UserNotFoundException exc)
        {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }
}
