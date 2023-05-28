package com.fundfun.fundfund.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

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

    public static String findProfileImg(UUID id) throws IOException {
        String directoryPath = "src/main/resources/static/img/profiles";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            throw new IllegalArgumentException("디렉토리를 찾을 수 없습니다: " + directoryPath);
        }

        File[] files = directory.listFiles();
        String fileName = "default.jpeg";
        if (files == null) {
            throw new IOException("디렉토리 내 파일을 읽어올 수 없습니다: " + directoryPath);
        }
        String fileNamePattern = id.toString().replace("-","");

        for (File file : files) {
            if (file.isFile() && file.getName().split("\\.")[0].equals(fileNamePattern)) {
                fileName = file.getName();
                break;
            }
        }
        return fileName.replace("-","");
    }

}
