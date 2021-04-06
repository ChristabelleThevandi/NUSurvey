/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SurveySessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import ejb.session.stateless.TransactionSessionBeanLocal;
import entity.CheckboxOption;
import entity.MultipleChoiceOption;
import entity.Question;
import entity.QuestionWrapper;
import entity.Survey;
import entity.Tag;
import entity.User;
import enumeration.FacultyType;
import enumeration.QuestionType;
import enumeration.TransactionType;
import exception.SurveyNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author miche
 */
@Named(value = "createSurveyManagedBean")
@ViewScoped
public class createSurveyManagedBean implements Serializable {

    @EJB(name = "SurveySessionBeanLocal")
    private SurveySessionBeanLocal surveySessionBeanLocal;

    @EJB(name = "TransactionSessionBeanLocal")
    private TransactionSessionBeanLocal transactionSessionBeanLocal;

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
    private Date expiry_date;
    private Survey survey;
    private Double surveyAmount;
    private Double incentiveAmount;
    private Double totalAmount;
    private User currUser;

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
        this.giveIncentive = true;
        this.currUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }

    @PostConstruct
    public void postConstruct() {
        tags = tagSessionBeanLocal.retrieveAllTags();
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public void redirectPayment(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/survey/surveyPayment.xhtml");
    }

    public void createTransaction() throws SurveyNotFoundException {

        transactionSessionBeanLocal.createNewTransaction(currUser.getCreditCard(), this.totalAmount, TransactionType.EXPENSE, this.survey.getSurveyId());
    }

    public void createSurvey(ActionEvent event) throws IOException {
        for (QuestionWrapper q : questions) {
            if (q.getQuestion().getType() == QuestionType.SLIDEBAR) {
                if (q.getSlider().getMinRange() >= q.getSlider().getMaxRange()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Min range of slider question can not be more than or equals to max range", null));
                    return;
                }
            }
        }
        System.out.println("start creating" + incentivePerResponse);
        Survey newSurvey = new Survey();
        newSurvey.setQuestions(questions);
        newSurvey.setCreator(this.currUser);
        newSurvey.setDescription(surveyDescription);
        newSurvey.setTitle(surveyTitle);
        System.out.println("title2" + newSurvey.getTitle());
        newSurvey.setMax_surveyees(maxNumberOfResponse);
        Double temp = Double.valueOf(incentivePerResponse);
        newSurvey.setReward(temp);
        newSurvey.setTags(tags);
        newSurvey.setFaculties(selectedFaculties);
        newSurvey.setExpiry_date(expiry_date);
        this.survey = newSurvey;
        this.setIncentiveAmount(temp * maxNumberOfResponse);
        this.setSurveyAmount(this.survey.getPrice_per_response() * maxNumberOfResponse);
        System.out.println("t" + incentiveAmount + " " + surveyAmount);
        this.setTotalAmount(incentiveAmount + surveyAmount);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currSurvey", survey);

        this.redirectPayment(event);
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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
        MultipleChoiceOption newOption = new MultipleChoiceOption();
        newOption.setQuestionWrapper(questionWrapper);
        questionWrapper.getMcq().add(newOption);
    }

    public void addOptionCheckbox(QuestionWrapper questionWrapper) {
        CheckboxOption newOption = new CheckboxOption();
        newOption.setQuestionWrapper(questionWrapper);
        questionWrapper.getCheckbox().add(newOption);
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

    public SurveySessionBeanLocal getSurveySessionBeanLocal() {
        return surveySessionBeanLocal;
    }

    public void setSurveySessionBeanLocal(SurveySessionBeanLocal surveySessionBeanLocal) {
        this.surveySessionBeanLocal = surveySessionBeanLocal;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
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

    public void setGiveIncentive() {
//        this.giveIncentive = !this.giveIncentive;
    }

    public TransactionSessionBeanLocal getTransactionSessionBeanLocal() {
        return transactionSessionBeanLocal;
    }

    public void setTransactionSessionBeanLocal(TransactionSessionBeanLocal transactionSessionBeanLocal) {
        this.transactionSessionBeanLocal = transactionSessionBeanLocal;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Double getSurveyAmount() {
        return surveyAmount;
    }

    public void setSurveyAmount(Double surveyAmount) {
        this.surveyAmount = surveyAmount;
    }

    public Double getIncentiveAmount() {
        return incentiveAmount;
    }

    public void setIncentiveAmount(Double incentiveAmount) {
        this.incentiveAmount = incentiveAmount;
    }
}
