/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Survey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hp
 */
@Stateless
public class SurveySessionBean implements SurveySessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager entityManager;

    public SurveySessionBean() {
    }
     @Override
    public List<Survey> searchSurveysByTitle(String searchString)
    {
        Query query = entityManager.createQuery("SELECT s FROM entityManager s WHERE s.name LIKE :inSearchString ORDER BY s.title ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Survey> SurveyEntities = query.getResultList();
        
        for(Survey SurveyEntity:SurveyEntities)
        {
            SurveyEntity.getDescription();
            SurveyEntity.getPrice_per_response();
            SurveyEntity.getTags().size();
        }
        
        return SurveyEntities;
    }
    
     @Override
    public List<Survey> sortSurveysByPrice() 
    {
        List<Survey> SurveyEntities = new ArrayList<>();
        
        for(Survey SurveyEntity:SurveyEntities)
        {
            SurveyEntity.getPrice_per_response();
            SurveyEntity.getTags().size();
        }
        
        Collections.sort(SurveyEntities, new Comparator<Survey>()
            {
                public int compare(Survey pe1, Survey pe2) {
                    return pe1.getPrice_per_response().compareTo(pe2.getPrice_per_response());
                }
            });

        return SurveyEntities;
    }
    @Override
    public List<Survey> filterSurveysByTags(List<Long> tagIds, String condition)
    {
        List<Survey> SurveyEntities = new ArrayList<>();
        
        if(tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR")))
        {
            return SurveyEntities;
        }
        else
        {
            if(condition.equals("OR"))
            {
                Query query = entityManager.createQuery("SELECT DISTINCT pe FROM SurveyEntity pe, IN (pe.tags) te WHERE te.tagId IN :inTagIds ORDER BY pe.title ASC");
                query.setParameter("inTagIds", tagIds);
                SurveyEntities = query.getResultList();                                                          
            }
            else
            {
                String selectClause = "SELECT pe FROM SurveyEntity pe";
                String whereClause = "";
                Boolean firstTag = true;
                Integer tagCount = 1;

                for(Long tagId:tagIds)
                {
                    selectClause += ", IN (pe.tags) te" + tagCount;

                    if(firstTag)
                    {
                        whereClause = "WHERE te1.tagId = " + tagId;
                        firstTag = false;
                    }
                    else
                    {
                        whereClause += " AND te" + tagCount + ".tagId = " + tagId; 
                    }
                    
                    tagCount++;
                }
                
                String jpql = selectClause + " " + whereClause + " ORDER BY pe.title ASC";
                Query query = entityManager.createQuery(jpql);
                SurveyEntities = query.getResultList();                                
            }
            
            for(Survey SurveyEntity:SurveyEntities)
            {
                SurveyEntity.getDescription();
                SurveyEntity.getPrice_per_response();
                SurveyEntity.getTags().size();
            }
            
            Collections.sort(SurveyEntities, new Comparator<Survey>()
            {
                public int compare(Survey pe1, Survey pe2) {
                    return pe1.getTitle().compareTo(pe2.getTitle());
                }
            });
            
            return SurveyEntities;
        }
    }
    
    
}
