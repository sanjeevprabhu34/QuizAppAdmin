package apps.shankarson.com.quizappadmin.Utilities;

import android.util.Log;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtilities {

    public static long StringTimeToDate(String dateStr, String dateFormat) throws ParseException {
        String str_date=dateStr;
        DateFormat formatter ;
        Date date ;
        formatter = new SimpleDateFormat(dateFormat);
        date = (Date)formatter.parse(str_date);

       // Log.e("time", "Today is " + date.getTime());
       long milliseconds =  stringTimeToUTC(date);
       return milliseconds;
    }

    public static long stringTimeToUTC(Date myDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(myDate);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("MMM dd, yyy h:mm a zz");
        String dateAsString = outputFmt.format(time);
        Log.e("milli", String.valueOf(myDate.getTime()));
        return myDate.getTime();
    }
}
