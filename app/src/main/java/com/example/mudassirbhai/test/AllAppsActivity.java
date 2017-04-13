package com.example.mudassirbhai.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.example.mudassirbhai.test.AlertDialogRadio.AlertPositiveListener;


public class AllAppsActivity extends AppCompatActivity implements AlertPositiveListener {
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    private List<ApplicationInfo> applist2 = null;
    ListView lv;
    int position=0;
    DBAdapter dbCon;
    public String title;
    List<String> names=new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ImageButton ib= (ImageButton) findViewById(R.id.ibCustomBack);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

     /*   // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/

        lv = (ListView)findViewById(R.id.list);
        packageManager = getPackageManager();
        new LoadApplications().execute();
        dbCon=new DBAdapter(this);
        dbCon.open();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                names=dbCon.getAllNames();
                ApplicationInfo app = applist.get(position);

                title = app.packageName;
                if(names.contains(title)){
                    Toast.makeText(getApplicationContext(),"You have already blocked this application",Toast.LENGTH_LONG).show();
                }
                else
                { /** Getting the fragment manager */
                    FragmentManager manager = getFragmentManager();

                    /** Instantiating the DialogFragment class */
                    AlertDialogRadio alert = new AlertDialogRadio();

                    /** Creating a bundle object to store the selected item's index */
                    Bundle b  = new Bundle();

                    /** Storing the selected item's index in the bundle object */
                    b.putInt("position", position);

                    /** Setting the bundle object to the dialog fragment object */
                    alert.setArguments(b);

                    /** Creating the dialog fragment object, which will in turn open the alert dialog window */
                    alert.show(manager, "alert_dialog_radio");
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbCon.close();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainPage.class);
        startActivity(i);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	/*	MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

       /* if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            //finish(); // close this activity and return to preview activity (if there is any)
        }*/

        switch (item.getItemId()) {
            case R.id.menu_about: {
                displayAboutDialog();

                break;
            }
            default: {
                result = super.onOptionsItemSelected(item);

                break;
            }
        }

        return result;
    }

    private void displayAboutDialog() {

    }



    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        applist2 = new ArrayList<ApplicationInfo>();
        names=dbCon.getAllNames();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    String n=info.packageName;
                    if(!names.contains(n))
                        applist2.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist2;
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(AllAppsActivity.this,R.layout.snippet_list_row, applist);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            lv.setAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(AllAppsActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public void onPositiveClick(int position) {
        // TODO Auto-generated method stub
        //new LoadApplications().execute();

        this.position = position;
        String t=Android.code[this.position];

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String a=sdf.format(new Date()).toString();
        String[] s=a.split(":");
        int h=Integer.valueOf(s[0]);
        int m=Integer.valueOf(s[1]);
        int se=Integer.valueOf(s[2]);
        h+=Integer.parseInt(t);
        String H=Integer.toString(h);
        String M=Integer.toString(m);
        String sec=Integer.toString(se);
        String timer=H+":"+M+":"+sec;


        dbCon.insertRecord(title,timer);
        new LoadApplications().execute();

        Toast.makeText(this,"Successfully Blocked for " + Android.code[this.position]+" hours",Toast.LENGTH_SHORT).show();
    }
}

