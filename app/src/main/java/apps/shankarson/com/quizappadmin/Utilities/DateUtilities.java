package apps.shankarson.com.quizappadmin.Utilities;

import android.util.Log;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import apps.shankarson.com.quizappadmin.AppConstants.DateTimeConstants;

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
        SimpleDateFormat outputFmt = new SimpleDateFormat("MMM dd, yyy h:mm");
        String dateAsString = outputFmt.format(time);
        Log.e("milli", String.valueOf(myDate.getTime()));
        return myDate.getTime();
    }

    public static String UTCMilliseondsToDate(long dateInMillis){
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dateInMillis);

        DateFormat formatter = new SimpleDateFormat(DateTimeConstants.DATE_FORMAT);

       String timeStr =  formatter.format(calendar.getTime());

        return timeStr;

    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat);
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(DateTimeConstants.DATE_FORMAT);

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOG.e(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }

}
