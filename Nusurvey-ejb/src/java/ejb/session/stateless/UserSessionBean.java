/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.User;
import exception.EmailExistException;
import exception.InputDataValidationException;
import exception.InvalidLoginCredentialException;
import exception.UnknownPersistenceException;
import exception.UserNotFoundException;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author Chrisya
 */
@Stateless
public class UserSessionBean implements UserSessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public UserSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public User login (String email, String password) throws InvalidLoginCredentialException {
        try
        {
            User user = retrieveUserByEmail(email);
            
            if(user.getPassword().equals(password))
            {
                user.getSurveyTaken().size();
                user.setLoggedIn(true);
                return user;
            }
            else
            {
                throw new InvalidLoginCredentialException("Invalid password!");
            }
        }
        catch(UserNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Email does not exist!");
        }
    }
    
    @Override
    public void logout(User user) {
        user.setLoggedIn(false);
    }
    
    @Override
    public User retrieveUserByEmail(String email) throws UserNotFoundException{
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :inEmail");
        query.setParameter("inEmail", email);
        
        try
        {
            return (User)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new UserNotFoundException("User with email " + email + " does not exist!");
        }
    }
    
    @Override
    public User register(User newUser) throws EmailExistException, UnknownPersistenceException, InputDataValidationException{
        Set<ConstraintViolation<User>>constraintViolations = validator.validate(newUser);
        
        if(constraintViolations.isEmpty())
        {
            try
            {
                em.persist(newUser);
                em.flush();

                return newUser;
            }
            catch(PersistenceException ex)
            {
                if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new EmailExistException();
                    }
                    else
                    {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<User>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
    @Override
    public void changePassword(User user,String password)
    {
        user.setPassword(password);
    }
}
