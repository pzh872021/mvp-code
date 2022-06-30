package com.mvp.kit;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Keep;

import org.json.JSONArray;
import org.json.JSONObject;


@Keep
public final class Logger {

    private static String sTag = "logger";

    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARN = 3;
    public static final int LEVEL_ERROR = 4;

    public static final int JSON_INDENT = 2;
    public static final int MIN_STACK_OFFSET = 3;

    public static int logLevel = LEVEL_DEBUG;

    private static boolean is_debug = true;

    private Logger() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void v(String msg) {
        v(msg, sTag);
    }

    public static void d(String msg) {
        d(msg, sTag);
    }

    public static void i(String msg) {
        i(msg, sTag);
    }

    public static void w(String msg) {
        w(msg, sTag);
    }

    public static void e(String msg) {
        e(msg, sTag);
    }

    public static void e(Object e) {
        if (is_debug) {
            if (e == null) {
                e("input object is null");
            } else {
                e(e.toString());
            }
        }
    }

    public static void v(String msg, String tag) {
        if (is_debug) {
            if (LEVEL_VERBOSE >= logLevel && !TextUtils.isEmpty(msg)) {
                String s = getMethodNames();
                Log.v(tag, String.format(s, msg));
            }
        }
    }

    public static void d(String msg, String tag) {
        if (is_debug) {
            if (LEVEL_DEBUG >= logLevel && !TextUtils.isEmpty(msg)) {
                String s = getMethodNames();
                Log.d(tag, String.format(s, msg));
            }
        }
    }

    public static void i(String msg, String tag) {
        if (is_debug) {
            if (LEVEL_INFO >= logLevel && !TextUtils.isEmpty(msg)) {
                String s = getMethodNames();
                Log.d(tag, String.format(s, msg));
            }
        }
    }

    public static void w(String msg, String tag) {
        if (is_debug) {
            if (LEVEL_WARN >= logLevel && !TextUtils.isEmpty(msg)) {
                String s = getMethodNames();
                Log.w(tag, String.format(s, msg));
            }
        }
    }

    public static void e(String msg, String tag) {
        if (is_debug) {
            if (LEVEL_ERROR >= logLevel) {
                if (TextUtils.isEmpty(msg)) {
                    msg = "input message is null";
                }
                String s = getMethodNames();
                Log.e(tag, String.format(s, msg));
            }
        }
    }

    public static void json(String json) {
        if (is_debug) {
            try {
                json = json.trim();
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    String message = jsonObject.toString(JSON_INDENT);
                    message = message.replaceAll("\n", "\n║ ");
                    String s = getMethodNames();
                    e(String.format(s, message));
                } else if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    String message = jsonArray.toString(JSON_INDENT);
                    message = message.replaceAll("\n", "\n║ ");
                    String s = getMethodNames();
                    e(String.format(s, message));
                }
            } catch (Exception e) {
                x(e);
            }
        }
    }

    private static String getMethodNames() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(stackTrace);
        stackOffset++;
        return stackTrace[stackOffset].getMethodName() +
                "(" + stackTrace[stackOffset].getFileName() +
                ":" + stackTrace[stackOffset].getLineNumber() +
                ") " + "%s";
    }

    private static int getStackOffset(StackTraceElement... trace) {
        int i = MIN_STACK_OFFSET;
        while (i < trace.length) {
            String name = trace[i].getClassName();
            if (!Logger.class.getName().equalsIgnoreCase(name)) {
                return --i;
            }
            i++;
        }
        return -1;
    }

    public static void x(Exception e) {
        if (is_debug) {
            StringBuilder sb = new StringBuilder();

            StackTraceElement[] stackTrace = e.getStackTrace();

            sb.append(">>>>>>>>>>      ").append(e).append(" at :     <<<<<<<<<<").append("\n");
            for (int i = 0; i < stackTrace.length; i++) {

                if (i < 100) {
                    StackTraceElement stackTraceElement = stackTrace[i];
                    String errorMsg = stackTraceElement.toString();
                    sb.append(errorMsg).append("\n");
                } else {
                    sb.append("more : ").append(stackTrace.length - 100).append("...").append("\n");
                    break;
                }
            }
            sb.append(">>>>>>>>>>     end of error     <<<<<<<<<<");
            e(sb.toString());
        }
    }
}