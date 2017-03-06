package com.holly.job.spring.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateTimeUtils {

    /**
     * 根据规则格式化时间
     * 
     * @Title: fomat
     * @Description: TODO
     * @param date
     * @param pattern
     * @return
     * @return: String
     */
    public static String fomat(String date, String pattern) {

	SimpleDateFormat format = new SimpleDateFormat(pattern);

	return format.format(new Date(Long.valueOf(date)));

    }
}
