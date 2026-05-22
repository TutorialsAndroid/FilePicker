package com.developer.filepicker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.model.FileListItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Storage and file-list utilities for the picker.
 */
public final class Utility {

    private Utility() {
        // No instances.
    }

    /**
     * Backward-compatible API retained from older versions.
     */
    public static boolean checkStorageAccessPermissions(Context context) {
        return hasStorageAccess(context, null);
    }

    /**
     * Backward-compatible media permission check retained from v9.x.
     */
    public static boolean checkMediaAccessPermissions(Context context) {
        if (context == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return context.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                    || context.checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
                    || context.checkSelfPermission(Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }
        return checkStorageAccessPermissions(context);
    }

    /**
     * Returns true when the app has the best available path-based read access.
     */
    public static boolean hasStorageAccess(Context context, DialogProperties properties) {
        if (context == null) {
            return false;
        }

        boolean allowManageAllFiles = properties == null || properties.allow_manage_external_storage;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && allowManageAllFiles && Environment.isExternalStorageManager()) {
            return true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return checkMediaAccessPermissions(context);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    /**
     * Runtime permissions that the host Activity can request directly.
     * MANAGE_EXTERNAL_STORAGE must be granted from Settings, not from a normal runtime dialog.
     */
    public static String[] getRuntimeReadPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
            };
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }
        return new String[0];
    }

    public static void requestRuntimeReadPermissions(Activity activity, int requestCode) {
        if (activity == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        String[] permissions = getRuntimeReadPermissions();
        if (permissions.length > 0) {
            activity.requestPermissions(permissions, requestCode);
        }
    }

    public static void openManageAllFilesAccessSettings(Context context) {
        if (context == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            return;
        }
        Intent intent;
        try {
            intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        } catch (Exception exception) {
            intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static ArrayList<FileListItem> prepareFileListEntries(ArrayList<FileListItem> internalList,
                                                                 File directory,
                                                                 ExtensionFilter filter,
                                                                 boolean showHiddenFiles) {
        ArrayList<FileListItem> result = internalList == null ? new ArrayList<>() : internalList;
        if (directory == null || !directory.exists() || !directory.isDirectory() || !directory.canRead()) {
            return result;
        }

        File[] files = filter == null ? directory.listFiles() : directory.listFiles(filter);
        if (files == null) {
            return result;
        }

        for (File file : files) {
            if (file == null || !file.canRead()) {
                continue;
            }
            if (!showHiddenFiles && isHidden(file)) {
                continue;
            }

            FileListItem item = new FileListItem();
            item.setFilename(file.getName());
            item.setDirectory(file.isDirectory());
            item.setLocation(file.getAbsolutePath());
            item.setTime(file.lastModified());
            result.add(item);
        }

        Collections.sort(result);
        return result;
    }

    public static boolean isSameOrChild(File root, File child) {
        if (root == null || child == null) {
            return false;
        }
        try {
            String rootPath = root.getCanonicalPath();
            String childPath = child.getCanonicalPath();
            return childPath.equals(rootPath) || childPath.startsWith(rootPath + File.separator);
        } catch (IOException exception) {
            String rootPath = root.getAbsolutePath();
            String childPath = child.getAbsolutePath();
            return childPath.equals(rootPath) || childPath.startsWith(rootPath + File.separator);
        }
    }

    private static boolean isHidden(File file) {
        return file.isHidden() || file.getName().startsWith(".");
    }
}
