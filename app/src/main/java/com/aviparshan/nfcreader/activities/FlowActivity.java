package com.aviparshan.nfcreader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aviparshan.nfcreader.utils.BaseApp;


/**
 * Created by avipars on 3/23/2019 on com.aviparshan.nfcreader.activities
 */
public class FlowActivity extends AppCompatActivity {

    public boolean logged_in;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_NoDisplay);
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        logged_in = BaseApp.getLogged(this);
        // go straight to main if a token is stored
        if (logged_in) {
            activityIntent = new Intent(this, MainReaderActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }

}
