package apps.shankarson.com.quizappadmin.Utilities;

public class StringUtilities {

    public static String  AddPrecedingZeroIfSingleDigit(int num){
        String currNum = String.valueOf(num);

        if(currNum.length() == 1){
            currNum = "0" + currNum;
        }

        return currNum;
    }
}
