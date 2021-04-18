/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.FacultyType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Chrisya
 */
@Entity
public class Survey implements Serializable {

    /**
     * @return the transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;
    private String expiry_date;
    private boolean surveyOpen;
    private String description;
    private String title;
    private Integer max_surveyees;
    private final Double price_per_response = 0.01;
    private Double reward;

    @ManyToMany(mappedBy = "surveyTaken")
    private List<User> surveyees;

    @ManyToOne
    private User creator;

    @OneToMany
    private List<Tag> tags;

    @OneToMany(mappedBy = "survey")
    private List<QuestionWrapper> questionWrappers;

    @OneToMany(mappedBy = "survey")
    private List<SurveyResponse> responses;

    private List<FacultyType> faculties;
    @OneToMany(mappedBy = "survey")
    private List<Transaction> transactions;

    public Survey() {
        this.surveyOpen = true;
    }

    public List<QuestionWrapper> getQuestionWrappers() {
        return questionWrappers;
    }

    public void setQuestionWrappers(List<QuestionWrapper> questionWrappers) {
        this.questionWrappers = questionWrappers;
    }

    public List<FacultyType> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<FacultyType> faculties) {
        this.faculties = faculties;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public boolean isSurveyOpen() {
        return surveyOpen;
    }

    public void setSurveyOpen(boolean surveyOpen) {
        this.surveyOpen = surveyOpen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMax_surveyees() {
        return max_surveyees;
    }

    public void setMax_surveyees(Integer max_surveyees) {
        this.max_surveyees = max_surveyees;
    }

    public Double getPrice_per_response() {
        return price_per_response;
    }

    public List<User> getSurveyees() {
        return surveyees;
    }

    public void setSurveyees(List<User> surveyees) {
        this.surveyees = surveyees;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<SurveyResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<SurveyResponse> responses) {
        this.responses = responses;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyId != null ? surveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the surveyId fields are not set
        if (!(object instanceof Survey)) {
            return false;
        }
        Survey other = (Survey) object;
        if ((this.surveyId == null && other.surveyId != null) || (this.surveyId != null && !this.surveyId.equals(other.surveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Survey[ id=" + surveyId + " ]";
    }

}
