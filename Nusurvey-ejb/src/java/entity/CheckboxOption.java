/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Chrisya
 */
@Entity
public class CheckboxOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkboxOptionId;

    private String content;
    @ManyToOne
    private QuestionWrapper questionWrapper;

    private Long tempId;
    private boolean isSelected;

    @ManyToMany(mappedBy = "optionsGiven")
    private List<CheckboxAnswer> checkboxAnswers;

    public CheckboxOption() {
        super();
    }

    public CheckboxOption(String content) {
        super();
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionWrapper getQuestionWrapper() {
        return questionWrapper;
    }

    public void setQuestionWrapper(QuestionWrapper questionWrapper) {
        this.questionWrapper = questionWrapper;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    public Long getCheckboxOptionId() {
        return checkboxOptionId;
    }

    public void setCheckboxOptionId(Long checkboxOptionId) {
        this.checkboxOptionId = checkboxOptionId;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public List<CheckboxAnswer> getCheckboxAnswers() {
        return checkboxAnswers;
    }

    public void setCheckboxAnswers(List<CheckboxAnswer> checkboxAnswers) {
        this.checkboxAnswers = checkboxAnswers;
    }

    @Override
    public boolean equals(Object obj) {
        CheckboxOption option = (CheckboxOption) obj;
        return this.equals(option);
    }
}
