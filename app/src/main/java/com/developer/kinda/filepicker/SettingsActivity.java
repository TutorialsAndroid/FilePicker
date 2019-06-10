package com.developer.kinda.filepicker;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.developer.kinda.filepicker.view.FilePickerPreference;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        FilePickerPreference fileDialog=(FilePickerPreference)findPreference("directories");
        fileDialog.setOnPreferenceChangeListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar toolbar;
        {   LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
            toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, root, false);
            root.addView(toolbar, 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary,getTheme()));
        }
        else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        toolbar.setNavigationIcon(R.drawable.ic_up_navigation);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getResources().getString(R.string.title_activity_settings));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if(preference.getKey().equals("directories"))
        {   String value=(String)o;
            String[] arr = value.split(":");
            for(String path:arr)
                Toast.makeText(SettingsActivity.this,path,Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
