package com.fundfun.fundfund.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static class date {
        public static String getCurDateFormatted(String pattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(new Date());
        }
    }
}
