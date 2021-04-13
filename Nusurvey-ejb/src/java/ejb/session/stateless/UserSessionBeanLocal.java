/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCard;
import entity.Survey;
import entity.Tag;
import entity.User;
import exception.CreditCardErrorException;
import exception.EmailExistException;
import exception.InputDataValidationException;
import exception.InvalidLoginCredentialException;
import exception.UnknownPersistenceException;
import exception.UserNotFoundException;
import java.io.File;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Chrisya
 */
@Local
public interface UserSessionBeanLocal {

    public User login(String username, String password) throws InvalidLoginCredentialException;

    public void logout(User user);

    public User retrieveUserByEmail(String email) throws UserNotFoundException;

    public User register(User newUser) throws EmailExistException, UnknownPersistenceException, InputDataValidationException;

    public void changePassword(User user, String password);

    public void createProfile(User user) throws UserNotFoundException;

    public void updateProfile(User user) throws UserNotFoundException;

    public void uploadAvatar(User user, String avatar) throws UserNotFoundException;

    public User addCreditCard(User user, CreditCard creditCard) throws CreditCardErrorException, UserNotFoundException;
    public void updateCreditCard(User user, CreditCard creditCard) throws CreditCardErrorException, UserNotFoundException;

    public void updateTag(User user, List<Tag> tags) throws UserNotFoundException;

    public List<Survey> getRecommendation(User user);
    public User removeAvatar(String email) throws UserNotFoundException;

    public boolean verifyEmail(String email) throws UserNotFoundException;

    public List<Tag> retrieveUserTags(User user) throws UserNotFoundException;
    
}
