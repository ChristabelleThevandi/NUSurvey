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
import javax.validation.constraints.Min;


/**
 *
 * @author Chrisya
 */
//@ScriptAssert(lang = "javascript", script = "_this.minRange < _this.maxRange")
@Entity
public class SliderOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sliderOptionId;

    private int minRange;
    private int maxRange;
    private String minLabel;
    private String maxLabel;
    
    @OneToOne
    private QuestionWrapper questionWrapper;
    @OneToOne(mappedBy = "optionGiven")
    private SliderAnswer sliderAnswer;

    public SliderOption() {
        super();
    }

    public SliderOption(int minRange, int maxRange, String minLabel, String maxLabel) {
        super();
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.minLabel = minLabel;
        this.maxLabel = maxLabel;
    }

    public QuestionWrapper getQuestionWrapper() {
        return questionWrapper;
    }

    public void setQuestionWrapper(QuestionWrapper questionWrapper) {
        this.questionWrapper = questionWrapper;
    }

    public int getMinRange() {
        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public String getMinLabel() {
        return minLabel;
    }

    public void setMinLabel(String minLabel) {
        this.minLabel = minLabel;
    }

    public String getMaxLabel() {
        return maxLabel;
    }

    public void setMaxLabel(String maxLabel) {
        this.maxLabel = maxLabel;
    }

    public Long getSliderOptionId() {
        return sliderOptionId;
    }

    public void setSliderOptionId(Long sliderOptionId) {
        this.sliderOptionId = sliderOptionId;
    }

    public SliderAnswer getSliderAnswer() {
        return sliderAnswer;
    }

    public void setSliderAnswer(SliderAnswer sliderAnswer) {
        this.sliderAnswer = sliderAnswer;
    }

}
