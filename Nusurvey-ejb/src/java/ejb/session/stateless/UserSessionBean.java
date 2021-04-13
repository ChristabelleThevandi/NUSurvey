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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
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

    @EJB
    private CreditCardSessionBeanLocal creditCardSessionBean;

    public UserSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public User login(String email, String password) throws InvalidLoginCredentialException {
        try {
            User user = retrieveUserByEmail(email);

            if (user.getPassword().equals(password)) {
                user.setLoggedIn(true);
                return user;
            } else {
                throw new InvalidLoginCredentialException("Invalid password!");
            }
        } catch (UserNotFoundException ex) {
            throw new InvalidLoginCredentialException("Email does not exist!");
        }
    }

    @Override
    public void logout(User user) {
        user.setLoggedIn(false);
    }

    @Override
    public User retrieveUserByEmail(String email) throws UserNotFoundException {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :inEmail");
        query.setParameter("inEmail", email);

        try {
            User user = (User) query.getSingleResult();
            user.getTags().size();
            user.getTransactions().size();
            user.getSurveyTaken().size();
            return user;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new UserNotFoundException("User with email " + email + " does not exist!");
        }
    }

    @Override
    public User register(User newUser) throws EmailExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(newUser);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newUser);
                em.flush();

                return newUser;

            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new EmailExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<User>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    @Override
    public void changePassword(User user, String password) {
        Long userId = user.getUserId();
        User currentUser = em.find(User.class, userId);
        currentUser.setPassword(password);
    }

    @Override
    public void createProfile(User user) throws UserNotFoundException {
        try {
            User currentUser = retrieveUserByEmail(user.getEmail());

            currentUser.setFirst_name(user.getFirst_name());
            currentUser.setLast_name(user.getLast_name());
            currentUser.setBirth_date(user.getBirth_date());
            currentUser.setFaculty(user.getFaculty());
            currentUser.setMajor(user.getMajor());
            currentUser.setGender(user.getGender());
        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }

    @Override
    public void updateProfile(User user) throws UserNotFoundException {
        try {
            User currentUser = retrieveUserByEmail(user.getEmail());

            currentUser.setFirst_name(user.getFirst_name());
            currentUser.setLast_name(user.getLast_name());
            currentUser.setGender(user.getGender());
        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }

    @Override
    public void uploadAvatar(User user, String avatar) throws UserNotFoundException {
        try {
            User currentUser = retrieveUserByEmail(user.getEmail());
            currentUser.setAvatar(avatar);
        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }

    @Override
    public User removeAvatar(String email) throws UserNotFoundException {
        try {
            User currentUser = retrieveUserByEmail(email);
            currentUser.setAvatar(null);

            return currentUser;
        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException("User with email " + email + " does not exist!");
        }
    }

    @Override
    public User addCreditCard(User user, CreditCard creditCard) throws CreditCardErrorException, UserNotFoundException {
        try {
            User currentUser = retrieveUserByEmail(user.getEmail());
            if (currentUser.getCreditCard() == null) {
                CreditCard newCreditCard;
                newCreditCard = creditCardSessionBean.createCreditCard(creditCard);
                currentUser.setCreditCard(newCreditCard);
                newCreditCard.setUser(currentUser);
            }

            return currentUser;
        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }

    @Override
    public void updateCreditCard(User user, CreditCard creditCard) throws CreditCardErrorException, UserNotFoundException {
        try {
            User currentUser = retrieveUserByEmail(user.getEmail());
            /* if (currentUser.getCreditCard().getCard_number().equals(creditCard.getCard_number()))
            {
                throw new CreditCardErrorException("Credit card with card number: " + creditCard.getCard_number() + " has already exists.");
            }
            else 
            {*/
            creditCardSessionBean.removeCreditCard(currentUser);
            CreditCard newCreditCard = creditCardSessionBean.retrieveCreditCardByCardId(creditCard.getCreditCardId());
            newCreditCard.setBalance(creditCard.getBalance());
            newCreditCard.setCard_number(creditCard.getCard_number());
            newCreditCard.setCvv(creditCard.getCvv());
            newCreditCard.setExpiry_date(creditCard.getExpiry_date());
            newCreditCard.setName(creditCard.getName());
            currentUser.setCreditCard(newCreditCard);

        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }

    /**
     *
     * @param user
     * @param tags
     * @throws UserNotFoundException
     */
    @Override
    public void updateTag(User user, List<Tag> tags) throws UserNotFoundException {
        try {
            User currentUser = retrieveUserByEmail(user.getEmail());
            currentUser.setTags(tags);
        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }

    @Override
    public List<Survey> getRecommendation(User user) {
        Long userId = user.getUserId();
        User currentUser = em.find(User.class, userId);
        List<Survey> recommendationSurvey = new ArrayList<>();

        Query query = em.createQuery("SELECT s FROM Survey s WHERE s.surveyOpen = TRUE ORDER BY s.expiry_date DESC");
        List<Survey> temp = query.getResultList();
        for (Survey s : temp) {
            if (!s.getSurveyees().contains(user) && s.getCreator() != user) {
                recommendationSurvey.add(s);
            }
        }
        return recommendationSurvey;
    }

    @Override
    public boolean verifyEmail(String email) throws UserNotFoundException {
        try {
            User user = retrieveUserByEmail(email);
            return false;
        } catch (UserNotFoundException ex) {
            String[] emailSplit = email.split("@");

            if (emailSplit.length != 2) {
                return false;
            }

            if (emailSplit[1].equals("u.nus.edu")) {
                return true;
            } else {
                throw new UserNotFoundException("Please use NUS email to register");
            }
        }
    }

    @Override
    public List<Tag> retrieveUserTags(User user) throws UserNotFoundException {
        try {
            String email = user.getEmail();
            User currUser = retrieveUserByEmail(email);
            return currUser.getTags();
        } catch (UserNotFoundException exc) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " does not exist!");
        }
    }
}
