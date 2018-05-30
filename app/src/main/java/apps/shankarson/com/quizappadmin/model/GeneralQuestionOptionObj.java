package apps.shankarson.com.quizappadmin.model;

public class GeneralQuestionOptionObj {
    private String optionText;
    private float optionPriorityInPercentage;
    private boolean IsOptionTheCorrectAnswer;

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public float getOptionPriorityInPercentage() {
        return optionPriorityInPercentage;
    }

    public void setOptionPriorityInPercentage(float optionPriorityInPercentage) {
        this.optionPriorityInPercentage = optionPriorityInPercentage;
    }

    public boolean isOptionTheCorrectAnswer() {
        return IsOptionTheCorrectAnswer;
    }

    public void setOptionTheCorrectAnswer(boolean optionTheCorrectAnswer) {
        IsOptionTheCorrectAnswer = optionTheCorrectAnswer;
    }
}
