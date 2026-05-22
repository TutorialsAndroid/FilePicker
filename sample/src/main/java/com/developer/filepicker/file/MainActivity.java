package com.developer.filepicker.file;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FilePickerDialog filePickerDialog;
    private ArrayList<ListItem> listItem;
    private FileListAdapter mFileListAdapter;

    private ActivityResultLauncher<String[]> storagePermissionLauncher;
    private ActivityResultLauncher<Intent> allFilesAccessLauncher;
    private boolean pendingOpenPicker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPermissionLaunchers();

        RecyclerView fileList = findViewById(R.id.listView);
        RadioGroup modeRadio = findViewById(R.id.modeRadio);
        RadioGroup typeRadio = findViewById(R.id.typeRadio);
        EditText extension = findViewById(R.id.extensions);
        EditText root = findViewById(R.id.root);
        EditText offset = findViewById(R.id.offset);
        CheckBox showHiddenFiles = findViewById(R.id.show_hidden_files);
        Button apply = findViewById(R.id.apply);
        Button showDialog = findViewById(R.id.show_dialog);

        listItem = new ArrayList<>();
        mFileListAdapter = new FileListAdapter(listItem, this);
        fileList.setLayoutManager(new LinearLayoutManager(this));
        fileList.setNestedScrollingEnabled(false);
        fileList.setAdapter(mFileListAdapter);

        final DialogProperties properties = new DialogProperties();

        filePickerDialog = new FilePickerDialog(this, properties);
        filePickerDialog.setTitle("Select a File");
        filePickerDialog.setPositiveBtnName("Select");
        filePickerDialog.setNegativeBtnName("Cancel");

        modeRadio.check(R.id.singleRadio);
        modeRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.singleRadio) {
                properties.selection_mode = DialogConfigs.SINGLE_MODE;
            } else if (checkedId == R.id.multiRadio) {
                properties.selection_mode = DialogConfigs.MULTI_MODE;
            }
        });

        typeRadio.check(R.id.selFile);
        typeRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.selFile) {
                properties.selection_type = DialogConfigs.FILE_SELECT;
            } else if (checkedId == R.id.selDir) {
                properties.selection_type = DialogConfigs.DIR_SELECT;
            } else if (checkedId == R.id.selfilenddir) {
                properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
            }
        });

        apply.setOnClickListener(view -> {
            String extensionText = extension.getText().toString().trim();
            if (!extensionText.isEmpty()) {
                String[] extensions = extensionText.split("\\s*,\\s*");
                properties.extensions = extensions;
            } else {
                properties.extensions = null;
            }

            String rootPath = root.getText().toString().trim();
            properties.root = rootPath.isEmpty()
                    ? new File(DialogConfigs.DEFAULT_DIR)
                    : new File(rootPath);

            String offsetPath = offset.getText().toString().trim();
            properties.offset = offsetPath.isEmpty()
                    ? new File(DialogConfigs.DEFAULT_DIR)
                    : new File(offsetPath);

            properties.show_hidden_files = showHiddenFiles.isChecked();
            properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
            filePickerDialog.setProperties(properties);

            Toast.makeText(this, "Properties applied", Toast.LENGTH_SHORT).show();
        });

        showDialog.setOnClickListener(view -> openFilePickerSafely());

        filePickerDialog.setDialogSelectionListener(files -> {
            int oldSize = listItem.size();
            listItem.clear();
            if (oldSize > 0) {
                mFileListAdapter.notifyItemRangeRemoved(0, oldSize);
            }

            for (String path : files) {
                File file = new File(path);
                ListItem item = new ListItem();
                item.setName(file.getName());
                item.setPath(file.getAbsolutePath());
                listItem.add(item);
            }

            if (!listItem.isEmpty()) {
                mFileListAdapter.notifyItemRangeInserted(0, listItem.size());
            }
        });
    }

    private void setupPermissionLaunchers() {
        storagePermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                this::handleStoragePermissionResult
        );

        allFilesAccessLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                        if (pendingOpenPicker) {
                            pendingOpenPicker = false;
                            filePickerDialog.show();
                        }
                    } else {
                        pendingOpenPicker = false;
                        Toast.makeText(this, "All files access was not granted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void openFilePickerSafely() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                filePickerDialog.show();
            } else {
                showAllFilesAccessDialog();
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                filePickerDialog.show();
            } else {
                storagePermissionLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
            }
            return;
        }

        filePickerDialog.show();
    }

    private void handleStoragePermissionResult(Map<String, Boolean> result) {
        Boolean readGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (Boolean.TRUE.equals(readGranted)) {
            filePickerDialog.show();
        } else {
            showPermissionDeniedDialog();
        }
    }

    private void showAllFilesAccessDialog() {
        pendingOpenPicker = true;
        new AlertDialog.Builder(this)
                .setTitle("All Files Access Needed")
                .setMessage("This demo uses raw file paths, so Android 11 and above require All files access to browse shared storage properly.")
                .setPositiveButton("Open Settings", (dialog, which) -> openAllFilesAccessSettings())
                .setNegativeButton("Cancel", (dialog, which) -> {
                    pendingOpenPicker = false;
                    dialog.dismiss();
                })
                .show();
    }

    private void openAllFilesAccessSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(
                        ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + getPackageName())
                );
                allFilesAccessLauncher.launch(intent);
            } catch (Exception ignored) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                allFilesAccessLauncher.launch(intent);
            }
        }
    }

    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("Storage permission is required to browse files in this demo.")
                .setPositiveButton("Try Again", (dialog, which) -> openFilePickerSafely())
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "TODO Add your own action!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
