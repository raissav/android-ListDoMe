package com.listdome.app.infrastructure;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by raissa on 08/12/2017.
 */

public final class DateHelper {

    private static final String TAG = DateHelper.class.getSimpleName();

    public static final String patternEn = "yyyy-MM-dd";
    public static final String patternPt = "dd/MM/yyyy";

    public static Date parseStringToDate(final String dateStr, final String pattern) {

        final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date = null;

        try {
            if (!TextUtils.isEmpty(dateStr)) {
                date = sdf.parse(dateStr);
            }
        } catch (final ParseException e) {
            Log.e(TAG, "[error] Exception: ", e);
        }

        return date;
    }

    public static String parseDateToString(final Date date, final String pattern) {

        final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        String dateStr = null;

        if (date != null) {
            dateStr = sdf.format(date);
        }

        return dateStr;
    }

    public static Date getWeekDateBegin() {

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK,
                cal.getActualMinimum(Calendar.DAY_OF_WEEK));

        return cal.getTime();
    }

    public static Date getWeekDateEnd() {

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK,
                cal.getActualMaximum(Calendar.DAY_OF_WEEK));

        return cal.getTime();
    }

    public static Date getMonthDateBegin() {

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }

    public static Date getMonthDateEnd() {

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }
}
