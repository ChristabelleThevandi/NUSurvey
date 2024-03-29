/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CheckboxOption;
import entity.MultipleChoiceOption;
import entity.QuestionWrapper;
import entity.Survey;
import entity.Tag;
import entity.User;
import exception.SurveyNotFoundException;
import exception.UnsupportedDeleteSurveyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author hp
 */
@Stateless
public class SurveySessionBean implements SurveySessionBeanLocal {

    @PersistenceContext(unitName = "Nusurvey-ejbPU")
    private EntityManager entityManager;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public SurveySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public List<Survey> searchSurveysByTitle(String searchString) {
        Query query = entityManager.createQuery("SELECT s FROM entityManager s WHERE s.name LIKE :inSearchString ORDER BY s.title ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Survey> SurveyEntities = query.getResultList();

        for (Survey SurveyEntity : SurveyEntities) {
            SurveyEntity.getDescription();
            SurveyEntity.getPrice_per_response();
            SurveyEntity.getTags().size();
        }

        return SurveyEntities;
    }

    @Override
    public Survey retrieveSurveyBySurveyId(Long surveyId) throws SurveyNotFoundException {
        Survey survey = entityManager.find(Survey.class, surveyId);
        
        if (survey == null) {
            throw new SurveyNotFoundException("Survey with this ID does not exist!");
        } else {
            survey.getFaculties().size();
            survey.getQuestionWrappers().size();
            survey.getResponses();
            survey.getSurveyees();
            survey.getTags();
            
            if (survey.getMax_surveyees() > survey.getResponses().size())
            {
                return survey;
            }
            else {
                throw new SurveyNotFoundException("Survey has reached maximum number of response");
            }
        }
    }

    @Override
    public List<Survey> sortSurveysByPrice() {
        List<Survey> SurveyEntities = new ArrayList<>();

        for (Survey SurveyEntity : SurveyEntities) {
            SurveyEntity.getPrice_per_response();
            SurveyEntity.getTags().size();
        }

        Collections.sort(SurveyEntities, new Comparator<Survey>() {
            public int compare(Survey pe1, Survey pe2) {
                return pe1.getPrice_per_response().compareTo(pe2.getPrice_per_response());
            }
        });

        return SurveyEntities;
    }

    @Override
    public List<Survey> filterSurveysByTags(List<Long> tagIds, String condition) {
        List<Survey> SurveyEntities = new ArrayList<>();

        if (tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR"))) {
            return SurveyEntities;
        } else {
            if (condition.equals("OR")) {
                Query query = entityManager.createQuery("SELECT DISTINCT pe FROM SurveyEntity pe, IN (pe.tags) te WHERE te.tagId IN :inTagIds ORDER BY pe.title ASC");
                query.setParameter("inTagIds", tagIds);
                SurveyEntities = query.getResultList();
            } else {
                String selectClause = "SELECT pe FROM SurveyEntity pe";
                String whereClause = "";
                Boolean firstTag = true;
                Integer tagCount = 1;

                for (Long tagId : tagIds) {
                    selectClause += ", IN (pe.tags) te" + tagCount;

                    if (firstTag) {
                        whereClause = "WHERE te1.tagId = " + tagId;
                        firstTag = false;
                    } else {
                        whereClause += " AND te" + tagCount + ".tagId = " + tagId;
                    }

                    tagCount++;
                }

                String jpql = selectClause + " " + whereClause + " ORDER BY pe.title ASC";
                Query query = entityManager.createQuery(jpql);
                SurveyEntities = query.getResultList();
            }

            for (Survey SurveyEntity : SurveyEntities) {
                SurveyEntity.getDescription();
                SurveyEntity.getPrice_per_response();
                SurveyEntity.getTags().size();
            }

            Collections.sort(SurveyEntities, new Comparator<Survey>() {
                public int compare(Survey pe1, Survey pe2) {
                    return pe1.getTitle().compareTo(pe2.getTitle());
                }
            });

            return SurveyEntities;
        }
    }

    @Override
    public User createSurvey(Survey newSurvey) {
        User creator = newSurvey.getCreator();
        User creatorPersisted = entityManager.find(User.class, creator.getUserId());
        newSurvey.setCreator(creatorPersisted);

        newSurvey.getQuestionWrappers().size();
        List<QuestionWrapper> questions = newSurvey.getQuestionWrappers();
        for (QuestionWrapper q : questions) {
            q.setSurvey(newSurvey);
            
            if (q.getQuestion().getMcq()) {
                q.setCheckbox(null);
                q.setSlider(null);
                q.setText(null);
                for (MultipleChoiceOption o : q.getMcq()) {
                    entityManager.persist(o);
                }
            } else if (q.getQuestion().getCheckbox()) {
                q.setMcq(null);
                q.setSlider(null);
                q.setText(null);
                for (CheckboxOption o : q.getCheckbox()) {
                    entityManager.persist(o);
                }
            } else if (q.getQuestion().getSlider()) {
                q.setCheckbox(null);
                q.setMcq(null);
                q.setText(null);
                entityManager.persist(q.getSlider());
            } else {
                q.setCheckbox(null);
                q.setSlider(null);
                q.setMcq(null);
                entityManager.persist(q.getText());
            }

            entityManager.persist(q.getQuestion());
            entityManager.persist(q);
        }

        entityManager.persist(newSurvey);
        entityManager.flush();

        return creator;
    }

    @Override
    public void deleteSurvey(Survey survey) throws UnsupportedDeleteSurveyException {
        survey = entityManager.find(Survey.class, survey.getSurveyId());
        if (!survey.getSurveyees().isEmpty()) {
            throw new UnsupportedDeleteSurveyException("Cannot delete survey that has been answered!");
        }

        survey.getQuestionWrappers().size();
        List<QuestionWrapper> questions = survey.getQuestionWrappers();
//        for (Question q: questions) {
//            q.getOptions().size();
//            List<QuestionOption> options = q.getOptions();
//            for (QuestionOption o: options) {
//                entityManager.remove(o);
//            }
//            entityManager.remove(q);
//        }

        survey.getTags().size();
        List<Tag> tags = survey.getTags();
        
        survey.getTags().size();
        tags = survey.getTags();
        for (Tag t : tags) {
            survey.getTags().remove(t);
        }

        entityManager.remove(survey);
    }

    @Override
    public void closeSurvey(Survey survey) {
        survey = entityManager.find(Survey.class, survey.getSurveyId());
        survey.setSurveyOpen(false);
    }

    @Override
    public List<Survey> retrieveAllSurveys() {
        Query query = entityManager.createQuery("SELECT s FROM Survey s");
        List<Survey> surveys = query.getResultList();

        return surveys;
    }

    @Override
    public List<Survey> retrieveMyCreatedSurveys(User currUser) {
        currUser = entityManager.find(User.class, currUser.getUserId());
        Query query = entityManager.createQuery("SELECT s FROM Survey s WHERE s.creator= :cUser");
        query.setParameter("cUser", currUser);
        List<Survey> surveys = query.getResultList();
        for (Survey s : surveys)
        {
            s.getResponses().size();
            s.getSurveyees().size();
            s.getFaculties().size();
            s.getQuestionWrappers().size();
            s.getTags().size();
        }
        return surveys;
    }

    @Override
    public List<Survey> retrieveMyFilledSurveys(User currUser) {
        currUser = entityManager.find(User.class, currUser.getUserId());
        Query query = entityManager.createQuery("SELECT s FROM Survey s WHERE currUser IN s.surveyees");
        List<Survey> surveys = query.getResultList();
        for (Survey s : surveys)
        {
            s.getResponses().size();
            s.getSurveyees().size();
            s.getFaculties().size();
            s.getQuestionWrappers().size();
            s.getTags().size();
        }
        return surveys;
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<User>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
