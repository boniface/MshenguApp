package zm.hashcode.android.mshengu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import zm.hashcode.android.mshengu.R;

public class SetDevice extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner siteDropDown;
    private  String   url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_device);
        addSiteDropDown();
        addListenerOnButton();

        Button setSiteButton=(Button)findViewById(R.id.setdata_set_site_button);
        setSiteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println( " THE URL IS NOW " +url);

////                TrucksIntentService.startActionLoadTrucks(v.getContext());
//                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);


            }
        });
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
        list.add("http://localhost:8084/mshengu/api/");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
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

        url =String.valueOf(siteDropDown.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        url="";
    }
}
