package com.developer.filepicker.model;

import android.graphics.Color;

import java.io.File;

/**
 * Public configuration object used by FilePickerDialog.
 *
 * Existing public fields are kept for backward compatibility with v9.x users.
 */
public class DialogProperties {

    /**
     * Internal sentinel used when a color should fall back to the library default.
     */
    public static final int COLOR_NOT_SET = Integer.MIN_VALUE;

    public int selection_mode;
    public int selection_type;
    public File root;
    public File error_dir;
    public File offset;
    public String[] extensions;
    public boolean show_hidden_files;

    /**
     * When true, the dialog will treat MANAGE_EXTERNAL_STORAGE as valid access
     * on Android 11+ if the host app declares it and the user has granted it.
     */
    public boolean allow_manage_external_storage;

    /**
     * When true, the dialog shows user-friendly toasts if required storage
     * access is missing instead of failing silently.
     */
    public boolean show_permission_error_toast;

    /**
     * Maximum allowed file size in bytes.
     *
     * Default value -1 means no maximum size filter.
     */
    public long max_file_size = -1L;

    /**
     * Minimum allowed file size in bytes.
     *
     * Default value -1 means no minimum size filter.
     */
    public long min_file_size = -1L;

    /**
     * Checked checkbox fill color.
     *
     * Default COLOR_NOT_SET means the picker will use R.color.colorAccent.
     */
    public int checkbox_checked_color = COLOR_NOT_SET;

    /**
     * Unchecked checkbox outer/background color.
     */
    public int checkbox_unchecked_color = Color.parseColor("#C1C1C1");

    /**
     * Checked checkbox tick/checkmark color.
     */
    public int checkbox_checkmark_color = Color.WHITE;

    /**
     * Unchecked checkbox inner fill color.
     *
     * Change this if the unchecked checkbox blends into a white/light background.
     */
    public int checkbox_unchecked_inner_color = Color.WHITE;

    public DialogProperties() {
        selection_mode = DialogConfigs.SINGLE_MODE;
        selection_type = DialogConfigs.FILE_SELECT;
        root = new File(DialogConfigs.DEFAULT_DIR);
        error_dir = new File(DialogConfigs.DEFAULT_DIR);
        offset = new File(DialogConfigs.DEFAULT_DIR);
        extensions = null;
        show_hidden_files = false;

        // v10.1.2 file size filters
        max_file_size = -1L;
        min_file_size = -1L;

        // v10.1.3 checkbox color customization
        checkbox_checked_color = COLOR_NOT_SET;
        checkbox_unchecked_color = Color.parseColor("#C1C1C1");
        checkbox_checkmark_color = Color.WHITE;
        checkbox_unchecked_inner_color = Color.WHITE;
    }
}
