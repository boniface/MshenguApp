package zm.hashcode.android.mshengu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zm.hashcode.android.mshengu.database.DeviceTruckTable;
import zm.hashcode.android.mshengu.database.SettingsTable;
import zm.hashcode.android.mshengu.database.TruckTable;
import zm.hashcode.android.mshengu.services.SitesIntentService;
import zm.hashcode.android.mshengu.services.TrucksIntentService;

public class SetDevice extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner siteDropDown;
    private String url;
    private  AutoCompleteTextView trucksDropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_device);
        addSiteDropDown();
        addListenerOnButton();

        Button setSiteButton = (Button) findViewById(R.id.setdata_set_site_button);
        Button loadTrucksButton = (Button) findViewById(R.id.setdata_load_trucks_button);
        Button setTruckButton = (Button) findViewById(R.id.setdata_set_truck_button);
        Button settingsButton = (Button) findViewById(R.id.setdata_settings_button);
        final TextView deviceURL = (TextView) findViewById((R.id.setdata_site_message));
        deviceURL.setText(getUrlMessage());
        final TextView loadTrucksMessage = (TextView) findViewById(R.id.setdata_truck_message);
        loadTrucksMessage.setText("Trucks Loaded: "+getTrucksLoaded());

        final TextView settruckMessage = (TextView) findViewById(R.id.setdata_truck_set_message);
        settruckMessage.setText(getDeviceTruck());

        setSiteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SitesIntentService.startActionSetSite(v.getContext(), url, "New");
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        loadTrucksButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TrucksIntentService.startActionLoadTrucks(v.getContext());
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        setTruckButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             final String numberPlace=trucksDropDown.getText().toString();
                TrucksIntentService.startActionSetDeviceTruck(v.getContext(),numberPlace);
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });

        autoCompleteTextBox();


    }

    private String getUrlMessage() {
        String siteName =" NO URL SET";

        Cursor cursor;
        cursor = getContentResolver().query(SettingsTable.CONTENT_URI, null, null, null, SettingsTable.DEFAULT_SORT);
        if (cursor.moveToFirst()) {
            {
                do {
                    siteName = cursor.getString(cursor.getColumnIndexOrThrow(SettingsTable.Column.SITEURL));


                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return "URL: "+siteName;
    }

    private String getDeviceTruck() {
        String truckName = "NO TRUCK SET";
        Cursor cursor;
        cursor = getContentResolver().query(DeviceTruckTable.CONTENT_URI, null, null, null, DeviceTruckTable.DEFAULT_SORT);
        if (cursor.moveToFirst()) {
            {
                do {
                    truckName = cursor.getString(cursor.getColumnIndexOrThrow(DeviceTruckTable.Column.NUMBERPLATE));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }


        return "Device Truck: "+truckName;
    }

    private int getTrucksLoaded() {
        int count = 0;
        Cursor cursor;
        cursor = getContentResolver().query(TruckTable.CONTENT_URI, null, null, null, TruckTable.DEFAULT_SORT);
        count =cursor.getCount();
        cursor.close();
        return count;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_device, menu);
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

    // add items into spinner dynamically
    public void addSiteDropDown() {

        siteDropDown = (Spinner) findViewById(R.id.setdata_set_site_dropdown);
        List<String> list = new ArrayList<String>();
        list.add("http://kmis.mshengutoilethire.co.za/mshengu/api/");
        list.add("http://212.71.251.128/mshengu/api/");
        list.add("http://10.0.0.14:8080/mshengu/api/");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        siteDropDown.setAdapter(dataAdapter);
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
        siteDropDown = (Spinner) findViewById(R.id.setdata_set_site_dropdown);
        siteDropDown.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        url = String.valueOf(siteDropDown.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        url = "";
    }

    public void autoCompleteTextBox() {
        Context ctx =getApplicationContext();
        trucksDropDown = (AutoCompleteTextView) findViewById(R.id.setdata_truck_dropdown);
        List<String> trucks = new ArrayList<String>();

        Cursor cursor;
        cursor = getContentResolver().query(TruckTable.CONTENT_URI, null, null, null, TruckTable.DEFAULT_SORT);
        if (cursor.moveToFirst()) {
            {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TruckTable.Column.NUMBERPLATE));
                    trucks.add(name);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        trucksDropDown.setAdapter(new ArrayAdapter<String>(ctx, R.layout.trucks_lists, trucks));

    }

}
