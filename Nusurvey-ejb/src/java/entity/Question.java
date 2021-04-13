/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.QuestionType;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Chrisya
 */
@Entity
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    private String title;
    private String image;
    private QuestionType type;
    private Long questionNumber;
    private String typeStr;
    private Boolean mcq;
    private Boolean checkbox;
    private Boolean slider;
    private Boolean text;

    @OneToOne(mappedBy = "question")
    private QuestionWrapper questionWrapper;

    public Question() {
        this.type = QuestionType.MCQ;
        this.typeStr = "Mcq";
    }

    public Long getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Long questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the questionId fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Question[ id=" + questionId + " ]";
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
        if (typeStr.equals("Mcq")) {
            this.setType(QuestionType.MCQ);
            this.mcq = true;
            this.checkbox = false;
            this.slider = false;
            this.text = false;
        } else if (typeStr.equals("Checkbox")) {
            this.setType(QuestionType.CHECKBOX);
            this.checkbox = true;
            this.mcq = false;
            this.slider = false;
            this.text = false;
        } else if (typeStr.equals("Slider")) {
            this.setType(QuestionType.SLIDEBAR);
            this.slider = true;
            this.checkbox = false;
            this.mcq = false;
            this.text = false;
        } else {
            this.setType(QuestionType.TEXT);
            this.text = true;
            this.checkbox = false;
            this.slider = false;
            this.mcq = false;
        }
    }

    public Boolean getMcq() {
        return (this.type.equals(QuestionType.MCQ));
    }

    public void setMcq(Boolean mcq) {
        this.mcq = mcq;
    }

    public Boolean getCheckbox() {
        return (this.type.equals(QuestionType.CHECKBOX));
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public Boolean getSlider() {
        return (this.type.equals(QuestionType.SLIDEBAR));
    }

    public void setSlider(Boolean slider) {
        this.slider = slider;
    }

    public Boolean getText() {
        return (this.type.equals(QuestionType.TEXT));
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public QuestionWrapper getQuestionWrapper() {
        return questionWrapper;
    }

    public void setQuestionWrapper(QuestionWrapper questionWrapper) {
        this.questionWrapper = questionWrapper;
    }

}
