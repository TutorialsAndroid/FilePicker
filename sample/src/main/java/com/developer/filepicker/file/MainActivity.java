package com.developer.filepicker.file;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import java.io.File;
import java.util.ArrayList;

//This is just sample activity for demo purposes always use the best practice to ask for
//storage permissions on Android version 6.0 and above.
public class MainActivity extends AppCompatActivity {

    private FilePickerDialog filePickerDialog;
    private ArrayList<ListItem> listItem;
    private FileListAdapter mFileListAdapter;

    private static final int REQUEST_STORAGE_PERMISSIONS = 123;
    private final String readPermission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String writePermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_MEDIA_PERMISSIONS = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Initialize Views
        RecyclerView fileList = findViewById(R.id.listView);
        RadioGroup modeRadio = findViewById(R.id.modeRadio);
        RadioGroup typeRadio = findViewById(R.id.typeRadio);
        EditText extension = findViewById(R.id.extensions);
        EditText root = findViewById(R.id.root);
        EditText offset = findViewById(R.id.offset);
        CheckBox show_hidden_files = findViewById(R.id.show_hidden_files);
        Button apply = findViewById(R.id.apply);
        Button showDialog = findViewById(R.id.show_dialog);

        listItem = new ArrayList<>();
        mFileListAdapter = new FileListAdapter(listItem, MainActivity.this);
        fileList.setAdapter(mFileListAdapter);
        fileList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        fileList.setNestedScrollingEnabled(false);

        //Create a DialogProperties object.
        final DialogProperties properties = new DialogProperties();
        //If you want to view files with specific extensions you can just call properties.extensions
//        properties.extensions = new String[]{"zip","jpg","mp3","csv"};

        //Instantiate FilePickerDialog with Context and DialogProperties.
        filePickerDialog = new FilePickerDialog(MainActivity.this, properties);
        filePickerDialog.setTitle("Select a File");
        filePickerDialog.setPositiveBtnName("Select");
        filePickerDialog.setNegativeBtnName("Cancel");

        modeRadio.check(R.id.singleRadio);
        modeRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.singleRadio) {
                //Setting selection mode to single selection.
                properties.selection_mode = DialogConfigs.SINGLE_MODE;
            }

            if (checkedId == R.id.multiRadio) {
                //Setting selection mode to multiple selection.
                properties.selection_mode = DialogConfigs.MULTI_MODE;
            }
        });

        typeRadio.check(R.id.selFile);
        typeRadio.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.selFile) {
                //Setting selection type to files.
                properties.selection_type = DialogConfigs.FILE_SELECT;
            }

            if (checkedId == R.id.selDir) {
                //Setting selection type to directories.
                properties.selection_type = DialogConfigs.DIR_SELECT;
            }

            if (checkedId == R.id.selfilenddir) {
                //Setting selection type to files and directories.
                properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
            }
        });

        apply.setOnClickListener(view -> {
            String f_extension = extension.getText().toString();
            if (f_extension.length() > 0) {
                //Add extensions to be sorted from the EditText input to the array of String.
                int commas = countCommas(f_extension);

                //Array representing extensions.
                String[] extensions = new String[commas + 1];
                StringBuilder stringBuffer = new StringBuilder();
                int i = 0;
                for (int j = 0; j < f_extension.length(); j++) {
                    if (f_extension.charAt(j) == ',') {
                        extensions[i] = stringBuffer.toString();
                        stringBuffer = new StringBuilder();
                        i++;
                    } else {
                        stringBuffer.append(f_extension.charAt(j));
                    }
                }
                extensions[i] = stringBuffer.toString();

                //Set String Array of extensions.
                properties.extensions = extensions;
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
            filePickerDialog.setProperties(properties);
        });

        showDialog.setOnClickListener(view -> {
            //If Android version is 13 and greater then you can only access Audio, Images and Videos file
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                //As the device is Android 13 and above so I want the permission of accessing Audio, Images, Videos
                //You can ask permission according to your requirements what you want to access.
                String audioPermission = android.Manifest.permission.READ_MEDIA_AUDIO;
                String imagesPermission = android.Manifest.permission.READ_MEDIA_IMAGES;
                String videoPermission = android.Manifest.permission.READ_MEDIA_VIDEO;
                // Check for permissions and request them if needed
                if (ContextCompat.checkSelfPermission(MainActivity.this, audioPermission) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this, imagesPermission) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this, videoPermission) == PackageManager.PERMISSION_GRANTED) {
                    // You have the permissions, you can proceed with your media file operations.
                    //Showing dialog when Show Dialog button is clicked.
                    filePickerDialog.show();
                } else {
                    // You don't have the permissions. Request them.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{audioPermission, imagesPermission, videoPermission}, REQUEST_MEDIA_PERMISSIONS);
                }
            } else {
                //Android version is below 13 so we are asking normal read and write storage permissions
                // Check for permissions and request them if needed
                if (ContextCompat.checkSelfPermission(MainActivity.this, readPermission) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this, writePermission) == PackageManager.PERMISSION_GRANTED) {
                    // You have the permissions, you can proceed with your file operations.
                    // Show the file picker dialog when needed
                    filePickerDialog.show();
                } else {
                    // You don't have the permissions. Request them.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{readPermission, writePermission}, REQUEST_STORAGE_PERMISSIONS);
                }
            }
        });

        //Method handle selected files.
        filePickerDialog.setDialogSelectionListener(files -> {
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
        });
    }

    private int countCommas(String f_extension) {
        int count = 0;
        for (char ch : f_extension.toCharArray()) {
            if (ch == ',') count++;
        }
        return count;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions were granted. You can proceed with your file operations.
                //Showing dialog when Show Dialog button is clicked.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    accessAllFilesPermissionDialog();
                } else {
                    filePickerDialog.show();
                }
            } else {
                // Permissions were denied. Show a rationale dialog or inform the user about the importance of these permissions.
                showRationaleDialog();
            }
        }

        //This conditions only works on Android 13 and above versions
        if (requestCode == REQUEST_MEDIA_PERMISSIONS) {
            if (grantResults.length > 0 && areAllPermissionsGranted(grantResults)) {
                // Permissions were granted. You can proceed with your media file operations.
                //Showing dialog when Show Dialog button is clicked.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    accessAllFilesPermissionDialog();
                }
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
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Request permissions when the user clicks OK.
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{readPermission, writePermission}, REQUEST_STORAGE_PERMISSIONS);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                        // Handle the case where the user cancels the permission request.
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
            Toast.makeText(MainActivity.this, "TODO Add your own action!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final static int APP_STORAGE_ACCESS_REQUEST_CODE = 501; // Any value
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void accessAllFilesPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("This app needs all files access permissions to view files from your storage. Clicking on OK will redirect you to new window were you have to enable the option.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Request permissions when the user clicks OK.
                    Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                    startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    // Handle the case where the user cancels the permission request.
                    filePickerDialog.show();
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_STORAGE_ACCESS_REQUEST_CODE) {
            // Permission granted. Now resume your workflow.
            filePickerDialog.show();
        }
    }
}