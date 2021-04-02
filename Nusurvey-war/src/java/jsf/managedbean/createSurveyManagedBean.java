/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SurveySessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.MultipleChoiceOption;
import entity.Question;
import entity.QuestionWrapper;
import entity.Survey;
import entity.Tag;
import entity.User;
import enumeration.FacultyType;
import enumeration.QuestionType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author miche
 */
@Named(value = "createSurveyManagedBean")
@ViewScoped
public class createSurveyManagedBean implements Serializable {

    @EJB(name = "SurveySessionBeanLocal")
    private SurveySessionBeanLocal surveySessionBeanLocal;

    @EJB
    private TagSessionBeanLocal tagSessionBeanLocal;

    private String surveyTitle;
    private String surveyDescription;
    private Integer maxNumberOfResponse;
    private Boolean giveIncentive;
    private String incentivePerResponse;
    private FacultyType[] faculties;
    private List<FacultyType> selectedFaculties;
    private List<Tag> tags;
    private Boolean selectAllFaculties;
    private List<Tag> selectedTags;
    private List<Tag> tempTags;
    private List<QuestionWrapper> questions;
    private String questionTitle;
    private String questionType;
    private QuestionType questionTypeEnum;
    private Boolean mcq;
    private Boolean checkbox;
    private Boolean slider;
    private Boolean text;
    private String optionContent;

    public createSurveyManagedBean() {
        faculties = new FacultyType[]{FacultyType.ART, FacultyType.BUSINESS,
            FacultyType.COMPUTING, FacultyType.DENTISTRY,
            FacultyType.DESIGN, FacultyType.DUKE, FacultyType.EMPLOYEE,
            FacultyType.ENGINEERING, FacultyType.HEALTH,
            FacultyType.INTEGRATIVE, FacultyType.LAW, FacultyType.LIFELONG,
            FacultyType.MEDICINE, FacultyType.POLICY,
            FacultyType.SCIENCE, FacultyType.USP, FacultyType.YALE, FacultyType.YST};

        this.selectedTags = new ArrayList<>();

        this.selectedFaculties = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.incentivePerResponse = "0";
    }

    @PostConstruct
    public void postConstruct() {
        tags = tagSessionBeanLocal.retrieveAllTags();
    }

    public void createSurvey(ActionEvent event) {
        System.out.println("start creating" + incentivePerResponse);
        Survey newSurvey = new Survey();
        newSurvey.setQuestions(questions);
        newSurvey.setCreator((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity"));
        newSurvey.setDescription(surveyDescription);
        newSurvey.setTitle(surveyTitle);
        System.out.println("title2" + newSurvey.getTitle());
        newSurvey.setMax_surveyees(maxNumberOfResponse);
        Double temp = Double.valueOf(incentivePerResponse);
        newSurvey.setReward(temp);
        newSurvey.setTags(tags);
        newSurvey.setFaculties(selectedFaculties);
        surveySessionBeanLocal.createSurvey(newSurvey);
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public QuestionType getQuestionTypeEnum() {
        return questionTypeEnum;
    }

    public void setQuestionTypeEnum(QuestionType questionTypeEnum) {
        this.questionTypeEnum = questionTypeEnum;
    }

    public Boolean getMcq() {
        return mcq;
    }

    public void setMcq(Boolean mcq) {
        this.mcq = mcq;
    }

    public Boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public Boolean getSlider() {
        return slider;
    }

    public void setSlider(Boolean slider) {
        this.slider = slider;
    }

    public Boolean getText() {
        return text;
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        QuestionType questionTypeEnum1;
        System.out.println("Halo " + questionType);
        if (questionType.equals("Mcq")) {
            questionTypeEnum1 = QuestionType.MCQ;
            this.mcq = true;
            this.checkbox = false;
            this.slider = false;
            this.text = false;
        } else if (questionType.equals("Checkbox")) {
            questionTypeEnum1 = QuestionType.CHECKBOX;
            this.checkbox = true;
            this.mcq = false;
            this.slider = false;
            this.text = false;
            System.out.println(this.checkbox);
        } else if (questionType.equals("Slider")) {
            questionTypeEnum1 = QuestionType.SLIDEBAR;
            this.slider = true;
            this.checkbox = false;
            this.mcq = false;
            this.text = false;
        } else {
            questionTypeEnum1 = QuestionType.TEXT;
            this.text = true;
            this.checkbox = false;
            this.slider = false;
            this.mcq = false;
        }
        this.questionTypeEnum = questionTypeEnum1;
    }

    public void addQuestion() {
        System.out.println("ADDED");
        QuestionWrapper questionWrapper = new QuestionWrapper(new Question());
        questionWrapper.getQuestion().setQuestionNumber((long) questions.size() + 1);
        questionWrapper.getQuestion().setTitle(questionTitle);
        questions.add(questionWrapper);
        this.questionTitle = null;
        this.questionType = null;
        this.questionTypeEnum = null;
        this.mcq = true;
        this.checkbox = false;
        this.slider = false;
        this.text = false;
    }

    public void addOption(QuestionWrapper questionWrapper) {
        questionWrapper.getMcq().add(new MultipleChoiceOption());
//        QuestionOption option = new MultipleChoiceOption(optionContent);
//        option.setOptionNumber((long) options.size() + 1);
//        options.add(option);
//        this.optionContent = null;
        
    }

    public TagSessionBeanLocal getTagSessionBeanLocal() {
        return tagSessionBeanLocal;
    }

    public void setTagSessionBeanLocal(TagSessionBeanLocal tagSessionBeanLocal) {
        this.tagSessionBeanLocal = tagSessionBeanLocal;
    }

    public List<Tag> getTempTags() {
        return tempTags;
    }

    public void setTempTags(List<Tag> tempTags) {
        this.tempTags = tempTags;
    }

    public List<QuestionWrapper> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionWrapper> questions) {
        this.questions = questions;
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
            this.selectedFaculties = new ArrayList<>();
            Collections.addAll(selectedFaculties, faculties);
        } else {
            this.selectedFaculties = new ArrayList<>();
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

    public Integer getMaxNumberOfResponse() {
        return maxNumberOfResponse;
    }

    public void setMaxNumberOfResponse(Integer maxNumberOfResponse) {
        this.maxNumberOfResponse = maxNumberOfResponse;
    }

    public Boolean getGiveIncentive() {
        return giveIncentive;
    }

    public void setGiveIncentive(Boolean giveIncentive) {
        this.giveIncentive = giveIncentive;
    }

    public String getIncentivePerResponse() {
        return incentivePerResponse;
    }

    public void setIncentivePerResponse(String incentivePerResponse) {
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
        System.out.println("SET SURVEY TITLE " + title);
        this.surveyTitle = title;
    }

    public String getSurveyDescription() {
        return this.surveyDescription;
    }

    public void setSurveyDescription(String description) {
        this.surveyDescription = description;
    }

//    public Bookean checkType()
}
