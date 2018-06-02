package apps.shankarson.com.quizappadmin.model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class GeneralQuestionObj {
    private String question;
    private ArrayList<GeneralQuestionOptionObj> optionList = new ArrayList<>();
    private long startDateStr;
    private long endDateStr;
    private Interactor interactor;
    private float optionPercentage = 0;

    public GeneralQuestionObj(Fragment fragment){
        interactor = (Interactor) fragment;
    }


    public interface Interactor {
        public void optionTextNotComplete();
        public void optionCorrectPriorityRemaining(String reason);
        public void optionTextComplete(GeneralQuestionOptionObj generalQuestionOptionObj);
        public void priorityPercentageRemaining(float perRemaining);
        public void priorityPercentageGreater(String reason, float perRemaining);
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

    public long getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(long startDateStr) {
        this.startDateStr = startDateStr;
    }

    public long getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(long endDateStr) {
        this.endDateStr = endDateStr;
    }

    public void checkForOptionCompleted(String optionText, boolean isCorrect,  float priorityPer, long startTime, long endTime ){
        GeneralQuestionOptionObj generalQuestionOptionObj = null;
          if(optionText.length() <= 0){
            interactor.optionTextNotComplete();
        }else if (isCorrect) {
              generalQuestionOptionObj = new GeneralQuestionOptionObj();
              generalQuestionOptionObj.setOptionText(optionText);
              generalQuestionOptionObj.setOptionTheCorrectAnswer(isCorrect);
              generalQuestionOptionObj.setOptionPriorityInPercentage(priorityPer);
              optionPercentage += priorityPer;
            if((optionPercentage ) <= 100) {
                optionList.add(generalQuestionOptionObj);
                interactor.priorityPercentageRemaining(100 -optionPercentage );
            }else{
                //optionList.add(generalQuestionOptionObj);
                //interactor.priorityPercentageRemaining((optionPercentage +priorityPer ) );
                //interactor.optionTextComplete(generalQuestionOptionObj);
                interactor.priorityPercentageGreater("priority percentage is greater than 100", optionPercentage);
            }

        } else if (!isCorrect) {
              generalQuestionOptionObj = new GeneralQuestionOptionObj();
            generalQuestionOptionObj.setOptionText(optionText);
            generalQuestionOptionObj.setOptionTheCorrectAnswer(isCorrect);
            optionList.add(generalQuestionOptionObj);
              interactor.optionTextComplete(generalQuestionOptionObj);
        }else if(startTime >= 0 && endTime >= 0){
              setStartDateStr(startTime);
              setEndDateStr(endTime);
          }else{
              interactor.optionTextComplete(generalQuestionOptionObj);
          }
    }

    public void IsQuestionSetCompleted(){
        question = getQuestion();
        if(question == null || question.length() <= 5){
            interactor.questionNotCompleted("Please Enter Question Correctly");

        } else if(optionPercentage < 100){
                 interactor.optionCorrectPriorityRemaining("Please fill atleast 3 options");
         }else if(optionList.size() < 3){
            interactor.optionTextNotComplete();
        }else{
                 //interactor.questionNotCompleted("Question Priority Not Completed to 100%");
                 interactor.questionCompleted();
                 setQuestion(question);
                 setStartDateStr(startDateStr);
                 setEndDateStr(endDateStr);
         }

        interactor.questionCompleted();
   }
}
