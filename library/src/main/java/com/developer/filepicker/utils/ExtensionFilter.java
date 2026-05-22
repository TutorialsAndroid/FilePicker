package com.developer.filepicker.utils;

import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

/**
 * Filters visible files based on selection type and allowed extensions.
 */
public class ExtensionFilter implements FileFilter {

    private final String[] validExtensions;
    private final DialogProperties properties;

    public ExtensionFilter(DialogProperties properties) {
        this.properties = properties == null ? new DialogProperties() : properties;
        this.validExtensions = normalizeExtensions(this.properties.extensions);
    }

    @Override
    public boolean accept(File file) {
        if (file == null) {
            return false;
        }

        if (file.isDirectory()) {
            return file.canRead();
        }

        if (properties.selection_type == DialogConfigs.DIR_SELECT) {
            return false;
        }

        if (!file.canRead()) {
            return false;
        }

        if (validExtensions.length == 0) {
            return true;
        }

        String name = file.getName().toLowerCase(Locale.ROOT);
        for (String extension : validExtensions) {
            if ("*".equals(extension) || name.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    private static String[] normalizeExtensions(String[] extensions) {
        if (extensions == null || extensions.length == 0) {
            return new String[0];
        }

        int count = 0;
        String[] temp = new String[extensions.length];
        for (String extension : extensions) {
            if (extension == null) {
                continue;
            }
            String ext = extension.trim().toLowerCase(Locale.ROOT);
            if (ext.isEmpty()) {
                continue;
            }
            if ("*".equals(ext) || ".*".equals(ext)) {
                temp[count++] = "*";
            } else {
                temp[count++] = ext.startsWith(".") ? ext : "." + ext;
            }
        }

        String[] result = new String[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }
}
