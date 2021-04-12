/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author miche
 */
@Entity
public class AnswerWrapper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "answerWrapper")
    private QuestionWrapper questionWrapper;

    @OneToOne
    private CheckboxAnswer checkboxAnswer;

    @OneToOne
    private MultipleChoiceAnswer multipleChoiceAnswer;

    @OneToOne
    private SliderAnswer sliderAnswer;

    @OneToOne
    private TextAnswer textAnswer;

    @OneToOne
    private SurveyResponse response;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionWrapper getQuestionWrapper() {
        return questionWrapper;
    }

    public void setQuestionWrapper(QuestionWrapper questionWrapper) {
        this.questionWrapper = questionWrapper;
    }

    public CheckboxAnswer getCheckboxAnswer() {
        return checkboxAnswer;
    }

    public void setCheckboxAnswer(CheckboxAnswer checkboxAnswer) {
        this.checkboxAnswer = checkboxAnswer;
    }

    public MultipleChoiceAnswer getMultipleChoiceAnswer() {
        return multipleChoiceAnswer;
    }

    public void setMultipleChoiceAnswer(MultipleChoiceAnswer multipleChoiceAnswer) {
        this.multipleChoiceAnswer = multipleChoiceAnswer;
    }

    public SliderAnswer getSliderAnswer() {
        return sliderAnswer;
    }

    public void setSliderAnswer(SliderAnswer sliderAnswer) {
        this.sliderAnswer = sliderAnswer;
    }

    public TextAnswer getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(TextAnswer textAnswer) {
        this.textAnswer = textAnswer;
    }

    public SurveyResponse getResponse() {
        return response;
    }

    public void setResponse(SurveyResponse response) {
        this.response = response;
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
        if (!(object instanceof AnswerWrapper)) {
            return false;
        }
        AnswerWrapper other = (AnswerWrapper) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AnswerWrapper[ id=" + id + " ]";
    }

}
