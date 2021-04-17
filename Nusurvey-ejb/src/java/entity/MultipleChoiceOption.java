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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Chrisya
 */
@Entity
public class MultipleChoiceOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mcqOptionId;

    private String content;
    @ManyToOne
    private QuestionWrapper questionWrapper;

    private Long tempId;
    @OneToOne(mappedBy = "optionChosen")
    private MultipleChoiceAnswer multipleChoiceAnswer;

    public MultipleChoiceOption() {
        super();
    }

    public MultipleChoiceOption(String content) {
        super();
        this.content = content;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
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

    public Long getMcqOptionId() {
        return mcqOptionId;
    }

    public void setMcqOptionId(Long mcqOptionId) {
        this.mcqOptionId = mcqOptionId;
    }

    public MultipleChoiceAnswer getMultipleChoiceAnswer() {
        return multipleChoiceAnswer;
    }

    public void setMultipleChoiceAnswer(MultipleChoiceAnswer multipleChoiceAnswer) {
        this.multipleChoiceAnswer = multipleChoiceAnswer;
    }
    
    public boolean equals(MultipleChoiceAnswer answer) {
        return this.equals(answer);
    }

}
