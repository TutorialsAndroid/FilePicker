![](https://github.com/TutorialsAndroid/FilePicker/blob/master/sample/src/main/res/mipmap-xxhdpi/ic_launcher.png)

# FilePicker ![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat) [![License](https://img.shields.io/badge/License-Apache%202.0-orange.svg)](https://opensource.org/licenses/Apache-2.0) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FilePicker-yellow.svg?style=flat)](https://android-arsenal.com/details/1/7663) [![](https://jitpack.io/v/TutorialsAndroid/FilePicker.svg)](https://jitpack.io/#TutorialsAndroid/FilePicker) [![](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://instagram.com/a.masram444) [![](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/a_masram444)
Android Library to select files/directories from Device Storage

## Will you buy a coffee for me

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.paypal.com/paypalme/tusharmasram)

## Contributors

[M&R Games](https://github.com/mrgames13)

[Hatzen](https://github.com/Hatzen)

**Library available at JitPack.io**

[![](https://jitpack.io/v/TutorialsAndroid/FilePicker.svg)](https://jitpack.io/#TutorialsAndroid/FilePicker)

`Latest version of this library is migrated to androidx and Added partial Support to Android 13.`

### Screenshot

![](https://github.com/TutorialsAndroid/FilePicker/blob/master/screenshots/device-2019-05-10-182300.png)

### Features

* Easy to Implement.
* Files, Directory Selection.
* Single or Multiple File selection.

### Installation with JitPack 
*( IMPORTANT NOTE: WE HAVE STOPPED PUSHING LIBRARY TO JITPACK v9.0.1 is outdated now. SEE mavenCentral() below )*

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.TutorialsAndroid:FilePicker:v9.0.1'
	}

### Installation with mavenCentral()
Step 1. Directly add the dependency in application build.gradle file:

    dependencies {
        implementation 'io.github.tutorialsandroid:filepicker:9.2.5'
    }

### Usage

**If you are targeting Android 10 or higher. Set this to your manifest**
```
<manifest ... >
  <!-- This attribute is "false" by default on apps targeting
       Android 10 or higher. -->
  <application 
       android:requestLegacyExternalStorage="true" ........ >
    ......
  </application>
</manifest>
```
**Also if you are targeting Android 10 or higher. You have to add permissions**

```xml
    <!-- If you are targeting apps above android version 6.0 to 12.0 You need to add this permission in your manifest -->    
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
    
    <!-- Now if you are targeting app above android version 13.0 then you have to add this permission in your manifest.
     It's upon you which type of files you want to access if you want access Audio, Images and Videos files then you have
     to call all this below permissions -->
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO"/>
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES"/>
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO"/>

    <!-- Now on android 11 and above if you want to access other file types like csv,doc,pdf etc...
     You have to ask user a special permission that is MANAGE_EXTERNAL_STORAGE. But use this permission
     at your own risk because if you are publishing app on google play-store then you can encounter some
      issues. Use this permission only when your app core functionality is to read/write/delete. See the
      below link for more reference on this permission-->
    <!-- https://support.google.com/googleplay/android-developer/answer/10467955 -->
    <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"/>

    <!-- If you are targeting your app from android version 4.4 to the latest version of android then
     you have to call all the above permissions as mentioned except MANAGE_EXTERNAL_STORAGE use it only
      when needed.-->
```

## FilePickerDialog
1. Start by creating an instance of `DialogProperties`.

    ```java
        DialogProperties properties = new DialogProperties();
    ```

    Now 'DialogProperties' has certain parameters.

2. Assign values to each Dialog Property using `DialogConfig` class.

    ```java
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        //If you want to view files of all extensions then pass null to properties.extensions
        properties.extensions = null;
        //If you want to view files with specific type of extensions the pass string array to properties.extensions
        properties.extensions = new String[]{"zip","jpg","mp3","csv"};
        properties.show_hidden_files = false;
    ```

3. Next create an instance of `FilePickerDialog`, and pass `Context` and `DialogProperties` references as parameters. Optional: You can change the title of dialog. Default is current directory name. Set the positive button string. Default is Select. Set the negative button string. Defalut is Cancel.

    ```java
        FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
        dialog.setTitle("Select a File");
    ```

4.  Next, Attach `DialogSelectionListener` to `FilePickerDialog` as below,
    ```java
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
            }
        });
    ```
    An array of paths is returned whenever user press the `select` button`.

5. Use ```dialog.show()``` method to show dialog.

### NOTE:
Marshmallow and above requests for the permission on runtime. You should override `onRequestPermissionsResult` in Activity/AppCompatActivity class and show the dialog only if permissions have been granted.

```java
        //Add this method to show Dialog when the required permission has been granted to the app.
        @Override
        public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
            switch (requestCode) {
                case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(dialog!=null)
                        {   //Show dialog if the read permission has been granted.
                            dialog.show();
                        }
                    }
                    else {
                        //Permission has not been granted. Notify the user.
                        Toast.makeText(MainActivity.this,"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
```

### Android11 and above Instructions to get all types file formats:

Step 1: In your AndroidManifest.xml file add this permissions:

```xml
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO"/>
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES"/>
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO"/>
    <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"/>
```

Step 2: Then in your Activity file ask for media permissions:

```java
    public class MainActivity extends AppCompatActivity {
        //...Your activity code goes here like onCreate() methods and all...
    
        private final static int APP_STORAGE_ACCESS_REQUEST_CODE = 501;
        private static final int REQUEST_STORAGE_PERMISSIONS = 123;
        private static final int REQUEST_MEDIA_PERMISSIONS = 456;
        private final String readPermission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        private final String writePermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    
        private void checkPermissions() {
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
        }
    
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQUEST_STORAGE_PERMISSIONS) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permissions were granted. You can proceed with your file operations.
                    //Showing dialog when Show Dialog button is clicked.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        //Android version is 11 and above so to access all types of files we have to give
                        //special permission so show user a dialog..
                        accessAllFilesPermissionDialog();
                    } else {
                        //Android version is 10 and below so need of special permission...
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
                        //Android version is 11 and above so to access all types of files we have to give
                        //special permission so show user a dialog..
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
                    ActivityCompat.shouldShowRequestPermissionRationale(this, writePermission) ) {
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
```
### Android13 and Above Instructions:
If your app targets Android 13 or higher and needs to access media files that other apps have created, you must request one or more of the following granular media permissions instead of the ```READ_EXTERNAL_STORAGE permission```:
As of Android 13 and above you can only browse and select Images,Videos and Audio files only. This library is still in development and I'm looking for contributors to make this library more better
```
    Type of media	  |  Permission to request
    
    Images and photos |	READ_MEDIA_IMAGES
    Videos	          | READ_MEDIA_VIDEO
    Audio files	      | READ_MEDIA_AUDIO
```

*Before you access another app's media files, verify that the user has granted the appropriate granular media permissions to your app.*

If you request both the ```READ_MEDIA_IMAGES``` permission and the ```READ_MEDIA_VIDEO``` permission at the same time, only one system permission dialog appears.

If your app was previously granted the ```READ_EXTERNAL_STORAGE``` permission, then any requested ```READ_MEDIA_*``` permissions are granted automatically when upgrading.

    That's It. You are good to proceed further. I hope this documentation will help you. You can contact me on telegram or instagram if having any issues id is given at start of readme.


### Important:
* `defaultValue`, `error_dir`, `root_dir`, `offset_dir` must have valid directory/file paths.
* `defaultValue` paths should end with ':'.
* `defaultValue` can have multiple paths, there should be a ':' between two paths.
* `extensions` must not have '.'.
* `extensions` should end with ':' , also have ':' between two extensions.
eg. /sdcard:/mnt:

### License
    Copyright (C) 2019 FilePicker

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
