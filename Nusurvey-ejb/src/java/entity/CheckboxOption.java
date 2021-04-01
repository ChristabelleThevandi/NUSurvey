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

/**
 *
 * @author Chrisya
 */
@Entity
public class CheckboxOption implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkboxOptionId;
    
    private List<String> options;
    @ManyToOne
    private QuestionWrapper questionWrapper;

    public CheckboxOption() {
        super();
    }

    public CheckboxOption(List<String> options) {
        super();
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Long getCheckboxOptionId() {
        return checkboxOptionId;
    }

    public void setCheckboxOptionId(Long checkboxOptionId) {
        this.checkboxOptionId = checkboxOptionId;
    }

}
