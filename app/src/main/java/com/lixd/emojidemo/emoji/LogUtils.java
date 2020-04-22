package com.lixd.emojidemo.emoji;

import android.util.Log;
import android.widget.TextView;

public class LogUtils {

    private TextView logView;

    private LogUtils() {
    }

    public static final LogUtils getInstance() {
        return Holer.INSTANCE;
    }

    private static class Holer {
        private static final LogUtils INSTANCE = new LogUtils();
    }

    public void log(String log) {
        Log.e("LogUtils", log);
        logToView(log);
    }

    public void log(String tag, String log) {
        Log.e(tag, log);
        logToView(log);
    }

    private void logToView(String log) {
        if (logView == null) {
            return;
        }
        logView.append("\n");
        logView.append(log);
    }

    public void bindLogView(TextView logView) {
        this.logView = logView;
    }
}
