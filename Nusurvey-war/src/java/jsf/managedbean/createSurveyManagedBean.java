/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import enumeration.FacultyType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author miche
 */
@Named(value = "createSurveyManagedBean")
@ViewScoped
public class createSurveyManagedBean implements Serializable {

    @EJB
    private TagSessionBeanLocal tagSessionBeanLocal;

    private String surveyTitle;
    private String surveyDescription;
    private String maxNumberOfResponse;
    private Boolean giveIncentive;
    private Double incentivePerResponse;
    private FacultyType[] faculties;
    private List<FacultyType> selectedFaculties;
    private List<Tag> tags;
    private Boolean selectAllFaculties;
    private List<Tag> selectedTags;
    private List<Tag> tempTags;
    

    public createSurveyManagedBean() {
        faculties = new FacultyType[]{FacultyType.ART, FacultyType.BUSINESS,
            FacultyType.COMPUTING, FacultyType.DENTISTRY,
            FacultyType.DESIGN, FacultyType.DUKE, FacultyType.EMPLOYEE,
            FacultyType.ENGINEERING, FacultyType.HEALTH,
            FacultyType.INTEGRATIVE, FacultyType.LAW, FacultyType.LIFELONG,
            FacultyType.MEDICINE, FacultyType.POLICY,
            FacultyType.SCIENCE, FacultyType.USP, FacultyType.YALE, FacultyType.YST};

        this.selectedTags = new ArrayList<>();

        this.selectedFaculties = new ArrayList<FacultyType>();
    }

    @PostConstruct
    public void postConstruct() {
        tags = tagSessionBeanLocal.retrieveAllTags();
    }

    public List<Tag> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<Tag> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public List<Tag> completeTags(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Tag> countries = tags;
        return countries.stream().filter(t -> t.getTag_name().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
    }

    public void onSelectAllFaculties() {
        if (this.selectAllFaculties) {
            this.selectedFaculties = new ArrayList<FacultyType>();
            Collections.addAll(selectedFaculties, faculties);
        } else {
            this.selectedFaculties = new ArrayList<FacultyType>();
        }
    }

    public Boolean getSelectAllFaculties() {
        return selectAllFaculties;
    }

    public void setSelectAllFaculties(Boolean selectAllFaculties) {
        this.selectAllFaculties = selectAllFaculties;
    }

    public List<FacultyType> getSelectedFaculties() {
        return selectedFaculties;
    }

    public void setSelectedFaculties(List<FacultyType> selectedFaculties) {
        this.selectedFaculties = selectedFaculties;
    }

    public String getMaxNumberOfResponse() {
        return maxNumberOfResponse;
    }

    public void setMaxNumberOfResponse(String maxNumberOfResponse) {
        this.maxNumberOfResponse = maxNumberOfResponse;
    }

    public Boolean getGiveIncentive() {
        return giveIncentive;
    }

    public void setGiveIncentive(Boolean giveIncentive) {
        this.giveIncentive = giveIncentive;
    }

    public Double getIncentivePerResponse() {
        return incentivePerResponse;
    }

    public void setIncentivePerResponse(Double incentivePerResponse) {
        this.incentivePerResponse = incentivePerResponse;
    }

    public FacultyType[] getFaculties() {
        return faculties;
    }

    public void setFaculties(FacultyType[] faculties) {
        this.faculties = faculties;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getSurveyTitle() {
        return this.surveyTitle;
    }

    public void setSurveyTitle(String title) {
        this.surveyTitle = title;
    }

    public String getSurveyDescription() {
        return this.surveyDescription;
    }

    public void setSurveyDescription(String description) {
        this.surveyDescription = description;
    }

}
