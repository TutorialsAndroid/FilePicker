package com.developer.filepicker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.developer.filepicker.model.FileListItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * @author akshay sunil masram
 */
public class Utility {

    public static boolean checkStorageAccessPermissions(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String permission = "android.permission.READ_EXTERNAL_STORAGE";
            int res = context.checkCallingOrSelfPermission(permission);
            return (res == PackageManager.PERMISSION_GRANTED);
        }
        else {
            return true;
        }
    }

    @android.annotation.TargetApi(Build.VERSION_CODES.TIRAMISU)
    public static boolean checkMediaAccessPermissions(Context context) {
        String audioPermission = Manifest.permission.READ_MEDIA_AUDIO;
        String imagesPermission = Manifest.permission.READ_MEDIA_IMAGES;
        String videoPermission = Manifest.permission.READ_MEDIA_VIDEO;
        // Check for permissions and if permissions are granted then it will return true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // You have the permissions, you can proceed with your media file operations.
            return context.checkSelfPermission(audioPermission) == PackageManager.PERMISSION_GRANTED ||
                    context.checkSelfPermission(imagesPermission) == PackageManager.PERMISSION_GRANTED ||
                    context.checkSelfPermission(videoPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public static ArrayList<FileListItem>
    prepareFileListEntries(ArrayList<FileListItem> internalList, File inter,
                           ExtensionFilter filter, boolean show_hidden_files) {
        try {
            for (File name : Objects.requireNonNull(inter.listFiles(filter))) {
                if (name.canRead()) {
                    if(name.getName().startsWith(".") && !show_hidden_files) continue;
                    FileListItem item = new FileListItem();
                    item.setFilename(name.getName());
                    item.setDirectory(name.isDirectory());
                    item.setLocation(name.getAbsolutePath());
                    item.setTime(name.lastModified());
                    internalList.add(item);
                }
            }
            Collections.sort(internalList);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            internalList=new ArrayList<>();
        }
        return internalList;
    }
}
