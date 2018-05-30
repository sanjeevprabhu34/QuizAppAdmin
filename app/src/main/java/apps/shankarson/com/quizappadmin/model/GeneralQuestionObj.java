package apps.shankarson.com.quizappadmin.model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class GeneralQuestionObj {
    private String question;
    private ArrayList<GeneralQuestionOptionObj> optionList = new ArrayList<>();
    private String startDateStr;
    private String endDateStr;
    private Interactor interactor;
    private float optionPercentage = 0;

    public GeneralQuestionObj(Fragment fragment){
        interactor = (Interactor) fragment;
    }


    public interface Interactor {
        public void optionTextNotComplete();
        public void optionCorrectPriorityRemaining();
        public void optionTextComplete(GeneralQuestionOptionObj generalQuestionOptionObj);
        public void priorityPercentageRemaining(float perRemaining);
        public void questionCompleted();
        public void questionNotCompleted(String reason);

    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<GeneralQuestionOptionObj> getOptionList() {
        return optionList;
    }

    public void setOptionList(ArrayList<GeneralQuestionOptionObj> optionList) {
        this.optionList = optionList;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public void checkForOptionCompleted(String optionText, boolean isCorrect,  float priorityPer ){
          if(optionText.length() <= 0){
            interactor.optionTextNotComplete();
        }else if (isCorrect) {
              GeneralQuestionOptionObj generalQuestionOptionObj = new GeneralQuestionOptionObj();
              generalQuestionOptionObj.setOptionText(optionText);
              generalQuestionOptionObj.setOptionTheCorrectAnswer(isCorrect);
              generalQuestionOptionObj.setOptionPriorityInPercentage(priorityPer);
            if((optionPercentage +priorityPer ) < 100) {
                optionList.add(generalQuestionOptionObj);
                interactor.priorityPercentageRemaining(100 -optionPercentage );
            }else{
                interactor.priorityPercentageRemaining((optionPercentage +priorityPer ) );
                interactor.optionTextComplete(generalQuestionOptionObj);
            }

        } else if (!isCorrect) {
            GeneralQuestionOptionObj generalQuestionOptionObj = new GeneralQuestionOptionObj();
            generalQuestionOptionObj.setOptionText(optionText);
            generalQuestionOptionObj.setOptionTheCorrectAnswer(isCorrect);
            optionList.add(generalQuestionOptionObj);
              interactor.optionTextComplete(generalQuestionOptionObj);


        }
    }

    public void IsQuestionSetCompleted(){
        if(question.length() < 10){
            interactor.questionNotCompleted("Please Enter Question Correctly");
        } else if(startDateStr.length() < 10){
            interactor.questionNotCompleted("Please Enter Start Date Correctly");
        } else if(endDateStr.length() < 10){
            interactor.questionNotCompleted("Please Enter End Date Correctly");
        } else{
             if(optionPercentage >= 100){
                 interactor.questionCompleted();
            }else{
                 //interactor.questionNotCompleted("Question Priority Not Completed to 100%");
                 interactor.optionCorrectPriorityRemaining();
             }
        }
    }
}
