package com.prongbang.startroom.model;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by mdev on 10/11/2017 AD.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
