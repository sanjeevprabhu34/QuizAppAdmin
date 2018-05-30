package apps.shankarson.com.quizappadmin.Utilities;

import android.util.Log;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilities {

    public static void StringTimeToDate(String dateStr, String dateFormat) throws ParseException {
        String str_date=dateStr;
        DateFormat formatter ;
        Date date ;
        formatter = new SimpleDateFormat(dateFormat);
        date = (Date)formatter.parse(str_date);

        Log.e("time", "Today is " + date.getTime());
    }
}
