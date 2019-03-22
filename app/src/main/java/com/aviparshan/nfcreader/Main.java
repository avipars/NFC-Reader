package com.aviparshan.nfcreader;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String TAG = "com.avipars.NFCReader";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    Tag myTag;

    TextView prompt;
    public int zone;

    SharedPreferences sp;

    //TODO: finish reading implementation

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(LoginActivity.PREFS,MODE_PRIVATE);

            zone = sp.getInt(SetupZone.bundle, 9);
            Toast.makeText(this, "Zone " + zone, Toast.LENGTH_SHORT).show();

            nfcChecker();
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            prompt = (TextView) findViewById(R.id.prompt);
            enableReaderMode();

        //TODO: finish login flow
        if(sp.getBoolean(LoginActivity.LOGGED,false)){
            goToLogin();
            //return to login
        }
//        else if(!sp.contains(SetupZone.bundle)){
//            goToZone();
//            //return to login
//        }

    }

    private void goToZone() {
        Intent in = new Intent(Main.this, SetupZone.class);
        startActivity(in);
        finish();
    }

    private void goToLogin() {
        Intent in = new Intent(Main.this, LoginActivity.class);
        startActivity(in);
        finish();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void nfcChecker() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            // NFC is not available for device
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            // NFC is available for device but not enabled
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Please Enable NFC");
            dialog.setMessage("The app requires NFC and cannot function without it, please turn on NFC to continue using this application :)");
            dialog.setPositiveButton("NFC Settings", new DialogInterface.OnClickListener() {
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

    private void enableReaderMode() {
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, new NFCCardReader(this), NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK | NfcAdapter.FLAG_READER_NFC_B, null);
        }
    }

    public void displayTagId(final String tagId) {
        //final TextView txtTagId = (TextView) findViewById(R.id.txtTagId);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                prompt.setText(tagId);
            }
        });
    }

}
