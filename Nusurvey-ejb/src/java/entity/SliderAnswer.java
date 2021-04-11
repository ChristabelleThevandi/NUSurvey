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
public class SliderAnswer implements Serializable {

    @OneToOne(mappedBy = "sliderAnswer")
    private AnswerWrapper answerWrapper;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private SliderOption optionGiven;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SliderOption getOptionGiven() {
        return optionGiven;
    }

    public void setOptionGiven(SliderOption optionGiven) {
        this.optionGiven = optionGiven;
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
        if (!(object instanceof SliderAnswer)) {
            return false;
        }
        SliderAnswer other = (SliderAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SliderAnswer[ id=" + id + " ]";
    }

}
