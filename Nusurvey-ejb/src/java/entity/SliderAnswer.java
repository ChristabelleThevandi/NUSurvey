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

/**
 *
 * @author Chrisya
 */
@Entity
public class SliderAnswer extends Answer {

    private int answer;
    private String answerLabel;

    public SliderAnswer() {
        super();
    }

    public SliderAnswer(int answer, String answerLabel) {
        super();
        this.answer = answer;
        this.answerLabel = answerLabel;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getAnswerLabel() {
        return answerLabel;
    }

    public void setAnswerLabel(String answerLabel) {
        this.answerLabel = answerLabel;
    }
    
}
