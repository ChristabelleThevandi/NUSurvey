/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SurveySessionBeanLocal;
import ejb.session.stateless.TransactionSessionBeanLocal;
import entity.Survey;
import entity.User;
import enumeration.TransactionType;
import exception.SurveyNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author miche
 */
@Named(value = "surveyTransactionManagedBean")
@ViewScoped
public class SurveyTransactionManagedBean implements Serializable {

    @EJB(name = "TransactionSessionBeanLocal")
    private TransactionSessionBeanLocal transactionSessionBeanLocal;

    @EJB(name = "SurveySessionBeanLocal")
    private SurveySessionBeanLocal surveySessionBeanLocal;

    private Survey survey;
    private Double totalAmount;
    private User currUser;
    private Double surveyCreationFee;
    private Double totalReward;

    public SurveyTransactionManagedBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        survey = (Survey) session.getAttribute("currSurvey");
        totalAmount = (survey.getReward() * survey.getMax_surveyees()) + survey.getPrice_per_response() * survey.getMax_surveyees();

        this.currUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        this.surveyCreationFee = survey.getPrice_per_response() * survey.getMax_surveyees();
        this.totalReward = survey.getReward() * survey.getMax_surveyees();
    }

    public void doCreateSurvey(ActionEvent event) throws IOException {
        LocalDateTime timeLocal = LocalDateTime.now();
        DateTimeFormatter timeLocalFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String finalDate = timeLocal.format(timeLocalFormat);
        currUser = surveySessionBeanLocal.createSurvey(this.survey);
        try {
            transactionSessionBeanLocal.createNewTransaction(currUser.getCreditCard(), totalAmount, TransactionType.EXPENSE, survey.getSurveyId(), finalDate);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentCustomerEntity", currUser);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/survey/viewMySurvey.xhtml");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Survey created successfully", null));
        } catch (SurveyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new transaction: " + ex.getMessage(), null));
        }
    }

    public Double getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(Double totalReward) {
        this.totalReward = totalReward;
    }

    public SurveySessionBeanLocal getSurveySessionBeanLocal() {
        return surveySessionBeanLocal;
    }

    public void setSurveySessionBeanLocal(SurveySessionBeanLocal surveySessionBeanLocal) {
        this.surveySessionBeanLocal = surveySessionBeanLocal;
    }

    public Double getSurveyCreationFee() {
        return surveyCreationFee;
    }

    public void setSurveyCreationFee(Double surveyCreationFee) {
        this.surveyCreationFee = surveyCreationFee;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

}
