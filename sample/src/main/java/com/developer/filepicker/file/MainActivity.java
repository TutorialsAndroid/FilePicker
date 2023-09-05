package com.developer.filepicker.file;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FilePickerDialog dialog;
    private ArrayList<ListItem> listItem;
    private FileListAdapter mFileListAdapter;

    private static final int REQUEST_STORAGE_PERMISSIONS = 123;
    private String readPermission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    private String writePermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final int REQUEST_MEDIA_PERMISSIONS = 456;
    private String audioPermission = android.Manifest.permission.READ_MEDIA_AUDIO;
    private String imagesPermission = android.Manifest.permission.READ_MEDIA_IMAGES;
    private String videoPermission = android.Manifest.permission.READ_MEDIA_VIDEO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItem = new ArrayList<>();
        RecyclerView fileList = findViewById(R.id.listView);
        mFileListAdapter = new FileListAdapter(listItem, MainActivity.this);
        fileList.setAdapter(mFileListAdapter);
        fileList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        fileList.setNestedScrollingEnabled(false);

        //Create a DialogProperties object.
        final DialogProperties properties = new DialogProperties();

        //Instantiate FilePickerDialog with Context and DialogProperties.
        dialog = new FilePickerDialog(MainActivity.this, MainActivity.this, properties);
        dialog.setTitle("Select a File");
        dialog.setPositiveBtnName("Select");
        dialog.setNegativeBtnName("Cancel");
      //  properties.selection_mode = DialogConfigs.MULTI_MODE;
      //  properties.selection_type = DialogConfigs.DIR_SELECT;

        RadioGroup modeRadio = findViewById(R.id.modeRadio);
        modeRadio.check(R.id.singleRadio);
        modeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.singleRadio:  //Setting selection mode to single selection.
                        properties.selection_mode = DialogConfigs.SINGLE_MODE;
                        break;
                    case R.id.multiRadio:   //Setting selection mode to multiple selection.
                        properties.selection_mode = DialogConfigs.MULTI_MODE;
                        break;
                }
            }
        });
        RadioGroup typeRadio = findViewById(R.id.typeRadio);
        typeRadio.check(R.id.selFile);
        typeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.selFile:  //Setting selection type to files.
                        properties.selection_type = DialogConfigs.FILE_SELECT;
                        break;
                    case R.id.selDir:   //Setting selection type to directories.
                        properties.selection_type = DialogConfigs.DIR_SELECT;
                        break;
                    case R.id.selfilenddir: //Setting selection type to files and directories.
                        properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
                        break;
                }
            }
        });
        final EditText extension = findViewById(R.id.extensions);
        final EditText root = findViewById(R.id.root);
        final EditText offset = findViewById(R.id.offset);
        final CheckBox show_hidden_files = findViewById(R.id.show_hidden_files);
        Button apply = findViewById(R.id.apply);
        Button showDialog = findViewById(R.id.show_dialog);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fextension = extension.getText().toString();
                if (fextension.length() > 0) {
                    //Add extensions to be sorted from the EditText input to the array of String.
                    int commas = countCommas(fextension);

                    //Array representing extensions.
                    String[] exts = new String[commas + 1];
                    StringBuffer buff = new StringBuffer();
                    int i = 0;
                    for (int j = 0; j < fextension.length(); j++) {
                        if (fextension.charAt(j) == ',') {
                            exts[i] = buff.toString();
                            buff = new StringBuffer();
                            i++;
                        } else {
                            buff.append(fextension.charAt(j));
                        }
                    }
                    exts[i] = buff.toString();

                    //Set String Array of extensions.
                    properties.extensions = exts;
                } else {   //If EditText is empty, Initialise with null reference.
                    properties.extensions = null;
                }
                String foffset = root.getText().toString();
                if (foffset.length() > 0 || !foffset.equals("")) {
                    //Setting Parent Directory.
                    properties.root = new File(foffset);
                } else {
                    //Setting Parent Directory to Default SDCARD.
                    properties.root = new File(DialogConfigs.DEFAULT_DIR);
                }

                String fset = offset.getText().toString();
                if (fset.length() > 0 || !fset.equals("")) {
                    //Setting Offset Directory.
                    properties.offset = new File(fset);
                } else {
                    //Setting Parent Directory to Default SDCARD.
                    properties.offset = new File(DialogConfigs.DEFAULT_DIR);
                }

                properties.show_hidden_files = show_hidden_files.isChecked();

                //Setting Alternative Directory, in case root is not accessible.This will be
                //used.

                properties.error_dir = new File("/mnt");
                //Set new properties of dialog.
                dialog.setProperties(properties);

//                Pre marking of files in Dialog
//                ArrayList<String> paths=new ArrayList<>();
//                paths.add("/mnt/sdcard/.VOD");
//                paths.add("/mnt/sdcard/.VOD/100.jpg");
//                paths.add("/mnt/sdcard/.VOD/1000.jpg");
//                paths.add("/mnt/sdcard/.VOD/1010.jpg");
//                paths.add("/mnt/sdcard/.VOD/1020.jpg");
//                paths.add("/mnt/sdcard/.VOD/1070.jpg");
//                dialog.markFiles(paths);
            }
        });

        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // Check for permissions and request them if needed
                    if (ContextCompat.checkSelfPermission(MainActivity.this, audioPermission) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(MainActivity.this, imagesPermission) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(MainActivity.this, videoPermission) == PackageManager.PERMISSION_GRANTED) {
                        // You have the permissions, you can proceed with your media file operations.
                        //Showing dialog when Show Dialog button is clicked.
                        dialog.show();
                    } else {
                        // You don't have the permissions. Request them.
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{audioPermission, imagesPermission, videoPermission}, REQUEST_MEDIA_PERMISSIONS);
                    }
                } else {
                    // Check for permissions and request them if needed
                    if (ContextCompat.checkSelfPermission(MainActivity.this, readPermission) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(MainActivity.this, writePermission) == PackageManager.PERMISSION_GRANTED) {
                        // You have the permissions, you can proceed with your file operations.
                        //Showing dialog when Show Dialog button is clicked.
                        dialog.show();
                    } else {
                        // You don't have the permissions. Request them.
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{readPermission, writePermission}, REQUEST_STORAGE_PERMISSIONS);
                    }
                }
            }
        });

        //Method handle selected files.
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of paths selected by the App User.
                int size = listItem.size();
                listItem.clear();
                mFileListAdapter.notifyItemRangeRemoved(0, size);
                for (String path : files) {
                    File file = new File(path);
                    ListItem item = new ListItem();
                    item.setName(file.getName());
                    item.setPath(file.getAbsolutePath());
                    listItem.add(item);
                }
                mFileListAdapter.notifyItemRangeInserted(0, listItem.size());
            }
        });
    }

    private int countCommas(String fextension) {
        int count = 0;
        for (char ch : fextension.toCharArray()) {
            if (ch == ',') count++;
        }
        return count;
    }

    //Add this method to show Dialog when the required permission has been granted to the app.
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                if (dialog != null) {
//                    //Show dialog if the read permission has been granted.
//                    dialog.show();
//                }
//            } else {
//                //Permission has not been granted. Notify the user.
//                Toast.makeText(MainActivity.this, "Permission is Required for getting list of files", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions were granted. You can proceed with your file operations.
                //Showing dialog when Show Dialog button is clicked.
                dialog.show();
            } else {
                // Permissions were denied. Show a rationale dialog or inform the user about the importance of these permissions.
                showRationaleDialog();
            }
        }

        if (requestCode == REQUEST_MEDIA_PERMISSIONS) {
            if (grantResults.length > 0 && areAllPermissionsGranted(grantResults)) {
                // Permissions were granted. You can proceed with your media file operations.
                //Showing dialog when Show Dialog button is clicked.
                dialog.show();
            } else {
                // Permissions were denied. Show a rationale dialog or inform the user about the importance of these permissions.
                showRationaleDialog();
            }
        }
    }

    private boolean areAllPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void showRationaleDialog() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, readPermission) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, writePermission)) {
            // Show a rationale dialog explaining why the permissions are necessary.
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This app needs storage permissions to read and write files.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Request permissions when the user clicks OK.
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{readPermission, writePermission}, REQUEST_STORAGE_PERMISSIONS);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // Handle the case where the user cancels the permission request.
                        }
                    })
                    .show();
        } else {
            // Request permissions directly if no rationale is needed.
            ActivityCompat.requestPermissions(this, new String[]{readPermission, writePermission}, REQUEST_STORAGE_PERMISSIONS);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            //startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}