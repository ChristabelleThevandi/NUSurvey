/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Survey;
import entity.User;

/**
 *
 * @author hp
 */
public class GiveRewardReq {
    private User user;
    private Survey survey;

    public GiveRewardReq() {
    }

    public GiveRewardReq(User user, Survey survey) {
        this.user = user;
        this.survey = survey;
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
     * @return the survey
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
    
}
