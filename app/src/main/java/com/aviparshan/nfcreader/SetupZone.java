package com.aviparshan.nfcreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SetupZone extends AppCompatActivity {

    Spinner zonePicker;
    Button btnCnt;
    private int spinPosition;
    public final static String bundle = "ZONE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_zone);

        zonePicker = findViewById(R.id.spinner);
        btnCnt = findViewById(R.id.button);

        setupSpinner();
        zonePicker.setOnItemSelectedListener(onItemSelectedListener);
        btnCnt.setOnClickListener(onButtonClick);

        //btnCnt.setEnabled(false);

    }

    private void setupSpinner()
    {

        List<String> spinnerArray = new ArrayList<>();
        //TODO: Load zones from the server
        spinnerArray.add("Zone 1");
        spinnerArray.add("Zone 2");
        spinnerArray.add("Zone 3");
        spinnerArray.add("Zone 4");
        spinnerArray.add("Zone 5");
        // (3) create an adapter from the list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerArray
        );
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // (4) set the adapter on the spinner
        zonePicker.setAdapter(adapter);
    }

    Spinner.OnItemSelectedListener onItemSelectedListener = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            spinPosition = zonePicker.getSelectedItemPosition();
            btnCnt.setEnabled(true);
//            switch(spinPosition){
//                case 0:
//                    break;
//                case 1:
//                    break;
//                case 2:
//                    break;
//
//            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            btnCnt.setEnabled(false);
        }
    };

    Button.OnClickListener onButtonClick = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =  new Intent (SetupZone.this, Main.class);
            intent.putExtra(bundle, spinPosition); //sending currently selected zone as bundle to nfc reader
            startActivity(intent);
        }
    };
}
