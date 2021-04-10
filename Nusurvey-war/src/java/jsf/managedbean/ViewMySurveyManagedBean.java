/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SurveySessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Survey;
import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author USER
 */
@Named(value = "viewMySurveyManagedBean")
@ViewScoped
public class ViewMySurveyManagedBean implements Serializable {

    @EJB
    private SurveySessionBeanLocal surveySessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private User currentUser;
    private List<Survey> surveys;
    
    /**
     * Creates a new instance of ViewMySurveyManagedBean
     */
    public ViewMySurveyManagedBean() {
        surveys = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        setCurrentUser((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity"));
        surveys = surveySessionBean.retrieveMyCreatedSurveys(currentUser);
    }
    
    public SurveySessionBeanLocal getSurveySessionBean() {
        return surveySessionBean;
    }

    public void setSurveySessionBean(SurveySessionBeanLocal surveySessionBean) {
        this.surveySessionBean = surveySessionBean;
    }

    public UserSessionBeanLocal getUserSessionBean() {
        return userSessionBean;
    }

    public void setUserSessionBean(UserSessionBeanLocal userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }
    
}
