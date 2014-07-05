package zm.hashcode.android.mshengu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import zm.hashcode.android.mshengu.database.DeviceTruckTable;
import zm.hashcode.android.mshengu.database.SettingsTable;
import zm.hashcode.android.mshengu.database.SitesTable;
import zm.hashcode.android.mshengu.database.TruckTable;
import zm.hashcode.android.mshengu.services.SitesIntentService;


public class Settings extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button homeButton = (Button) findViewById(R.id.settings_home_button);
        Button loadServiceSitesButton = (Button) findViewById(R.id.settings_load_service_sites_button);
        Button setDeviceData = (Button) findViewById(R.id.settings_set_data_device);
        final TextView setUrlStatus = (TextView) findViewById(R.id.settings_set_url_status);
        setUrlStatus.setText(getURLSet());
        final TextView loadTrucksStatus = (TextView) findViewById(R.id.settings_load_truck_status);
        loadTrucksStatus.setText("Loaded Trucks: "+getTrucksLoaded());
        final TextView setDeviceTruckStatus = (TextView) findViewById(R.id.settings_set_truck_status);
        setDeviceTruckStatus.setText(getDeviceTruckSet());
        final TextView loadSitesStatus = (TextView) findViewById(R.id.settings_load_sites_status);
        loadSitesStatus.setText("Sites Loaded: "+getSitesLoaded());
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });

        loadServiceSitesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SitesIntentService.startActionLoadSites(view.getContext());
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);



            }
        });

        setDeviceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetDevice.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);



            }
        });

    }

    private void DisplayToast(String msg) {
        Toast.makeText(getBaseContext(), msg,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private int getTrucksLoaded() {
        int count = 0;
        Cursor cursor;
        cursor = getContentResolver().query(TruckTable.CONTENT_URI, null, null, null, TruckTable.DEFAULT_SORT);
        count =cursor.getCount();
        cursor.close();
        return count;
    }

    private int getSitesLoaded() {
        int count = 0;
        Cursor cursor;
        cursor = getContentResolver().query(SitesTable.CONTENT_URI, null, null, null, SitesTable.DEFAULT_SORT);
        count =cursor.getCount();
        cursor.close();
        return count;
    }

    private String getDeviceTruckSet() {
        String value="DEVICE NOT SET";
        Cursor cursor;
        cursor = getContentResolver().query(DeviceTruckTable.CONTENT_URI, null, null, null, DeviceTruckTable.DEFAULT_SORT);
        if(cursor.getCount()>0){
            value="DEVICE TRUCK SET";
        }
        cursor.close();
        return value;
    }

    private String getURLSet() {
        String value="URL NOT SET";
        Cursor cursor;
        cursor = getContentResolver().query(SettingsTable.CONTENT_URI, null, null, null, SettingsTable.DEFAULT_SORT);
        if(cursor.getCount()>0){
            value="URL SET";
        }
        cursor.close();
        return value;
    }




}
