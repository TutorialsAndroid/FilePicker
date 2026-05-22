package com.developer.filepicker.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.filepicker.R;
import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.controller.adapters.FileListAdapter;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.model.FileListItem;
import com.developer.filepicker.model.MarkedItemList;
import com.developer.filepicker.utils.ExtensionFilter;
import com.developer.filepicker.utils.Utility;
import com.developer.filepicker.widget.MaterialCheckbox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Path-based file picker dialog.
 *
 * Important for Android 11+: arbitrary shared-storage browsing with java.io.File
 * requires MANAGE_EXTERNAL_STORAGE or a narrower root the app can actually read.
 * For Play Store apps that are not file managers, prefer SAF/Photo Picker for
 * user-facing document/media picking.
 */
public class FilePickerDialog extends Dialog implements AdapterView.OnItemClickListener {

    public static final int EXTERNAL_READ_PERMISSION_GRANT = 112;

    private final Context context;
    private Activity activity;
    private ListView listView;
    private TextView dname;
    private TextView dirPath;
    private TextView title;
    private DialogProperties properties;
    private DialogSelectionListener callbacks;
    private ArrayList<FileListItem> internalList;
    private ExtensionFilter filter;
    private FileListAdapter fileListAdapter;
    private Button select;
    private String titleStr;
    private String positiveBtnNameStr;
    private String negativeBtnNameStr;
    private File currentDirectory;

    private int dialogWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private float dialogWidthPercent = -1f;
    private float dialogHeightPercent = -1f;

    public FilePickerDialog(Context context) {
        super(context);
        this.context = context;
        this.activity = findActivity(context);
        init(new DialogProperties());
    }

    @Deprecated
    public FilePickerDialog(Activity activity, Context context) {
        super(context);
        this.context = context;
        this.activity = activity == null ? findActivity(context) : activity;
        init(new DialogProperties());
    }

    public FilePickerDialog(Context context, DialogProperties properties, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.activity = findActivity(context);
        init(properties);
    }

    @Deprecated
    public FilePickerDialog(Activity activity, Context context, DialogProperties properties, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.activity = activity == null ? findActivity(context) : activity;
        init(properties);
    }

    public FilePickerDialog(Context context, DialogProperties properties) {
        super(context);
        this.context = context;
        this.activity = findActivity(context);
        init(properties);
    }

    @Deprecated
    public FilePickerDialog(Activity activity, Context context, DialogProperties properties) {
        super(context);
        this.context = context;
        this.activity = activity == null ? findActivity(context) : activity;
        init(properties);
    }

    private void init(DialogProperties dialogProperties) {
        properties = dialogProperties == null ? new DialogProperties() : dialogProperties;
        sanitizeProperties();
        filter = new ExtensionFilter(properties);
        internalList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_main);

        listView = findViewById(R.id.fileList);
        select = findViewById(R.id.select);
        dname = findViewById(R.id.dname);
        title = findViewById(R.id.title);
        dirPath = findViewById(R.id.dir_path);
        Button cancel = findViewById(R.id.cancel);

        if (negativeBtnNameStr != null) {
            cancel.setText(negativeBtnNameStr);
        }

        select.setOnClickListener(view -> {
            String[] paths = MarkedItemList.getSelectedPaths();
            if (callbacks != null) {
                callbacks.onSelectedFilePaths(paths);
            }
            dismiss();
        });
        cancel.setOnClickListener(view -> cancel());

        fileListAdapter = new FileListAdapter(internalList, context, properties);
        fileListAdapter.setNotifyItemCheckedListener(this::updateSelectButtonState);
        listView.setAdapter(fileListAdapter);
        listView.setOnItemClickListener(this);

        setTitle();
        updateSelectButtonState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasRequiredStorageAccess()) {
            loadInitialDirectory();
        } else {
            handleMissingPermission();
        }
        applyDialogSize();
    }

    @Override
    public void show() {
        if (!hasRequiredStorageAccess()) {
            handleMissingPermission();
            return;
        }
        super.show();
    }

    private void loadInitialDirectory() {
        sanitizeProperties();
        File startDirectory;
        if (properties.offset != null && properties.offset.isDirectory() && validateOffsetPath(properties.offset)) {
            startDirectory = properties.offset;
        } else if (properties.root != null && properties.root.exists() && properties.root.isDirectory()) {
            startDirectory = properties.root;
        } else {
            startDirectory = properties.error_dir;
        }
        loadDirectory(startDirectory, true);
    }

    private void loadDirectory(File directory, boolean addParentIfNeeded) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            directory = properties.error_dir;
        }
        if (directory == null || !directory.exists() || !directory.isDirectory() || !directory.canRead()) {
            showToast(R.string.error_dir_access);
            return;
        }

        currentDirectory = directory;
        internalList.clear();

        if (addParentIfNeeded && shouldShowParent(directory)) {
            File parentFile = directory.getParentFile();
            if (parentFile != null && parentFile.canRead() && Utility.isSameOrChild(properties.root, parentFile)) {
                internalList.add(createParentItem(parentFile, directory.lastModified()));
            }
        }

        dname.setText(directory.getName().isEmpty() ? directory.getAbsolutePath() : directory.getName());
        dirPath.setText(directory.getAbsolutePath());
        setTitle();
        Utility.prepareFileListEntries(internalList, directory, filter, properties.show_hidden_files);
        fileListAdapter.notifyDataSetChanged();
    }

    private boolean shouldShowParent(File directory) {
        return properties.root != null && !sameFile(directory, properties.root);
    }

    private FileListItem createParentItem(File parentFile, long fallbackTime) {
        FileListItem parent = new FileListItem();
        parent.setFilename(context.getString(R.string.label_parent_dir));
        parent.setDirectory(true);
        parent.setLocation(parentFile.getAbsolutePath());
        parent.setTime(parentFile.lastModified() > 0 ? parentFile.lastModified() : fallbackTime);
        return parent;
    }

    private boolean validateOffsetPath(File offset) {
        return properties.root != null && Utility.isSameOrChild(properties.root, offset) && !sameFile(properties.root, offset);
    }

    private boolean sameFile(File first, File second) {
        return first != null && second != null && first.getAbsolutePath().equals(second.getAbsolutePath());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (position < 0 || position >= internalList.size()) {
            return;
        }

        FileListItem item = internalList.get(position);
        if (item.isDirectory()) {
            File selectedDirectory = new File(item.getLocation());
            if (selectedDirectory.canRead()) {
                loadDirectory(selectedDirectory, true);
            } else {
                showToast(R.string.error_dir_access);
            }
            return;
        }

        MaterialCheckbox checkbox = view.findViewById(R.id.file_mark);
        if (checkbox != null && checkbox.getVisibility() == View.VISIBLE && checkbox.isEnabled()) {
            checkbox.performClick();
        }
    }

    public int countFilesInFolder(File folder) {
        int count = 0;
        if (folder != null && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null && file.isFile()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public DialogProperties getProperties() {
        return properties;
    }

    public void setProperties(DialogProperties properties) {
        this.properties = properties == null ? new DialogProperties() : properties;
        sanitizeProperties();
        filter = new ExtensionFilter(this.properties);
        if (isShowing() && fileListAdapter != null && hasRequiredStorageAccess()) {
            loadInitialDirectory();
        }
    }

    public void setDialogSelectionListener(DialogSelectionListener callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void setTitle(CharSequence titleStr) {
        this.titleStr = titleStr == null ? null : titleStr.toString();
        setTitle();
    }

    private void setTitle() {
        if (title == null || dname == null) {
            return;
        }
        if (titleStr != null && !titleStr.trim().isEmpty()) {
            title.setVisibility(View.VISIBLE);
            title.setText(titleStr);
            dname.setVisibility(View.INVISIBLE);
        } else {
            title.setVisibility(View.INVISIBLE);
            dname.setVisibility(View.VISIBLE);
        }
    }

    public void setPositiveBtnName(CharSequence positiveBtnNameStr) {
        this.positiveBtnNameStr = positiveBtnNameStr == null ? null : positiveBtnNameStr.toString();
        updateSelectButtonState();
    }

    public void setNegativeBtnName(CharSequence negativeBtnNameStr) {
        this.negativeBtnNameStr = negativeBtnNameStr == null ? null : negativeBtnNameStr.toString();
    }

    public void markFiles(List<String> paths) {
        if (paths == null || paths.isEmpty()) {
            updateSelectButtonState();
            return;
        }

        if (properties.selection_mode == DialogConfigs.SINGLE_MODE) {
            markPath(paths.get(0));
        } else {
            for (String path : paths) {
                markPath(path);
            }
        }
        updateSelectButtonState();
        if (fileListAdapter != null) {
            fileListAdapter.notifyDataSetChanged();
        }
    }

    private void markPath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return;
        }
        File file = new File(path);
        if (!isValidSelection(file)) {
            return;
        }

        FileListItem item = new FileListItem();
        item.setFilename(file.getName());
        item.setDirectory(file.isDirectory());
        item.setMarked(true);
        item.setTime(file.lastModified());
        item.setLocation(file.getAbsolutePath());

        if (properties.selection_mode == DialogConfigs.SINGLE_MODE) {
            MarkedItemList.addSingleFile(item);
        } else {
            MarkedItemList.addSelectedItem(item);
        }
    }

    private boolean isValidSelection(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        switch (properties.selection_type) {
            case DialogConfigs.DIR_SELECT:
                return file.isDirectory();
            case DialogConfigs.FILE_SELECT:
                return file.isFile();
            case DialogConfigs.FILE_AND_DIR_SELECT:
                return file.isFile() || file.isDirectory();
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (currentDirectory == null || properties.root == null || sameFile(currentDirectory, properties.root)) {
            super.onBackPressed();
            return;
        }

        File parent = currentDirectory.getParentFile();
        if (parent == null || !parent.canRead() || !Utility.isSameOrChild(properties.root, parent)) {
            super.onBackPressed();
            return;
        }

        loadDirectory(parent, true);
    }

    @Override
    public void dismiss() {
        MarkedItemList.clearSelectionList();
        if (internalList != null) {
            internalList.clear();
        }
        super.dismiss();
    }

    private void updateSelectButtonState() {
        if (select == null) {
            return;
        }

        String baseLabel = positiveBtnNameStr == null
                ? context.getString(R.string.choose_button_label)
                : positiveBtnNameStr;
        int size = MarkedItemList.getFileCount();
        boolean hasSelection = size > 0;
        int accentColor = getColorCompat(R.color.colorAccent);

        select.setEnabled(hasSelection);
        select.setTextColor(hasSelection
                ? accentColor
                : Color.argb(128, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor)));
        select.setText(hasSelection ? baseLabel + " (" + size + ")" : baseLabel);

        if (properties.selection_mode == DialogConfigs.SINGLE_MODE && fileListAdapter != null) {
            fileListAdapter.notifyDataSetChanged();
        }
    }

    private boolean hasRequiredStorageAccess() {
        return Utility.hasStorageAccess(context, properties);
    }

    private void handleMissingPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && properties.allow_manage_external_storage) {
            if (properties.show_permission_error_toast) {
                Toast.makeText(context, R.string.error_dir_access, Toast.LENGTH_LONG).show();
            }
            Utility.openManageAllFilesAccessSettings(context);
            return;
        }

        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Utility.requestRuntimeReadPermissions(activity, EXTERNAL_READ_PERMISSION_GRANT);
            } else if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_READ_PERMISSION_GRANT);
            }
            return;
        }

        if (properties.show_permission_error_toast) {
            Toast.makeText(context, R.string.error_dir_access, Toast.LENGTH_LONG).show();
        }
    }

    private void sanitizeProperties() {
        if (properties.root == null) {
            properties.root = new File(DialogConfigs.DEFAULT_DIR);
        }
        if (properties.error_dir == null) {
            properties.error_dir = properties.root;
        }
        if (properties.offset == null) {
            properties.offset = properties.root;
        }
        if (properties.selection_mode != DialogConfigs.SINGLE_MODE && properties.selection_mode != DialogConfigs.MULTI_MODE) {
            properties.selection_mode = DialogConfigs.SINGLE_MODE;
        }
        if (properties.selection_type != DialogConfigs.FILE_SELECT
                && properties.selection_type != DialogConfigs.DIR_SELECT
                && properties.selection_type != DialogConfigs.FILE_AND_DIR_SELECT) {
            properties.selection_type = DialogConfigs.FILE_SELECT;
        }
    }

    private int getColorCompat(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorRes, context.getTheme());
        }
        return context.getResources().getColor(colorRes);
    }

    private void showToast(int stringRes) {
        Toast.makeText(context, stringRes, Toast.LENGTH_SHORT).show();
    }

    private static Activity findActivity(Context context) {
        Context current = context;
        while (current instanceof ContextWrapper) {
            if (current instanceof Activity) {
                return (Activity) current;
            }
            current = ((ContextWrapper) current).getBaseContext();
        }
        return null;
    }

    /**
     * Sets the dialog width and height directly.
     *
     * Use ViewGroup.LayoutParams.MATCH_PARENT,
     * ViewGroup.LayoutParams.WRAP_CONTENT,
     * or a pixel value.
     */
    public void setDialogSize(int width, int height) {
        this.dialogWidth = width;
        this.dialogHeight = height;
        this.dialogWidthPercent = -1f;
        this.dialogHeightPercent = -1f;

        if (isShowing()) {
            applyDialogSize();
        }
    }

    /**
     * Sets the dialog size using screen percentage.
     *
     * Example:
     * setDialogSizeByPercent(0.85f, 0.75f);
     *
     * Width and height values must be between 0.1f and 1.0f.
     */
    public void setDialogSizeByPercent(float widthPercent, float heightPercent) {
        this.dialogWidthPercent = sanitizePercent(widthPercent);
        this.dialogHeightPercent = sanitizePercent(heightPercent);

        if (isShowing()) {
            applyDialogSize();
        }
    }

    private float sanitizePercent(float percent) {
        if (percent <= 0f) {
            return -1f;
        }
        if (percent < 0.1f) {
            return 0.1f;
        }
        if (percent > 1f) {
            return 1f;
        }
        return percent;
    }

    private void applyDialogSize() {
        Window window = getWindow();
        if (window == null) {
            return;
        }

        int width = dialogWidth;
        int height = dialogHeight;

        if (dialogWidthPercent > 0f || dialogHeightPercent > 0f) {
            WindowManager windowManager = window.getWindowManager();
            if (windowManager != null) {
                android.graphics.Point size = new android.graphics.Point();
                windowManager.getDefaultDisplay().getSize(size);

                if (dialogWidthPercent > 0f) {
                    width = (int) (size.x * dialogWidthPercent);
                }

                if (dialogHeightPercent > 0f) {
                    height = (int) (size.y * dialogHeightPercent);
                }
            }
        }

        window.setLayout(width, height);
    }
}
