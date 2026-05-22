package com.developer.filepicker.model;

import android.os.Environment;

/**
 * File picker configuration constants.
 */
public final class DialogConfigs {

    private DialogConfigs() {
        // No instances.
    }

    public static final int SINGLE_MODE = 0;
    public static final int MULTI_MODE = 1;

    public static final int FILE_SELECT = 0;
    public static final int DIR_SELECT = 1;
    public static final int FILE_AND_DIR_SELECT = 2;

    /**
     * Default shared-storage root. On modern Android versions, access to this
     * path depends on scoped-storage rules and app permissions.
     */
    public static final String DEFAULT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
}
