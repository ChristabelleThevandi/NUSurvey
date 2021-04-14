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
public class MultipleChoiceAnswer implements Serializable {

    @OneToOne(mappedBy = "multipleChoiceAnswer")
    private AnswerWrapper answerWrapper;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private MultipleChoiceOption optionChosen;

    private String optionChosenString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionChosenString() {
        return optionChosenString;
    }

    public void setOptionChosenString(String optionChosenString) {
        this.optionChosenString = optionChosenString;
    }

    public AnswerWrapper getAnswerWrapper() {
        return answerWrapper;
    }

    public void setAnswerWrapper(AnswerWrapper answerWrapper) {
        this.answerWrapper = answerWrapper;
    }

    public MultipleChoiceOption getOptionChosen() {
        return optionChosen;
    }

    public void setOptionChosen(MultipleChoiceOption optionChosen) {
        this.optionChosen = optionChosen;
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
        if (!(object instanceof MultipleChoiceAnswer)) {
            return false;
        }
        MultipleChoiceAnswer other = (MultipleChoiceAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MultipleChoiceAnswer[ id=" + id + " ]";
    }

}
