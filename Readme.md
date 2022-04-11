![](https://github.com/TutorialsAndroid/FilePicker/blob/master/sample/src/main/res/mipmap-xxhdpi/ic_launcher.png)

# FilePicker ![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat) [![License](https://img.shields.io/badge/License-Apache%202.0-orange.svg)](https://opensource.org/licenses/Apache-2.0) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FilePicker-yellow.svg?style=flat)](https://android-arsenal.com/details/1/7663) 
Android Library to select files/directories from Device Storage

## And Don't Forget To Follow Me On Instagram

<p align="center">Follow me on instagram to stay up-to-date https://instagram.com/akshay.sunil.masram.1998

## Contributors

[M&R Games](https://github.com/mrgames13)

[Hatzen](https://github.com/Hatzen)

**Library available at JitPack.io**

[![](https://jitpack.io/v/TutorialsAndroid/FilePicker.svg)](https://jitpack.io/#TutorialsAndroid/FilePicker)

`Latest version of this library is migrated to androidx`

### Screenshot

![](https://github.com/TutorialsAndroid/FilePicker/blob/master/screenshots/device-2019-05-10-182300.png)

### Features

* Easy to Implement.
* No permissions required.
* Files, Directory Selection.
* Single or Multiple File selection.

### Installation

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.TutorialsAndroid:FilePicker:v8.0.19'
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
        properties.extensions = null;
        properties.show_hidden_files = false;
    ```

3. Next create an instance of `FilePickerDialog`, and pass `Context` and `DialogProperties` references as parameters. Optional: You can change the title of dialog. Default is current directory name. Set the positive button string. Default is Select. Set the negative button string. Defalut is Cancel.

    ```java
        FilePickerDialog dialog = new FilePickerDialog(MainActivity.this,properties);
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

    That's It. You are good to proceed further.


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
