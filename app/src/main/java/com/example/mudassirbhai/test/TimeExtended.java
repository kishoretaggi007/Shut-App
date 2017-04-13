package com.example.mudassirbhai.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TimeExtended extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    public String app=null;
    DBAdapter dbCon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_extended);

       /* // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/
        dbCon = new DBAdapter(this);
        dbCon.open();
        addListenerOnButton();

    }

    protected void onDestroy() {
        super.onDestroy();
        dbCon.close();
    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        btnDisplay = (Button) findViewById(R.id.button1);

        btnDisplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                app=getIntent().getExtras().getString("ap");

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                String x = (radioButton.getText()).toString();
                String[] sp = x.split(" ");
                int ho = Integer.valueOf(sp[0]);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String a = sdf.format(new Date()).toString();
                String[] s = a.split(":");
                int h = Integer.valueOf(s[0]);
                int m = Integer.valueOf(s[1]);
                int se = Integer.valueOf(s[2]);
                h += ho;
                String H = Integer.toString(h);
                String M = Integer.toString(m);
                String sec = Integer.toString(se);
                String timer = H + ":" + M + ":" + sec;
                dbCon.insertRecord(app, timer);
                Toast.makeText(
                        TimeExtended.this,
                        app + "has been blocked successfully for "
                                + radioButton.getText(), Toast.LENGTH_SHORT)
                        .show();
                Intent intent=new Intent(getApplicationContext(),BlockedApps1.class);
                startActivity(intent);
            }

        });



    }




}
