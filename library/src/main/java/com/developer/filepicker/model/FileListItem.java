package com.developer.filepicker.model;

import java.util.Locale;

/**
 * Represents one row in the picker list.
 */
public class FileListItem implements Comparable<FileListItem> {

    private String filename;
    private String location;
    private boolean directory;
    private boolean marked;
    private long time;

    public String getFilename() {
        return filename == null ? "" : filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLocation() {
        return location == null ? "" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public int compareTo(FileListItem other) {
        if (other == null) {
            return -1;
        }

        if (directory && !other.directory) {
            return -1;
        }
        if (!directory && other.directory) {
            return 1;
        }

        return getFilename()
                .toLowerCase(Locale.ROOT)
                .compareTo(other.getFilename().toLowerCase(Locale.ROOT));
    }
}
