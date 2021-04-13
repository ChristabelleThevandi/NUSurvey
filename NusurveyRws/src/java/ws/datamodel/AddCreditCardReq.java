/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.CreditCard;
import entity.User;

/**
 *
 * @author hp
 */
public class AddCreditCardReq {
    private User user;
    private CreditCard card;

    public AddCreditCardReq() {
    }

    public AddCreditCardReq(User user, CreditCard card) {
        this.user = user;
        this.card = card;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
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
    
}
