/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Chrisya
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBeean {

    @EJB
    private TagSessionBeanLocal tagSessionBean;

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager em;
    
    

    public DataInitSessionBeean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        List<Tag> t = tagSessionBean.retrieveAllTags();
        if (t.size() == 0)
        {
            initializeData();
        }
    }
    
    private void initializeData()
    {
        Tag t = new Tag("IS3106");
        em.persist(t);
        
        t = new Tag("University Town");
        em.persist(t);
        
        t = new Tag("RC4");
        em.persist(t);
        
        t = new Tag("Tembusu");
        em.persist(t);
        
        t = new Tag("CAPT");
        em.persist(t);
        
        t = new Tag("Post-Graduate");
        em.persist(t);
    }
}
