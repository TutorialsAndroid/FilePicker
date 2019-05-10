package com.github.tutorialsandroid.filepicker;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

public class FilePickerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
