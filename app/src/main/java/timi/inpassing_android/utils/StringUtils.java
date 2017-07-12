package timi.inpassing_android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * String   工具类
 *
 * @autor timi
 * create at 2017/7/12 14:05
 */

public class StringUtils {
    private static final String DEFAULT_FILE_PATTERN = "yyyy-MM-dd-HH-mm-ss";
    private static final String DEFAULT_FILE_PATTERN_TO_WX = "yyyyMMddHHmmss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_PATTERN = "hh:mm";
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private static final String DEFAULT_DATE_PATTERNBYFTP = "yyyyMMdd";
    /**
     * 生成微信支付的时间戳
     *
     * @return
     */
    public static String getWXTime(long current) {
        return formatDate(new Date(current), DEFAULT_FILE_PATTERN_TO_WX);
    }
    /**
     * 格式化日期字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
