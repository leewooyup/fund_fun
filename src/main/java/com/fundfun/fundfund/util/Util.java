package com.fundfun.fundfund.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static class number {
        public static String formatNumberWithComma(Long number) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(number);
        }
    }
    public static class url {
        public static String encode(String str) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        }
    }
    public static class date {
        public static String getCurDateFormatted(String pattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(new Date());
        }
    }
}
