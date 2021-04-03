/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.FacultyType;
import enumeration.GenderType;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Chrisya
 */
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String first_name;
    private String last_name;
    private Date birth_date;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private FacultyType faculty;
    private String major;
    private String avatar;
    private boolean loggedIn;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private Double incentive;
    private Double milestone;

    @OneToMany(mappedBy = "creator")
    private List<Survey> mySurveys;
    
    @ManyToMany
    private List<Survey> surveyTaken;
    
    @ManyToMany
    private List<Tag> tags;
    
    @OneToOne
    private CreditCard creditCard;
    
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
    
    @OneToMany(mappedBy = "surveyee")
    private List<Response> responses;
    

    public User(String first_name, String last_name, Date birth_date, String email, String password, FacultyType faculty, String major, GenderType gender) {
        this();
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.email = email;
        this.password = password;
        this.faculty = faculty;
        this.major = major;
        this.gender = gender;
        this.mySurveys = new ArrayList<>();
        this.surveyTaken = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.responses = new ArrayList<>();
    }
   
    public User() {
        this.incentive = 5.0;
        this.milestone = 100.0;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

        /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the birth_date
     */
    public Date getBirth_date() {
        return birth_date;
    }

    /**
     * @param birth_date the birth_date to set
     */
    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major the major to set
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public FacultyType getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyType faculty) {
        this.faculty = faculty;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public List<Survey> getMySurveys() {
        return mySurveys;
    }

    public void setMySurveys(List<Survey> mySurveys) {
        this.mySurveys = mySurveys;
    }

    public List<Survey> getSurveyTaken() {
        return surveyTaken;
    }

    public void setSurveyTaken(List<Survey> surveyTaken) {
        this.surveyTaken = surveyTaken;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
   
    public Double getMilestone() {
        return milestone;
    }

    public void setMilestone(Double milestone) {
        this.milestone = milestone;
    }

    public Double getIncentive() {
        return incentive;
    }

    public void setIncentive(Double incentive) {
        this.incentive = incentive;
    }
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userId fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + userId + " ]";
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }
 
}
