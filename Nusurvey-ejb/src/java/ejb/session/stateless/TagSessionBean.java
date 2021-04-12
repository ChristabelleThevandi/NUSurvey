/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Tag;
import static entity.User_.tags;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author miche
 */
@Stateless
public class TagSessionBean implements TagSessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager entityManager;

    public TagSessionBean() {
    }

    @Override
    public Long createTag(Tag newTag) {
        entityManager.persist(newTag);
        entityManager.flush();

        return newTag.getTagId();
    }

    @Override
    public List<Tag> retrieveAllTags() {
        Query query = entityManager.createQuery("SELECT t FROM Tag t");
        List<Tag> tags = query.getResultList();

        return tags;
    }
    
    public Tag retrieveTagByTagName(String name) {
        System.out.println("name " + name);
        Query query = entityManager.createQuery("SELECT t FROM Tag t WHERE t.tag_name= :name");
        query.setParameter("name", name);
        return (Tag) query.getSingleResult();
    }
}
