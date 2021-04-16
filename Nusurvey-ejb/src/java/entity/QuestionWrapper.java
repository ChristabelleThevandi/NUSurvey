/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author miche
 */
@Entity
public class QuestionWrapper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Question question;

    @OneToMany(mappedBy = "questionWrapper")
    private List<MultipleChoiceOption> mcq;

    @OneToOne(mappedBy = "questionWrapper")
    private TextOption text;

    @OneToOne(mappedBy = "questionWrapper")
    private SliderOption slider;

    @OneToMany(mappedBy = "questionWrapper")
    private List<CheckboxOption> checkbox;
    @ManyToOne
    private Survey survey;

    private Long tempId;
    
    private String imgPath;
    private boolean hasImage;

    @OneToMany(mappedBy = "questionWrapper")
    private List<AnswerWrapper> answerWrappers;

    public QuestionWrapper() {
        this.question = new Question();
        this.mcq = new ArrayList<>();
        this.slider = new SliderOption();
        this.checkbox = new ArrayList<>();
        this.text = new TextOption();
        this.hasImage = false;
    }

    public QuestionWrapper(Question question) {
        this.question = question;
        this.mcq = new ArrayList<>();
        this.slider = new SliderOption();
        this.slider.setQuestionWrapper(this);
        this.checkbox = new ArrayList<>();
        this.text = new TextOption();
        this.text.setQuestionWrapper(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }


    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionWrapper)) {
            return false;
        }
        QuestionWrapper other = (QuestionWrapper) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.QuestionWrapper[ id=" + id + " ]";
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<MultipleChoiceOption> getMcq() {
        return mcq;
    }

    public void setMcq(List<MultipleChoiceOption> mcq) {
        this.mcq = mcq;
    }

    public TextOption getText() {
        return text;
    }

    public void setText(TextOption text) {
        this.text = text;
    }

    public SliderOption getSlider() {
        return slider;
    }

    public void setSlider(SliderOption slider) {
        this.slider = slider;
    }

    public List<CheckboxOption> getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(List<CheckboxOption> checkbox) {
        this.checkbox = checkbox;
    }

    public List<AnswerWrapper> getAnswerWrappers() {
        return answerWrappers;
    }

    public void setAnswerWrappers(List<AnswerWrapper> answerWrappers) {
        this.answerWrappers = answerWrappers;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

}
