package com.prongbang.startroom.utils;

/**
 * Created by mdev on 10/17/2017 AD.
 */

public class Objects {

    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        if (o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

}
