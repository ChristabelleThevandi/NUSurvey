/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.TransactionSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Transaction;
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
@Named(value = "viewTransactionManagedBean")
@ViewScoped
public class ViewTransactionManagedBean implements Serializable {

    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;
    
    private List<Transaction> transactions;
    private User currentUser;
    
    /**
     * Creates a new instance of ViewTransactionManagedBean
     */
    public ViewTransactionManagedBean() {
        transactions = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct() {
        setCurrentUser((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity"));
        transactions = transactionSessionBean.retrieveAllTransaction(currentUser);
    }


    public TransactionSessionBeanLocal getTransactionSessionBean() {
        return transactionSessionBean;
    }

    public void setTransactionSessionBean(TransactionSessionBeanLocal transactionSessionBean) {
        this.transactionSessionBean = transactionSessionBean;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
}
