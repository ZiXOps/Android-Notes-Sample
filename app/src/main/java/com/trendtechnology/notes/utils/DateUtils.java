package com.trendtechnology.notes.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    private DateUtils() {
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date parseDate(String date) {
        Date result = null;
        try {
            result = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Возвращает короткую запись даты или времени в зависимости от разницы времени параметра и
     * настоящего времени.
     *
     * @param displayDate - Дата которую необходимо привести к короткой форме.
     * @return - короткую строку с датой. Например: "3:35" | "7 апр." | "2015"
     */
    public static String getShortDateDiff(Date displayDate) {
        return getShortDateDiff(displayDate, new Date());
    }

    /**
     * Возвращает короткую запись даты или времени в зависимости от разницы времени в параметрах.
     *
     * @param displayDate - Дата которую необходимо привести к короткой форме.
     * @param currentDate - Текущая дата.
     * @return - короткую строку с датой. Например: "3:35" | "7 апр." | "2015"
     */
    public static String getShortDateDiff(Date displayDate, Date currentDate) {
        // TODO: Дописать вывод записи в часх и годах.
        SimpleDateFormat shortDateFormat = new SimpleDateFormat( "d LLL", Locale.getDefault() );
        return shortDateFormat.format(displayDate);
    }

}
