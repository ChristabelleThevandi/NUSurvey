/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import java.util.List;

/**
 *
 * @author hp
 */
public class FilterSurveysByTagsReq {
    private List<Long> tagIds;
    private String condition;

    public FilterSurveysByTagsReq() {
    }

    public FilterSurveysByTagsReq(List<Long> tagIds, String condition) {
        this.tagIds = tagIds;
        this.condition = condition;
    }

    /**
     * @return the tagIds
     */
    public List<Long> getTagIds() {
        return tagIds;
    }

    /**
     * @param tagIds the tagIds to set
     */
    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
}
