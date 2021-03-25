/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.User;
import enumeration.FacultyType;
import enumeration.GenderType;
import exception.EmailExistException;
import exception.InputDataValidationException;
import exception.InvalidLoginCredentialException;
import exception.UnknownPersistenceException;
import exception.UserNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author USER
 */
@Named(value = "userRegisterManagedBean")
@RequestScoped
public class UserRegisterManagedBean {

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;
    private String first_name;
    private String last_name;
    private Date birth_date;
    private String email;
    private String password;
    private FacultyType faculty;
    private String major;
    private File avatar;
    private GenderType gender;
    private Integer genderInt;
    private Integer facultyInt;
    
    /**
     * Creates a new instance of UserRegisterManagedBean
     */
    public UserRegisterManagedBean() {
    }
     public void register(ActionEvent event) throws IOException
    {
        try
        {
            if (getGenderInt() == 0) {
                setGender(GenderType.MALE);
            } else if (getGenderInt() == 1) {
                setGender(GenderType.FEMALE);
            } else if (getGenderInt() == 2) {
                setGender(GenderType.OTHERS);
            }
            
            int tempFaculty = getFacultyInt();
            
            if (tempFaculty == 0) {
                setFaculty(FacultyType.ART);
            } else if (tempFaculty == 1) {
                setFaculty(FacultyType.BUSINESS);
            } else if (tempFaculty == 2) {
                setFaculty(FacultyType.COMPUTING);
            } else if (tempFaculty == 3) {
                setFaculty(FacultyType.DENTISTRY);
            } 
            
            User newUser = new User(getFirst_name(), getLast_name(), getBirth_date(), getEmail(), getPassword(), getFaculty(), getMajor(), getGender());
            User user = userSessionBeanLocal.register(newUser);
            userSessionBeanLocal.login(getEmail(), getPassword());
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", user);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        }
        catch(EmailExistException | InvalidLoginCredentialException | UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FacultyType getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyType faculty) {
        this.faculty = faculty;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    /**
     * @return the genderInt
     */
    public Integer getGenderInt() {
        return genderInt;
    }

    /**
     * @param genderInt the genderInt to set
     */
    public void setGenderInt(Integer genderInt) {
        this.genderInt = genderInt;
    }

    /**
     * @return the facultyInt
     */
    public Integer getFacultyInt() {
        return facultyInt;
    }

    /**
     * @param facultyInt the facultyInt to set
     */
    public void setFacultyInt(Integer facultyInt) {
        this.facultyInt = facultyInt;
    }
}
