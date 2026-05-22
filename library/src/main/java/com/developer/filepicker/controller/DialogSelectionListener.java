package com.developer.filepicker.controller;

/**
 * Callback invoked when the user confirms selected file/folder paths.
 */
public interface DialogSelectionListener {
    void onSelectedFilePaths(String[] files);
}
