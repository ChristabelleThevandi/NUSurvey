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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author miche
 */
@Entity
public class CheckboxAnswer implements Serializable {

    @OneToOne(mappedBy = "checkboxAnswer")
    private AnswerWrapper answerWrapper;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "checkboxAnswer")
    private List<CheckboxOption> optionsGiven;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerWrapper getAnswerWrapper() {
        return answerWrapper;
    }

    public void setAnswerWrapper(AnswerWrapper answerWrapper) {
        this.answerWrapper = answerWrapper;
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
        if (!(object instanceof CheckboxAnswer)) {
            return false;
        }
        CheckboxAnswer other = (CheckboxAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public List<CheckboxOption> getOptionsGiven() {
        return optionsGiven;
    }

    public void setOptionsGiven(List<CheckboxOption> optionsGiven) {
        this.optionsGiven = optionsGiven;
    }

    @Override
    public String toString() {
        return "entity.CheckboxAnswer[ id=" + id + " ]";
    }

}
