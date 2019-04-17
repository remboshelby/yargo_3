package yargo.inc.common.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeTransformer {
    public DateTimeTransformer() {
    }

    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }
    public static boolean isTomorrow(Date d) {
        return DateUtils.isToday(d.getTime() - DateUtils.DAY_IN_MILLIS);
    }
    public static String getMonth(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String monthName = new SimpleDateFormat("dd MMMM").format(cal.getTime());
        return monthName;
    }
    public String dateCreator(String date_){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.ENGLISH);
        format.setLenient(false);
        SimpleDateFormat output_format_time = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        try {
            date = format.parse(date_);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date_1 =null;
        if (DateUtils.isToday(date.getTime())){
            date_1 = "Сегодня, " + output_format_time.format(date);
        }else if(isYesterday(date)){
            date_1 = "Вчера, " + output_format_time.format(date);
        }else if(isTomorrow(date)){
            date_1 = "Завтра, " + output_format_time.format(date);
        }else if (date_1==null){
            try {
                String t = getMonth(date);
                date_1 = t + ", " + output_format_time.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date_1;
        }
        return date_1;
    }
}
