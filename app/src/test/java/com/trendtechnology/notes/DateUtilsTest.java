package com.trendtechnology.notes;

import com.trendtechnology.notes.utils.DateUtils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {
    @Test
    public void shortDateDiff_Day_isCorrect() throws Exception {
        Date displayDate = DateUtils.parseDate("12/04/1961 09:07:00");
        Date currentDate = DateUtils.parseDate("12/04/1961 09:55:00");
        assertEquals(DateUtils.getShortDateDiff(displayDate, currentDate), "9:07");
    }
    @Test
    public void shortDateDiff_Month_isCorrect() throws Exception {
        Date displayDate = DateUtils.parseDate("12/04/1961 09:07:00");
        Date currentDate = DateUtils.parseDate("13/04/1961 09:07:00");
        assertEquals(DateUtils.getShortDateDiff(displayDate, currentDate), "12 апр");
    }
    @Test
    public void shortDateDiff_Year_isCorrect() throws Exception {
        Date displayDate = DateUtils.parseDate("12/04/1961 09:07:00");
        Date currentDate = DateUtils.parseDate("12/04/2016 09:07:00");
        assertEquals(DateUtils.getShortDateDiff(displayDate, currentDate), "1961");
    }
}
