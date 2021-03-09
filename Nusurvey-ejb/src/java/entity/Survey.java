/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;
    private Date expiry_date;
    private boolean open;
    private String description;
    private String title;
    private Integer max_surveyees;
    private Double price_per_response;
    
    @ManyToMany(mappedBy = "surveyTaken")
    private List<User> surveyees;

    @ManyToOne
    private User creator;
    
    @ManyToMany
    private List<Tag> tags;
    
    @OneToMany(mappedBy = "survey")
    private List<Question> questions;
    
    @OneToMany(mappedBy = "survey")
    private List<Response> responses;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
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

    public void setPrice_per_response(Double price_per_response) {
        this.price_per_response = price_per_response;
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
