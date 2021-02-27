/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.UserSessionBeanLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Chrisya
 */
@Named(value = "viewMySurveysManagedBean")
@RequestScoped
public class ViewMySurveysManagedBean {

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    /**
     * Creates a new instance of ViewMySurveysManagedBean
     */
    public ViewMySurveysManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        //surveys = userSessionBeanLocal.retrieveAllSurveys();
    }
    
}
