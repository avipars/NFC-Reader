package com.aviparshan.nfcreader.activities;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aviparshan.nfcreader.R;
import com.aviparshan.nfcreader.utils.BaseApp;
import com.aviparshan.nfcreader.utils.NFCCardReader;

public class MainReaderActivity extends AppCompatActivity {

    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String TAG = "com.avipars.NFCReader";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    Tag myTag;

    TextView prompt;
    public int zone;
    public boolean logged_in;

    LinearLayout linear;
    CheckBox check;

    //TODO: finish reading implementation

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zone = BaseApp.getZone(this);
        logged_in = BaseApp.getLogged(this);

        Toast.makeText(this, "Zone " + zone, Toast.LENGTH_SHORT).show();

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        prompt = findViewById(R.id.prompt);

        linear = findViewById(R.id.linear);
        linear.setVisibility(View.GONE);
        check = findViewById(R.id.check);

        enableReaderMode();

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Toast.makeText(MainReaderActivity.this, "User Present: " + checked, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        nfcAdapter.disableReaderMode(this);
    }

    private void goToZone() {
        Intent in = new Intent(MainReaderActivity.this, SetupZoneActivity.class);
        startActivity(in);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_log) {
            //BaseApp.clearSharedPrefs(this);
            BaseApp.setLogged(this, false);
            goToLogin();
            return true;
        } else if (id == R.id.action_zone) {
            goToZone();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void goToLogin() {
        Intent in = new Intent(MainReaderActivity.this, LoginActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        nfcAdapter.disableReaderMode(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        nfcChecker();
        enableReaderMode();
    }

    private void nfcChecker() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            // NFC is not available for device
            Toast.makeText(this, R.string.nfc_not_supported, Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            // NFC is available for device but not enabled
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.enable_nfc);
            dialog.setMessage(R.string.nfc_requiretd);
            dialog.setPositiveButton(R.string.nfc_settings, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                    startActivity(intent);
                }
            });
            dialog.setCancelable(false);
            dialog.create();
            dialog.show();
        }
    }

    /**
     * Works well but not supported on older devices
     */
    private void enableReaderMode() {
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, new NFCCardReader(this), NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK | NfcAdapter.FLAG_READER_NFC_B, null);
        }
    }

    public void displayTagId(final String tagId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                prompt.setText(tagId);
                linear.setVisibility(View.VISIBLE);
                check.setChecked(false); //resets checkbox
            }
        });
    }

}
