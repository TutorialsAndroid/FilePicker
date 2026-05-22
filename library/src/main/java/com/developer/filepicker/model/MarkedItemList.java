package com.developer.filepicker.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Thread-safe selection store. LinkedHashMap keeps selected path order stable.
 */
public final class MarkedItemList {

    private static final Object LOCK = new Object();
    private static final LinkedHashMap<String, FileListItem> SELECTED_ITEMS = new LinkedHashMap<>();

    private MarkedItemList() {
        // No instances.
    }

    public static void addSelectedItem(FileListItem item) {
        if (item == null || item.getLocation().trim().isEmpty()) {
            return;
        }
        synchronized (LOCK) {
            SELECTED_ITEMS.put(item.getLocation(), item);
        }
    }

    public static void removeSelectedItem(String key) {
        if (key == null) {
            return;
        }
        synchronized (LOCK) {
            SELECTED_ITEMS.remove(key);
        }
    }

    public static boolean hasItem(String key) {
        if (key == null) {
            return false;
        }
        synchronized (LOCK) {
            return SELECTED_ITEMS.containsKey(key);
        }
    }

    public static void clearSelectionList() {
        synchronized (LOCK) {
            SELECTED_ITEMS.clear();
        }
    }

    public static void addSingleFile(FileListItem item) {
        if (item == null || item.getLocation().trim().isEmpty()) {
            return;
        }
        synchronized (LOCK) {
            SELECTED_ITEMS.clear();
            SELECTED_ITEMS.put(item.getLocation(), item);
        }
    }

    public static String[] getSelectedPaths() {
        synchronized (LOCK) {
            return SELECTED_ITEMS.keySet().toArray(new String[0]);
        }
    }

    public static int getFileCount() {
        synchronized (LOCK) {
            return SELECTED_ITEMS.size();
        }
    }

    public static Map<String, FileListItem> snapshot() {
        synchronized (LOCK) {
            return new LinkedHashMap<>(SELECTED_ITEMS);
        }
    }
}
