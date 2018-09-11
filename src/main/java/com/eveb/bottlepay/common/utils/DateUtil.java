package com.eveb.bottlepay.common.utils;

import com.eveb.bottlepay.common.exception.RRException;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by William on 2018/2/1.
 */
public class DateUtil {
    private static final int step = 3;
    private static final int fullCursor = 2;
    private static final int fullIndex = 4 * 5;
    private static final String MATCH_TEMPLATE = "yyyy/MM/dd HH:mm:ss:SSS";
    private static final String PATTERN_TEMPLATE = "0000/00/00 00:00:00:000";
    public static final String FORMAT_25_DATE_TIME = "yyyy-MM-dd HH:mm:ss.SSSSSS";
    public static final String FORMAT_22_DATE_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_18_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_18_DATE_TIME2 = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT_12_DATE_TIME = "yyyy-MM-dd HH";
    public static final String FORMAT_10_DATE = "yyyy-MM-dd";
    public static final String FORMAT_8_DATE = "yyyyMMdd";
    public static final String FORMAT_DATE_T = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

    /**
     * 获取SimpleDateFormat实例
     *
     * @param pattern 模式串
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        SimpleDateFormat format = local.get();
        format.applyPattern(pattern);
        return format;
    }

    /**
     * 获取本周 周一的日期
     *
     * @param pattern
     * @return
     */
    public static String getMonday(String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern, Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return sf.format(calendar.getTime());
    }

    /**
     * 获取本周 周日的日期
     *
     * @param pattern
     * @return
     */
    public static String getWeek(String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern, Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return sf.format(calendar.getTime());
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(today);
    }

    /**
     * 获取表示当前时间的字符串
     *
     * @param pattern 模式串
     * @return
     */
    public static String getCurrentDate(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 获取表示当前美东时间
     *
     * @return
     */
    public static String getAmericaDate(String pattern, Date date) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        sf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        return sf.format(date);
    }

    /**
     * 日期时间格式化, 自动匹配格式化模式串
     *
     * @param date Date
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat format = getSimpleDateFormat(MATCH_TEMPLATE);
        String dateStr = format.format(date);
        String ms = dateStr.substring(dateStr.length() - step);
        if (Short.parseShort(ms) == 0) {
            dateStr = dateStr.substring(0, dateStr.length() - step - 1);
        } else {
            return dateStr;
        }
        String time = dateStr.substring(dateStr.length() - step * 3 + 1);
        time = time.replace(":", "");
        if (Integer.parseInt(time) == 0) {
            dateStr = dateStr.substring(0, dateStr.length() - step * 3);
        } else {
            return dateStr;
        }
        return dateStr;
    }

    /**
     * 日期时间格式化
     *
     * @param date    Date
     * @param pattern 模式串
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat format = getSimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 解析字符串类型日期, 为参数自动匹配解析模式串
     *
     * @param date 日期字符串
     * @return
     */
    public static Date parse(String date) {
        try {
            String[] mapper = format(date);
            SimpleDateFormat format = getSimpleDateFormat(mapper[0]);
            return format.parse(mapper[1]);
        } catch (ParseException e) {
            throw new RRException("Unparseable date: \"" + date + "\"");
        } catch (Throwable e) {
            throw new RRException(e.getMessage());
        }
    }

    /**
     * 解析字符串类型日期
     *
     * @param date    日期字符串
     * @param pattern 模式串
     * @return
     */
    public static Date parse(String date, String pattern) {
        try {
            SimpleDateFormat format = getSimpleDateFormat(pattern);
            return format.parse(date);
        } catch (Throwable e) {
            throw new RRException(e.getMessage());
        }
    }

    /**
     * 格式化参数日期字符串, 源日期字符填充到预设模板
     *
     * @param date 日期字符串
     * @return
     */
    private static String[] format(String date) {
        char[] origin = date.toCharArray();
        char[] pattern = PATTERN_TEMPLATE.toCharArray();
        char o, p;
        int cursor = 0, j = 0;
        for (int i = 0; i < origin.length; i++, j++) {
            o = origin[i];
            p = pattern[j];
            if (isCursor(o)) {
                if (!isCursor(p)) {
                    moveToNext(pattern, j - 1);
                    j++;
                }
                cursor = j;
            }
            if (isCursor(p)) {
                if (!isCursor(o)) {
                    cursor = j;
                    j++;
                }
            }
            pattern[j] = o;
        }
        j--;
        if (cursor < fullIndex - 1 && j - cursor == 1) {
            moveToNext(pattern, j);
        }
        cursor = pattern.length;
        for (int i = 0; i < fullCursor; i++) {
            if (pattern[cursor - 1] == '0') {
                cursor--;
            } else {
                break;
            }
        }
        if (cursor != pattern.length) {
            char[] target = new char[cursor];
            System.arraycopy(pattern, 0, target, 0, cursor);
            pattern = target;
        }
        char[] match = MATCH_TEMPLATE.toCharArray();
        for (int i = 4; i < fullIndex; i += step) {
            match[i] = pattern[i];
        }
        return new String[]{new String(match), new String(pattern)};
    }

    /**
     * 字符是否为非数值
     *
     * @param ch 被测试的字符
     * @return
     */
    private static boolean isCursor(char ch) {
        if (ch >= '0' && ch <= '9') {
            return false;
        }
        return true;
    }

    /**
     * 后移元素
     *
     * @param pattern 数组
     * @param i       被移动的元素的索引值
     */
    private static void moveToNext(char[] pattern, int i) {
        pattern[i + 1] = pattern[i];
        pattern[i] = '0';
    }

    public static Integer subtractTime(Date expireTime) {
        if (StringUtils.isEmpty(expireTime))
            return 0;
        Long second = (expireTime.getTime() - (new Date()).getTime()) / 1000;
        if (second <= 0) {
            return 0;
        } else {
            return new Integer(String.valueOf(second));
        }
   /*     SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        String nowtime = d.format(new Date());// 按以上格式 将当前时间转换成字符串
        String testtime = "2011-04-01 14:07:35";// 测试时间
        try {
            long result = (d.parse(nowtime).getTime() - d.parse(testtime)
                    .getTime()) / 3600000;// 当前时间减去测试时间
            // 这个的除以1000得到秒，相应的60000得到分，3600000得到小时
            System.out.println("当前时间减去测试时间=" + result + "小时");
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    public static Date addTime(Integer second) {
        return new Date(System.currentTimeMillis() + second * 1000);
    }


    /**
     * 求现在n小时前的时间
     * @param hour
     * @return
     */
    public static String getBrforeTime(Date d,Integer hour){
        DateFormat df=new SimpleDateFormat(FORMAT_18_DATE_TIME);
        Calendar cal=Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.HOUR_OF_DAY,-hour);
        return df.format(cal.getTime());
    }
}
