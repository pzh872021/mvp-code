package com.mvp.kit;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Kits {

    public static class Package {
        /**
         * 获取版本号
         *
         * @param context
         * @return
         */
        public static int getVersionCode(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionCode;
        }

        /**
         * 获取当前版本
         *
         * @param context
         * @return
         */
        public static String getVersionName(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionName;
        }

        /**
         * 安装App
         *
         * @param context
         * @param filePath
         * @return
         */
        public static boolean installNormal(Context context, String filePath) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            File file = new File(filePath);
            if (!file.exists() || !file.isFile() || file.length() <= 0) {
                return false;
            }

            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * 卸载App
         *
         * @param context
         * @param packageName
         * @return
         */
        public static boolean uninstallNormal(Context context, String packageName) {
            if (packageName == null || packageName.length() == 0) {
                return false;
            }

            Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder().append("package:")
                    .append(packageName).toString()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * 判断是否是系统App
         *
         * @param context
         * @param packageName 包名
         * @return
         */
        public static boolean isSystemApplication(Context context, String packageName) {
            if (context == null) {
                return false;
            }
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || packageName == null || packageName.length() == 0) {
                return false;
            }

            try {
                ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
                return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 判断某个包名是否运行在顶层
         *
         * @param context
         * @param packageName
         * @return
         */
        public static Boolean isTopActivity(Context context, String packageName) {
            if (context == null || TextUtils.isEmpty(packageName)) {
                return null;
            }

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo == null || tasksInfo.isEmpty()) {
                return null;
            }
            try {
                return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 获取Meta-Data
         *
         * @param context
         * @param key
         * @return
         */
        public static String getAppMetaData(Context context, String key) {
            if (context == null || TextUtils.isEmpty(key)) {
                return null;
            }
            String resultData = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            resultData = applicationInfo.metaData.getString(key);
                        }
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return resultData;
        }

        /**
         * 判断当前应用是否运行在后台
         *
         * @param context
         * @return
         */
        public static boolean isApplicationInBackground(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
            if (taskList != null && !taskList.isEmpty()) {
                ComponentName topActivity = taskList.get(0).topActivity;
                if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class Dimens {

        public static float dpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }

        public static float pxToDp(Context context, float px) {
            return px / context.getResources().getDisplayMetrics().density;
        }

        public static int dpToPxInt(Context context, float dp) {
            return (int) (dpToPx(context, dp) + 0.5f);
        }

        public static int pxToDpCeilInt(Context context, float px) {
            return (int) (pxToDp(context, px) + 0.5f);
        }
    }

    public static class Empty {

        public static boolean check(Object obj) {
            return obj == null;
        }

        public static boolean check(List list) {
            return list == null || list.isEmpty();
        }

        public static boolean check(Object[] array) {
            return array == null || array.length == 0;
        }

        public static boolean check(String str) {
            return str == null || "".equals(str);
        }

        public static boolean check(Map map) {
            return map == null || map.isEmpty();
        }

        public static boolean check(Set set) {
            return set == null || set.isEmpty();
        }

    }

    public static class Date {
        private static final SimpleDateFormat m = new SimpleDateFormat("MM", Locale.getDefault());
        private static final SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());
        private static final SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.getDefault());
        private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        private static final SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        private static final SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        private static final SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        private static final SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        private static final SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        private static final SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
        private static final SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

        /**
         * 年月日[2015-07-28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmd(long timeInMills) {
            return ymd.format(new java.util.Date(timeInMills));
        }

        /**
         * 年月日[2015.07.28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmdDot(long timeInMills) {
            return ymdDot.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhms(long timeInMills) {
            return ymdhms.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhmsS(long timeInMills) {
            return ymdhmss.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhm(long timeInMills) {
            return ymdhm.format(new java.util.Date(timeInMills));
        }

        public static String getHm(long timeInMills) {
            return hm.format(new java.util.Date(timeInMills));
        }

        public static String getMd(long timeInMills) {
            return md.format(new java.util.Date(timeInMills));
        }

        public static String getMdhm(long timeInMills) {
            return mdhm.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmLink(long timeInMills) {
            return mdhmLink.format(new java.util.Date(timeInMills));
        }

        public static String getM(long timeInMills) {
            return m.format(new java.util.Date(timeInMills));
        }

        public static String getD(long timeInMills) {
            return d.format(new java.util.Date(timeInMills));
        }

        /**
         * 是否是今天
         *
         * @param timeInMills
         * @return
         */
        public static boolean isToday(long timeInMills) {
            String dest = getYmd(timeInMills);
            String now = getYmd(Calendar.getInstance().getTimeInMillis());
            return dest.equals(now);
        }

        /**
         * 是否是同一天
         *
         * @param aMills
         * @param bMills
         * @return
         */
        public static boolean isSameDay(long aMills, long bMills) {
            String aDay = getYmd(aMills);
            String bDay = getYmd(bMills);
            return aDay.equals(bDay);
        }

        /**
         * 获取年份
         *
         * @param mills
         * @return
         */
        public static int getYear(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.YEAR);
        }

        /**
         * 获取月份
         *
         * @param mills
         * @return
         */
        public static int getMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.MONTH) + 1;
        }


        /**
         * 获取月份的天数
         *
         * @param mills
         * @return
         */
        public static int getDaysInMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            switch (month) {
                case Calendar.JANUARY:
                case Calendar.MARCH:
                case Calendar.MAY:
                case Calendar.JULY:
                case Calendar.AUGUST:
                case Calendar.OCTOBER:
                case Calendar.DECEMBER:
                    return 31;
                case Calendar.APRIL:
                case Calendar.JUNE:
                case Calendar.SEPTEMBER:
                case Calendar.NOVEMBER:
                    return 30;
                case Calendar.FEBRUARY:
                    return (year % 4 == 0) ? 29 : 28;
                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }


        /**
         * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
         *
         * @param mills
         * @return
         */
        public static int getWeek(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        /**
         * 获取当月第一天的时间（毫秒值）
         *
         * @param mills
         * @return
         */
        public static long getFirstOfMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            return calendar.getTimeInMillis();
        }

    }

    public static class Random {
        public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String NUMBERS = "0123456789";
        public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

        public static String getRandomNumbersAndLetters(int length) {
            return getRandom(NUMBERS_AND_LETTERS, length);
        }

        public static String getRandomNumbers(int length) {
            return getRandom(NUMBERS, length);
        }

        public static String getRandomLetters(int length) {
            return getRandom(LETTERS, length);
        }

        public static String getRandomCapitalLetters(int length) {
            return getRandom(CAPITAL_LETTERS, length);
        }

        public static String getRandomLowerCaseLetters(int length) {
            return getRandom(LOWER_CASE_LETTERS, length);
        }

        public static String getRandom(String source, int length) {
            return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
        }

        public static String getRandom(char[] sourceChar, int length) {
            if (sourceChar == null || sourceChar.length == 0 || length < 0) {
                return null;
            }

            StringBuilder str = new StringBuilder(length);
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < length; i++) {
                str.append(sourceChar[random.nextInt(sourceChar.length)]);
            }
            return str.toString();
        }

        public static int getRandom(int max) {
            return getRandom(0, max);
        }

        public static int getRandom(int min, int max) {
            if (min > max) {
                return 0;
            }
            if (min == max) {
                return min;
            }
            return min + new java.util.Random().nextInt(max - min);
        }
    }

    public static class ParseNumber {

        public static int getInt(String res, int defeatValues) {
            try {
                return Integer.parseInt(res);
            } catch (Exception e) {
                return defeatValues;
            }
        }

        public static int getInt(String res) {
            try {
                return Integer.parseInt(res);
            } catch (Exception e) {
                return 0;
            }
        }

        public static double getDouble(String res, double defeatValues) {
            try {
                return Double.parseDouble(res);
            } catch (Exception e) {
                return defeatValues;
            }
        }

        public static double getDouble(String res) {
            try {
                return Double.parseDouble(res);
            } catch (Exception e) {
                return 0;
            }
        }

        public static float getFloat(String res, float defeatValues) {
            try {
                return Float.parseFloat(res);
            } catch (Exception e) {
                return defeatValues;
            }
        }

        public static float getFloat(String res) {
            try {
                return Float.parseFloat(res);
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public static class MathArith {

        /**
         * 保留两位小数
         * 传double的时候 精度会损失。。所以tostring
         */
        public static float truncate(float v1, int number) {
            BigDecimal bg = new BigDecimal(Float.toString(v1)).setScale(number, RoundingMode.DOWN);
            return bg.floatValue();
        }

        /**
         * 保留两位小数
         * 传double的时候 精度会损失。。所以tostring
         */
        public static double truncate(double v1, int number) {
            BigDecimal bg = new BigDecimal(Double.toString(v1)).setScale(number, RoundingMode.DOWN);
            return bg.doubleValue();
        }


        /**
         * 两个 double 相减    避免   2.0 - 1.1 问题
         */
        public static float subtract(float v1, float v2) {
            BigDecimal b1 = new BigDecimal(Float.toString(v1));
            BigDecimal b2 = new BigDecimal(Float.toString(v2));
            return b1.subtract(b2).floatValue();
        }


        /**
         * 两个 double 相减    避免   2.0 - 1.1 问题
         */
        public static double subtract(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.subtract(b2).doubleValue();
        }

        /**
         * 两个 float 相加  避免  3.6 + 0.4 问题
         */
        public static float add(float v1, float v2) {
            BigDecimal b1 = new BigDecimal(Float.toString(v1));
            BigDecimal b2 = new BigDecimal(Float.toString(v2));
            return b1.add(b2).floatValue();
        }

        /**
         * 两个 double 相加  避免  3.6 + 0.4 问题
         */
        public static BigDecimal add(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.add(b2);
        }

        /**
         * 乘法
         */
        public static BigDecimal multiply(float v1, float v2) {
            BigDecimal b1 = new BigDecimal(Float.toString(v1));
            BigDecimal b2 = new BigDecimal(Float.toString(v2));
            return b1.multiply(b2);
        }

        public static BigDecimal multiply(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.multiply(b2);
        }

        public static BigDecimal multiplyBig(BigDecimal b1, double v2) {
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.multiply(b2);
        }

        /**
         * 除法
         */
        public static float divide(float v1, float v2) {
            BigDecimal b1 = new BigDecimal(Float.toString(v1));
            BigDecimal b2 = new BigDecimal(Float.toString(v2));
            return b1.divide(b2).setScale(2).floatValue();
        }

        public static BigDecimal divide(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2);
        }

    }
}
