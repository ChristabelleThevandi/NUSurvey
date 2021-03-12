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
    
}
