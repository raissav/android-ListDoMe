package com.listdome.app.infrastructure;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

}
