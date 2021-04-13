/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CreditCardSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.CreditCard;
import entity.Tag;
import entity.User;
import enumeration.GenderType;
import exception.CreditCardErrorException;
import exception.UserNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author USER
 */
@Named(value = "viewProfileManagedBean")
@ViewScoped
public class ProfileManagementManagedBean implements Serializable {

    @EJB
    private TagSessionBeanLocal tagSessionBean;

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
    private Boolean hasAvatar;

    //Credit Card
    private CreditCard creditCard;
    private String nameOnCard;
    private String cardNumber;
    private String cvv;
    private Date expiryDate;
    private String path;

    //Tags
    private List<Tag> tags;
    private List<Tag> currentUserTags;
    private List<String> currUserTagStr;

    public ProfileManagementManagedBean() {
        currentUserTags = new ArrayList<>();
        currUserTagStr = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        selectedUserToUpdate = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        setPlaceHolderName(selectedUserToUpdate.getFirst_name());
        setPlaceHolderLastName(selectedUserToUpdate.getLast_name());
        setPlaceHolderGender(selectedUserToUpdate.getGender().toString());
        setTags(getTagSessionBean().retrieveAllTags());
        setPath("../uploadedFiles/" + selectedUserToUpdate.getEmail() + ".jpg");
        System.out.println(path);
        if (!selectedUserToUpdate.getTags().isEmpty()) {
            setCurrentUserTags(selectedUserToUpdate.getTags());
            for (Tag t : currentUserTags) {
                currUserTagStr.add(t.getTag_name());
            }
        }

        if (selectedUserToUpdate.getAvatar() == null) {
            setHasAvatar(false);
        } else {
            setHasAvatar(true);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + selectedUserToUpdate.getEmail() + ".jpg";

            System.err.println("********** ManagedBean.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** ManagedBean.handleFileUpload(): newFilePath: " + newFilePath);

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputStream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            getUserSessionBeanLocal().uploadAvatar(selectedUserToUpdate, selectedUserToUpdate.getEmail());
            setPath("../uploadedFiles/" + selectedUserToUpdate.getEmail() + ".jpg");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
            System.out.println("Uploaded profile picture");
            System.out.println("Uploaded profile picture");
            System.out.println("Uploaded profile picture");

        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        } catch (UserNotFoundException ex) {
            Logger.getLogger(ProfileManagementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            getUserSessionBeanLocal().updateProfile(getSelectedUserToUpdate());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
        } catch (UserNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found exception has occured: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void doAddCreditCard(ActionEvent event) {
        System.out.println("Selected this button");
    }

    public void addCreditCard(ActionEvent event) throws IOException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(getExpiryDate());
        creditCard = new CreditCard(getNameOnCard(), getCardNumber(), getCvv(), strDate);
        try {
            selectedUserToUpdate = getUserSessionBeanLocal().addCreditCard(getSelectedUserToUpdate(), getCreditCard());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Credit card added successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentCustomerEntity", selectedUserToUpdate);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/accounts/viewProfile.xhtml");
        } catch (UserNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found exception has occured: " + ex.getMessage(), null));
        } catch (CreditCardErrorException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Card exception has occured: " + ex.getMessage(), null));
        }
    }

    public void doDeleteCreditCard(ActionEvent event) throws IOException {
        try {
            selectedUserToUpdate = getCreditCardSessionBeanLocal().removeCreditCard(selectedUserToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Credit card deleted successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentCustomerEntity", selectedUserToUpdate);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/accounts/viewProfile.xhtml");
        } catch (UserNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found exception has occured: " + ex.getMessage(), null));
        }
    }

    public List<Tag> completeTags(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Tag> countries = getTags();
        countries.stream().filter(t -> t.getTag_name().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
        List<Tag> toReturn = new ArrayList<>();
        Boolean notExist = true;

        for (Tag t : countries) {
            for (String c : currUserTagStr) {
                String temp = t.getTag_name();
                if (temp.equals(c)) {
                    notExist = false;
                    break;
                }
            }

            if (notExist) {
                toReturn.add(t);
            } else {
                notExist = true;
            }
        }

        return toReturn;
    }

    public void updateUserTags(ActionEvent event) throws IOException {
        try {
            currentUserTags.clear();
            for (String s : currUserTagStr) {
                Tag t = tagSessionBean.retrieveTagByTagName(s);
                currentUserTags.add(t);
            }
            userSessionBeanLocal.updateTag(selectedUserToUpdate, this.currentUserTags);
            currentUserTags = userSessionBeanLocal.retrieveUserTags(selectedUserToUpdate);
            currUserTagStr.clear();
            if (!currentUserTags.isEmpty()) {
                for (Tag t : currentUserTags) {
                    currUserTagStr.add(t.getTag_name());
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tags updated succesfully", null));
        } catch (UserNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found exception has occured: " + ex.getMessage(), null));
        }
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

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    public TagSessionBeanLocal getTagSessionBean() {
        return tagSessionBean;
    }

    public void setTagSessionBean(TagSessionBeanLocal tagSessionBean) {
        this.tagSessionBean = tagSessionBean;
    }

    public CreditCardSessionBeanLocal getCreditCardSessionBeanLocal() {
        return creditCardSessionBeanLocal;
    }

    public void setCreditCardSessionBeanLocal(CreditCardSessionBeanLocal creditCardSessionBeanLocal) {
        this.creditCardSessionBeanLocal = creditCardSessionBeanLocal;
    }

    public UserSessionBeanLocal getUserSessionBeanLocal() {
        return userSessionBeanLocal;
    }

    public void setUserSessionBeanLocal(UserSessionBeanLocal userSessionBeanLocal) {
        this.userSessionBeanLocal = userSessionBeanLocal;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Boolean getHasAvatar() {
        return hasAvatar;
    }

    public void setHasAvatar(Boolean hasAvatar) {
        this.hasAvatar = hasAvatar;
    }

    public List<Tag> getCurrentUserTags() {
        return currentUserTags;
    }

    public void setCurrentUserTags(List<Tag> currentUserTags) {
        this.currentUserTags = currentUserTags;
    }

    public List<String> getCurrUserTagStr() {
        return currUserTagStr;
    }

    public void setCurrUserTagStr(List<String> currUserTagStr) {
        this.currUserTagStr = currUserTagStr;
    }
}
