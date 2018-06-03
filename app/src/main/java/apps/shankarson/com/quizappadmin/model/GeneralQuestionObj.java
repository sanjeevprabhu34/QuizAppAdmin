package apps.shankarson.com.quizappadmin.model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class GeneralQuestionObj {
    private String question;
    private ArrayList<GeneralQuestionOptionObj> optionList = new ArrayList<>();
    private long startDateStr = 0;
    private long endDateStr = 0;
    private Interactor interactor;
    private float optionPercentage = 0;

    public GeneralQuestionObj(Fragment fragment){
        interactor = (Interactor) fragment;
    }


    public interface Interactor {
        public void optionTextNotComplete();
        public void optionCorrectPriorityRemaining(String reason);
        public void optionTextComplete(GeneralQuestionOptionObj generalQuestionOptionObj, float perRemaining );
        public void priorityPercentageRemaining(float perRemaining);
        public void priorityPercentageGreater(String reason, float perRemaining);
        public void questionCompleted();
        public void questionNotCompleted(String reason);
        public void startTimeNotCorrect();
        public void endTimeNotCorrect();
        public void startDateSet(long timeinMilliseconds);
        public void endDateSet(long timeinMilliseconds);

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
        interactor.startDateSet(startDateStr);
    }

    public long getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(long endDateStr) {
        this.endDateStr = endDateStr;
        interactor.endDateSet(endDateStr);
    }

    public void checkForOptionCompleted(String optionText, boolean isCorrect,  float priorityPer ){
        GeneralQuestionOptionObj generalQuestionOptionObj = new GeneralQuestionOptionObj();
          if(optionText.length() <= 0){
            interactor.optionTextNotComplete();
        }else if (isCorrect) {
            if(priorityPer <= 100){
                generalQuestionOptionObj.setOptionText(optionText);
                generalQuestionOptionObj.setOptionTheCorrectAnswer(isCorrect);


                if((optionPercentage + priorityPer) <= 100){
                    generalQuestionOptionObj.setOptionPriorityInPercentage(priorityPer);
                    optionPercentage += priorityPer;
                    optionList.add(generalQuestionOptionObj);
                    interactor.optionTextComplete(generalQuestionOptionObj, (100 - optionPercentage));
                }else{
                    interactor.priorityPercentageGreater("priority percentage greater. Will add the remaining percentage. Please Edit the percentage if required",(100 - optionPercentage) );
                }
            }
        }else if (!isCorrect) {
                  generalQuestionOptionObj.setOptionText(optionText);
                  generalQuestionOptionObj.setOptionTheCorrectAnswer(isCorrect);
                  optionList.add(generalQuestionOptionObj);
                  interactor.optionTextComplete(generalQuestionOptionObj, (100 - optionPercentage));


          }
    }

    public void IsQuestionSetCompleted(){
        question = getQuestion();
        if(question == null ){
            interactor.questionNotCompleted("Please Enter Question Correctly");

        } else if(optionPercentage < 100){
                 interactor.optionCorrectPriorityRemaining("Please fill atleast 3 options");
         }else if(optionList.size() < 3){
            interactor.optionTextNotComplete();
        }else if(startDateStr ==0){
            interactor.startTimeNotCorrect();
        }else if(endDateStr ==0){
            interactor.endTimeNotCorrect();
        }else{
                 //interactor.questionNotCompleted("Question Priority Not Completed to 100%");
            setQuestion(question);
            setStartDateStr(startDateStr);
            setEndDateStr(endDateStr);
                 interactor.questionCompleted();

         }


   }
}
