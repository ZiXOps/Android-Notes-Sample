package com.trendtechnology.notes.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    private DateUtils() {
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static Date parseDate(String date) {
        Date result = null;
        try {
            result = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Возвращает короткую запись даты или времени в зависимости от разницы времени параметра и
     * настоящего времени.
     *
     * @param displayDate Дата которую необходимо привести к короткой форме.
     * @return короткую строку с датой. Например: "3:35" | "7 апр." | "2015"
     */
    public static String getShortDateDiff(Date displayDate) {
        return getShortDateDiff(displayDate, new Date());
    }

    /**
     * Возвращает короткую запись даты или времени в зависимости от разницы времени в параметрах.
     *
     * @param displayDate Дата которую необходимо привести к короткой форме.
     * @param currentDate Текущая дата.
     * @return короткую строку с датой. Например: "3:35" | "7 апр." | "2015"
     */
    public static String getShortDateDiff(Date displayDate, Date currentDate) {
        String result;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(displayDate);
        int displayYear = calendar.get(Calendar.YEAR);
        calendar.setTime(currentDate);
        int currentYear = calendar.get(Calendar.YEAR);

        long timeDiff = Math.abs(currentDate.getTime() - displayDate.getTime());
        final long DAY_IN_MS = 8640000;

        if (displayYear != currentYear ) {
            SimpleDateFormat shortDateFormat = new SimpleDateFormat( "yyyy", Locale.getDefault() );
            result = shortDateFormat.format(displayDate);
        } else if (timeDiff > DAY_IN_MS ) {
            SimpleDateFormat shortDateFormat = new SimpleDateFormat( "d MMM", Locale.getDefault() );
            result = shortDateFormat.format(displayDate);
        } else {
            SimpleDateFormat shortDateFormat = new SimpleDateFormat( "H:mm", Locale.getDefault() );
            result = shortDateFormat.format(displayDate);
        }

        return result;
    }

}
