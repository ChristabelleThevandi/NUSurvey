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
 * @author Chrisya
 */
@Entity
public class TextOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long textOptionId;

    private String text;

    @OneToOne
    private QuestionWrapper questionWrapper;
    @OneToOne(mappedBy = "answerGiven")
    private TextAnswer textAnswer;

    public TextOption() {
        super();
    }

    public TextOption(String text) {
        super();
        this.text = text;
    }

    public QuestionWrapper getQuestionWrapper() {
        return questionWrapper;
    }

    public void setQuestionWrapper(QuestionWrapper questionWrapper) {
        this.questionWrapper = questionWrapper;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTextOptionId() {
        return textOptionId;
    }

    public void setTextOptionId(Long textOptionId) {
        this.textOptionId = textOptionId;
    }

}
