package com.zk.function.eCharts.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class DateUtil {
    public static final String Y = "yyyy";
    public static final String dd = "dd";
    public static final String MD = "MM-dd";
    public static final String MDD = "MMdd";
    public static final String M = "MM";
    public static final String MM = "yyyyMM";
    public static final String DD = "yyyyMMdd";
    public static final String H = "HH:00";
    public static final String HM = "HH:mm";
    public static final String HMS = "HH:mm:ss";
    public static final String YM = "yyyy-MM";
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMDH = "yyyy-MM-dd HH";
    public static final String YMDHM = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String TABLENAME = "yyyyMMdd";
    public static final String yyyymmddhhmmss = "yyyyMMddHHmmss";
    public static final String yyyymmddhhmmssSSS = "yyyyMMddHHmmssSSS";
    private static final GregorianCalendar GC = new GregorianCalendar();

    public DateUtil() {
    }

    public static Date parse(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return format.parse(date);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);

        try {
            return fmt.parse(date);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Date parse(Date date, String format) {
        String dateString = format(date, format);
        Date finnalDate = parse(dateString, format);
        return finnalDate;
    }

    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    public static List<Object> getTimeSpace(Date startTime, Date endTime, int cycle, int type, String format) {
        ArrayList resultList;
        for(resultList = new ArrayList(); startTime.before(endTime); startTime = subtract(startTime, cycle, type)) {
            resultList.add(format(startTime, format));
        }

        return resultList;
    }

    public static Date subtract(Date date, int offset, int type) {
        GC.setTime(date);
        GC.add(type, offset);
        return GC.getTime();
    }

    public static List<Object> getOneDayTimeSpace(int cycle, int type, String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        Date start = cal.getTime();
        cal.add(5, 1);
        Date end = cal.getTime();
        return getTimeSpace(start, end, cycle, type, format);
    }

    public static String[] getXAxisDay(String time) {
        int count = getDaysOfMonth(time);
        ArrayList XNames = new ArrayList();

        int i;
        for(int XValue = 1; XValue < count + 1; ++XValue) {
            i = String.valueOf(XValue).length();
            if(i == 1) {
                XNames.add("0" + XValue + " 日");
            } else {
                XNames.add(XValue + " 日");
            }
        }

        String[] var5 = new String[XNames.size()];

        for(i = 0; i < XNames.size(); ++i) {
            var5[i] = (String)XNames.get(i);
        }

        return var5;
    }

    public static String[] getXAxisMonth() {
        ArrayList XNames = new ArrayList();

        int i;
        for(int XValue = 1; XValue < 13; ++XValue) {
            i = String.valueOf(XValue).length();
            if(i == 1) {
                XNames.add("0" + XValue + " 月");
            } else {
                XNames.add(XValue + " 月");
            }
        }

        String[] var3 = new String[XNames.size()];

        for(i = 0; i < XNames.size(); ++i) {
            var3[i] = (String)XNames.get(i);
        }

        return var3;
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static int getDaysOfMonth(String str) {
        int count = 0;

        try {
            Calendar e = Calendar.getInstance();
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");
            e.setTime(simpleDate.parse(str));
            count = e.getActualMaximum(5);
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return count;
    }

    public static String getDelayDay(String nowdate, String delay) {
        try {
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = d.getTime() / 1000L + (long)(Integer.parseInt(delay) * 24 * 60 * 60);
            d.setTime(myTime * 1000L);
            mdate = e.format(d);
            return mdate;
        } catch (Exception var7) {
            return "";
        }
    }

    public static List<String> getSpell(Date start, int type, int num, boolean add, String format) {
        ArrayList spell = new ArrayList();
        Calendar calendar = Calendar.getInstance();

        for(int i = num; i >= 1; --i) {
            calendar.setTime(start);
            if(add) {
                calendar.add(type, i);
            } else {
                calendar.add(type, -i);
            }

            String formatTime = format(calendar.getTime(), format);
            spell.add(formatTime);
        }

        return spell;
    }

    public static Long getTimeGap(Date start, Date end) {
        Long startTime = Long.valueOf(start.getTime());
        Long endTime = Long.valueOf(end.getTime());
        Long timeGap = Long.valueOf((startTime.longValue() - endTime.longValue()) / 60000L);
        return timeGap;
    }

    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        cal.set(5, 1);
        int value = cal.getActualMaximum(5);
        cal.set(5, value);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
    }

    public static void main(String[] args) {
        List oneDayTimeSpace = getOneDayTimeSpace(5, 12, "HH:mm");
        Iterator i$ = oneDayTimeSpace.iterator();

        while(i$.hasNext()) {
            Object o = i$.next();
            System.out.println(String.valueOf(o));
        }

        System.out.println(oneDayTimeSpace.size());
    }
}
