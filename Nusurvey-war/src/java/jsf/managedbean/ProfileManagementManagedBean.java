/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CreditCardSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.CreditCard;
import entity.User;
import enumeration.GenderType;
import exception.CreditCardErrorException;
import exception.UserNotFoundException;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author USER
 */
@Named(value = "viewProfileManagedBean")
@ViewScoped
public class ProfileManagementManagedBean implements Serializable {

    @EJB(name = "CreditCardSessionBeanLocal")
    private CreditCardSessionBeanLocal creditCardSessionBeanLocal;

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    //User Profile
    private String first_name;
    private String last_name;
    private GenderType gender;
    private User selectedUserToUpdate;
    private String placeHolderName;
    private String placeHolderLastName;
    private String placeHolderGender;
    private Integer genderInt;

    //Credit Card
    private CreditCard creditCard;
    private String nameOnCard;
    private String cardNumber;
    private String cvv;
    private Date expiryDate;
    
    public ProfileManagementManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        selectedUserToUpdate = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        setPlaceHolderName(selectedUserToUpdate.getFirst_name());
        setPlaceHolderLastName(selectedUserToUpdate.getLast_name());
        setPlaceHolderGender(selectedUserToUpdate.getGender().toString());
    }
    
    public void doUpdateProfile(ActionEvent event) {
        System.out.println("Selected this button");
    }
    
    public void updateProfile(ActionEvent event) {
        try {
            System.out.println("Pressed the button for update");
            
            if (getGenderInt() == 0) {
                getSelectedUserToUpdate().setGender(GenderType.MALE);
            } else if (getGenderInt() == 1) {
                getSelectedUserToUpdate().setGender(GenderType.FEMALE);
            } else if (getGenderInt() == 2) {
                getSelectedUserToUpdate().setGender(GenderType.OTHERS);
            }
            
            userSessionBeanLocal.updateProfile(getSelectedUserToUpdate());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
        } catch (UserNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found exception has occured: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void doAddCreditCard(ActionEvent event) {
        creditCard = new CreditCard(nameOnCard, cardNumber, cvv, expiryDate);
        creditCardSessionBeanLocal.createCreditCard(creditCard);
        try {
            userSessionBeanLocal.addCreditCard(selectedUserToUpdate, creditCard);
        }catch (UserNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found exception has occured: " + ex.getMessage(), null));
        }catch (CreditCardErrorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Card exception has occured: " + ex.getMessage(), null));
        }
    }
    
    public void updateCreditCard(ActionEvent event) {
        
    }

    public GenderType getGender() {
        return gender;
    }
    
    public void setGender(GenderType gender) {
        this.gender = gender;
    }
    
    public User getSelectedUserToUpdate() {
        return selectedUserToUpdate;
    }
    
    public void setSelectedUserToUpdate(User selectedUserToUpdate) {
        this.selectedUserToUpdate = selectedUserToUpdate;
    }
    
    public CreditCard getCreditCard() {
        return creditCard;
    }
    
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
    
    public String getNameOnCard() {
        return nameOnCard;
    }
    
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCvv() {
        return cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    public Date getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the placeHolderName
     */
    public String getPlaceHolderName() {
        return placeHolderName;
    }

    /**
     * @param placeHolderName the placeHolderName to set
     */
    public void setPlaceHolderName(String placeHolderName) {
        this.placeHolderName = placeHolderName;
    }

    /**
     * @return the placeHolderLastName
     */
    public String getPlaceHolderLastName() {
        return placeHolderLastName;
    }

    /**
     * @param placeHolderLastName the placeHolderLastName to set
     */
    public void setPlaceHolderLastName(String placeHolderLastName) {
        this.placeHolderLastName = placeHolderLastName;
    }

    /**
     * @return the placeHolderGender
     */
    public String getPlaceHolderGender() {
        return placeHolderGender;
    }

    /**
     * @param placeHolderGender the placeHolderGender to set
     */
    public void setPlaceHolderGender(String placeHolderGender) {
        this.placeHolderGender = placeHolderGender;
    }
    
    public Integer getGenderInt() {
        return genderInt;
    }
    
    public void setGenderInt(Integer genderInt) {
        this.genderInt = genderInt;
    }
}
