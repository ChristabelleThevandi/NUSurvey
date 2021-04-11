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
    private String title;

    public CreateNewTransactionReq() {
    }

    public CreateNewTransactionReq(CreditCard card, Double amount, TransactionType type, String title) {
        this.card = card;
        this.amount = amount;
        this.type = type;
        this.title = title;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
}
