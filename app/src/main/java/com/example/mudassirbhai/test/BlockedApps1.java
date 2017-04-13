package com.example.mudassirbhai.test;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class BlockedApps1 extends AppCompatActivity{

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist1 = null;
    private List<ApplicationInfo> applist2 = null;
    private ApplicationAdapter listadaptor = null;
    public ListView lv;
    //Dint position=0;
    DBAdapter dbCon;
    public String n;
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

      /*  // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/

        packageManager = getPackageManager();

        new LoadApplications().execute();
        dbCon=new DBAdapter(this);
        dbCon.open();

        lv =(ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                names=dbCon.getAllNames();

                ApplicationInfo app = applist1.get(position);

                n = app.packageName;

                Intent i= new Intent(getApplicationContext(),TimeExtended.class);
                i.putExtra("ap", n);
                startActivity(i);
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

        /*if (item.getItemId() == android.R.id.home) {
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
                    n=info.packageName;
                    if(names.contains(n))
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
            applist1 = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(BlockedApps1.this,R.layout.snippet_list_row_unhide, applist1);

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
            progress = ProgressDialog.show(BlockedApps1.this, null,"Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
