/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Tag;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author miche
 */
@Local
public interface TagSessionBeanLocal {

    public Long createTag(Tag newTag);

    public List<Tag> retrieveAllTags();
    
}
