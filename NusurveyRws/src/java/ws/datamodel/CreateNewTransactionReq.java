/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.CreditCard;
import enumeration.TransactionType;

/**
 *
 * @author hp
 */
public class CreateNewTransactionReq {
    private CreditCard card;
    private Double amount;
    private TransactionType type;
    private Long surveyId;
    private String date;

    public CreateNewTransactionReq() {
    }

    public CreateNewTransactionReq(CreditCard card, Double amount, TransactionType type, Long surveyId, String date) {
        this.card = card;
        this.amount = amount;
        this.type = type;
        this.surveyId = surveyId;
        this.date = date;
    }

    

    /**
     * @return the card
     */
    public CreditCard getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(CreditCard card) {
        this.card = card;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TransactionType type) {
        this.type = type;
    }

   

    /**
     * @return the surveyId
     */
    public Long getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId the surveyId to set
     */
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
    
}
