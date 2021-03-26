/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author USER
 */
@Named(value = "privacyManagedBean")
@ViewScoped
public class PrivacyManagedBean implements Serializable {

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;
    private String password;
    private String confirmPassword;
    private String newPassword;
    private User user;
    /**
     * Creates a new instance of PrivacyManagedBean
     */
    public PrivacyManagedBean() {
        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }

    public void doChangePassword()
    {
        String currentPassword = user.getPassword();
        String crPassword = getPassword();
        String  nPassword = getNewPassword();
        String  cPassword = getConfirmPassword();
        
        if (cPassword.equals(nPassword))
        {
            if (currentPassword.equals(crPassword))
            {
                userSessionBeanLocal.changePassword(user, nPassword);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated your password", null));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect Old Password", null));
            }     
        } 
        else
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect Confirmation Password", null)); 
            
        }
     
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
