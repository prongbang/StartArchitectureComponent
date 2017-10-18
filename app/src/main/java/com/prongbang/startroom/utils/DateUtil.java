package com.prongbang.startroom.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mdev on 10/12/2017 AD.
 */

public class DateUtil {

    /**
     * To YYYY-MM-DD
     *
     * @param date
     * @return
     */
    public static String toYyyyMmDd(Date date) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        return format1.format(date.getTime());
    }

    /**
     * To Date
     *
     * @param yyyyMmDd
     * @return
     */
    public static Date toDate(String yyyyMmDd) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = new Date();
        try {
            date = format.parse(yyyyMmDd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
