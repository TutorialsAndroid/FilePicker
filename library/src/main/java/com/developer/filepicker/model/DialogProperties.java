package com.developer.filepicker.model;

import java.io.File;

/**
 * Public configuration object used by FilePickerDialog.
 *
 * Existing public fields are kept for backward compatibility with v9.x users.
 */
public class DialogProperties {

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
    }
}
