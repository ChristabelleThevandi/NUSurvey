/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SurveySessionBeanLocal;
import entity.AnswerWrapper;
import entity.CheckboxOption;
import entity.MultipleChoiceOption;
import entity.QuestionWrapper;
import entity.Survey;
import exception.SurveyNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author Chrisya
 */
@Named(value = "viewSurveyDetailsManagedBean")
@RequestScoped
public class ViewSurveyDetailsManagedBean implements Serializable {

    @EJB(name = "SurveySessionBeanLocal")
    private SurveySessionBeanLocal surveySessionBeanLocal;

    private Survey survey;
    private List<QuestionWrapper> questionWrappers;
    private List<PieChartModel> pieCharts;
    private List<BarChartModel> barCharts;
    private int pieChartIndex = 0;
    private int barChartIndex = 0;

    /**
     * Creates a new instance of ViewSurveyDetailsManagedBean
     */
    public ViewSurveyDetailsManagedBean() {
        this.pieCharts = new ArrayList<>();
        this.barCharts = new ArrayList<>();
        this.questionWrappers = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {

        try {
            Long surveyId = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("surveyId"));
            this.survey = surveySessionBeanLocal.retrieveSurveyBySurveyId(surveyId);
        } catch (NumberFormatException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Survey ID not provided", null));
        } catch (SurveyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the survey record: " + ex.getMessage(), null));
        }

        this.questionWrappers = this.survey.getQuestionWrappers();
        iterateMcqAnswers();
//        iterateCheckboxAnswers();
    }

    public PieChartModel getPieChart() {
        int temp = this.pieChartIndex;
        if (temp >= pieCharts.size()) {
            return this.pieCharts.get(temp - 1);
        }
        this.pieChartIndex++;
        return this.pieCharts.get(temp);
    }

    public BarChartModel getBarChart() {
        int temp = this.barChartIndex;
        if (temp >= barCharts.size()) {
            return this.barCharts.get(temp - 1);
        }
        this.barChartIndex++;
        return this.barCharts.get(temp);
    }
    
    public void iterateMcqAnswers() {
        for (QuestionWrapper qw : this.questionWrappers) {
            HashMap<MultipleChoiceOption, Integer> mcqoptions = new HashMap<>();
            if (qw.getQuestion().getMcq()) {
                List<AnswerWrapper> answerWrappers = qw.getAnswerWrappers();
                for (AnswerWrapper aw : answerWrappers) {
                    MultipleChoiceOption temp = aw.getMultipleChoiceAnswer().getOptionChosen();
                    mcqoptions.put(temp, mcqoptions.getOrDefault(temp, 0) + 1);
                    System.out.println(mcqoptions.getOrDefault(temp, 0));
                }
                generatePieChart(mcqoptions);
            }
        }
    }

//    public void iterateCheckboxAnswers() {
//        for (QuestionWrapper qw : this.questionWrappers) {
//            HashMap<CheckboxOption, Integer> checkboxOptions = new HashMap<>();
//            if (qw.getQuestion().getCheckbox()) {
//                List<AnswerWrapper> answerWrappers = qw.getAnswerWrappers();
//                for (AnswerWrapper aw : answerWrappers) {
//                    List<CheckboxOption> temp = aw.getCheckboxAnswer().getOptionsGiven();
//                    for (CheckboxOption box : temp) {
//                        checkboxOptions.put(box, checkboxOptions.getOrDefault(temp, 0) + 1);
//                        System.out.println(checkboxOptions.getOrDefault(temp, 0));
//                    }
//                }
//                generateBarChart(checkboxOptions);
//            }
//        }
//    }
//    
//    public void generateBarChart(HashMap<CheckboxOption, Integer> hashmap) {
//        BarChartModel barModel = new BarChartModel();
//        ChartData data = new ChartData();
//
//        BarChartDataSet barDataSet = new BarChartDataSet();
//        barDataSet.setLabel("My First Dataset");
//
//        List<Number> values = new ArrayList<>();
//        values.add(65);
//        values.add(59);
//        values.add(80);
//        values.add(81);
//        values.add(56);
//        values.add(55);
//        values.add(40);
//        barDataSet.setData(values);
//
//        List<String> bgColor = new ArrayList<>();
//        bgColor.add("rgba(255, 99, 132, 0.2)");
//        bgColor.add("rgba(255, 159, 64, 0.2)");
//        bgColor.add("rgba(255, 205, 86, 0.2)");
//        bgColor.add("rgba(75, 192, 192, 0.2)");
//        bgColor.add("rgba(54, 162, 235, 0.2)");
//        bgColor.add("rgba(153, 102, 255, 0.2)");
//        bgColor.add("rgba(201, 203, 207, 0.2)");
//        barDataSet.setBackgroundColor(bgColor);
//
//        List<String> borderColor = new ArrayList<>();
//        borderColor.add("rgb(255, 99, 132)");
//        borderColor.add("rgb(255, 159, 64)");
//        borderColor.add("rgb(255, 205, 86)");
//        borderColor.add("rgb(75, 192, 192)");
//        borderColor.add("rgb(54, 162, 235)");
//        borderColor.add("rgb(153, 102, 255)");
//        borderColor.add("rgb(201, 203, 207)");
//        barDataSet.setBorderColor(borderColor);
//        barDataSet.setBorderWidth(1);
//
//        data.addChartDataSet(barDataSet);
//
//        List<String> labels = new ArrayList<>();
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");
//        labels.add("May");
//        labels.add("June");
//        labels.add("July");
//        data.setLabels(labels);
//        barModel.setData(data);
//
//        //Options
//        BarChartOptions options = new BarChartOptions();
//        CartesianScales cScales = new CartesianScales();
//        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
//        linearAxes.setOffset(true);
//        CartesianLinearTicks ticks = new CartesianLinearTicks();
//        ticks.setBeginAtZero(true);
//        linearAxes.setTicks(ticks);
//        cScales.addYAxesData(linearAxes);
//        options.setScales(cScales);
//
//        Title title = new Title();
//        title.setDisplay(true);
//        title.setText("Bar Chart");
//        options.setTitle(title);
//
//        Legend legend = new Legend();
//        legend.setDisplay(true);
//        legend.setPosition("top");
//        LegendLabel legendLabels = new LegendLabel();
//        legendLabels.setFontStyle("bold");
//        legendLabels.setFontColor("#2980B9");
//        legendLabels.setFontSize(24);
//        legend.setLabels(legendLabels);
//        options.setLegend(legend);
//
//        barModel.setOptions(options);
//        this.barCharts.add(barModel);
//    }

    public void generatePieChart(HashMap<MultipleChoiceOption, Integer> hashmap) {
        pieCharts.add(new PieChartModel());
        Iterator it = hashmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            MultipleChoiceOption temp = (MultipleChoiceOption) pair.getKey();
            Integer temp2 = (Integer) pair.getValue();
            pieCharts.get(pieCharts.size() - 1).set(temp.getContent(), temp2);
        }
        System.out.println("DCWEDD" + hashmap.toString());
        pieCharts.get(pieCharts.size() - 1).setLegendPosition("e");
        pieCharts.get(pieCharts.size() - 1).setFill(true);
        pieCharts.get(pieCharts.size() - 1).setShowDataLabels(true);
        pieCharts.get(pieCharts.size() - 1).setDiameter(150);
        pieCharts.get(pieCharts.size() - 1).setShadow(false);
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public List<QuestionWrapper> getQuestionWrappers() {
        return questionWrappers;
    }

    public void setQuestionWrappers(List<QuestionWrapper> questionWrappers) {
        this.questionWrappers = questionWrappers;
    }

    public List<PieChartModel> getPieCharts() {
        return pieCharts;
    }

    public void setPieCharts(List<PieChartModel> pieCharts) {
        this.pieCharts = pieCharts;
    }

    public List<BarChartModel> getBarCharts() {
        return barCharts;
    }

    public void setBarCharts(List<BarChartModel> barCharts) {
        this.barCharts = barCharts;
    }
}
