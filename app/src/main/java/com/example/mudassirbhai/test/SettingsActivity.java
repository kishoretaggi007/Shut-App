package com.example.mudassirbhai.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    ListView lv1, lv2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageButton ib= (ImageButton) findViewById(R.id.ibCustomBack);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


      /*  // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/
        lv1 = (ListView) findViewById(R.id.listView1);
        lv2 = (ListView) findViewById(R.id.listView2);

        String sa1[]={"Easy Touch"};
        ArrayAdapter<String> aa = new ArrayAdapter<String>(SettingsActivity.this,android.R.layout.simple_list_item_1,sa1);
        lv1.setAdapter(aa);

        String sa2[] = {"Change Color"};
        ArrayAdapter<String> ab = new ArrayAdapter<String>(SettingsActivity.this,android.R.layout.simple_list_item_1,sa2);
        lv2.setAdapter(ab);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder abd = new AlertDialog.Builder(SettingsActivity.this);
                abd.setTitle("Easy Touch");
                abd.setMessage("Enable/Disable Easy Touch Mode...???");

                abd.setPositiveButton("Enable/Disable",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Toast.makeText(SettingsActivity.this, "Easy Mode is enabled/disabled", Toast.LENGTH_SHORT).show();
                    }
                });
                abd.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                abd.show();
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog d1 = new Dialog(SettingsActivity.this);
                d1.setContentView(R.layout.cust_color);
                ImageButton i1=(ImageButton) d1.findViewById(R.id.imageButton1);
                ImageButton i2=(ImageButton) d1.findViewById(R.id.imageButton2);
                ImageButton i3=(ImageButton) d1.findViewById(R.id.imageButton3);
                ImageButton i4=(ImageButton) d1.findViewById(R.id.imageButton4);
                ImageButton i5=(ImageButton) d1.findViewById(R.id.imageButton5);
                ImageButton i6=(ImageButton) d1.findViewById(R.id.imageButton6);

                Button b1 = (Button) d1.findViewById(R.id.button1);
                Button b2 = (Button) d1.findViewById(R.id.button2);
                i1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d1.cancel();
                    }
                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d1.cancel();
                        Toast.makeText(SettingsActivity.this,"Default color has been set", Toast.LENGTH_SHORT).show();
                    }
                });
                d1.show();
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainPage.class);
        startActivity(i);
        finish();

    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            //finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }*/

}
