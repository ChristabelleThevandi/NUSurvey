/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Tag;
import entity.User;
import java.util.List;

/**
 *
 * @author hp
 */
public class UpdateTagReq {
    private User user;
    private List<Tag> tags;

    public UpdateTagReq() {
    }

    public UpdateTagReq(User user, List<Tag> tags) {
        this.user = user;
        this.tags = tags;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
}
