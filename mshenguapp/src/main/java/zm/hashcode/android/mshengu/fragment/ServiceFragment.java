package zm.hashcode.android.mshengu.fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.text.SimpleDateFormat;
import java.util.Date;

import zm.hashcode.android.mshengu.MainActivity;
import zm.hashcode.android.mshengu.R;
import zm.hashcode.android.mshengu.location.LocationUtil;
import zm.hashcode.android.mshengu.services.PostDateIntentService;


public class ServiceFragment extends Fragment {
    CheckBox wasteExtraction, cleanWasteTankAndToilet, chemicalRecharge;
    Location location;
    private double latitude = 0;
    private double longitude = 0;
    LocationUtil locationListenerClass;
    View v;
    View layout_green, layout_red;
    private String serviceType;

    RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.service, container, false);
        //Checkbox Listeners
        wasteExtractionCheckBoxListener();
        cleanWasteTankAndToiletCheckBoxListener();
        chemicalRechargeCheckBoxListener();

        Button scanButton = (Button) v.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.initiateScan();

                calculateLocation(getActivity().getApplicationContext());

            }
        });

        Button submitButton= (Button) v.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0)
            {
                EditText unitId = (EditText) getView().findViewById(R.id.unitID);
                EditText reportIncident=(EditText) getView().findViewById(R.id.reportIncident);
                if(latitude==0)
                {
                    TextView text = (TextView) layout_red.findViewById(R.id.text);
                    text.setText("Data cannot be saved on device without location");
                    Toast toast = new Toast(getView().getContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout_red);
                    toast.show();

                }else
                {
                    Date date=new Date();
                    String str_date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    Bundle bundle=new Bundle();
                    bundle.clear();
                    bundle.putString("unit_id", unitId.getText().toString());
                    bundle.putString("pump_out", getValueExtraction());
                    bundle.putString("wash_bucket", getValueClean());
                    bundle.putString("suction_out", getValueExtraction());
                    bundle.putString("recharge_bucket", getValueRecharge());
                    bundle.putString("scrub_floor", getValueClean());
                    bundle.putString("clean_perimeter", getValueClean());
                    bundle.putString("report_incident",reportIncident.getText().toString());
                    bundle.putString("latitude",String.valueOf(latitude));
                    bundle.putString("longitude",String.valueOf(longitude));
                    bundle.putString("date",str_date);
                    bundle.putString("serviceType",serviceType);
                    PostDateIntentService.startActionService(v.getContext(),bundle);
                }

                unitId.setText("");
                wasteExtraction.setChecked(false);
                cleanWasteTankAndToilet.setChecked(false);
                chemicalRecharge.setChecked(false);
                reportIncident.setText("");
                radioGroup.clearCheck();
            }
        });

        //---RadioButton---
        radioGroup = (RadioGroup) v.findViewById(R.id.service_type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton start = (RadioButton) v.findViewById(R.id.service_start_id);
                if (start.isChecked()) {
                    serviceType="START";
                } else {
                    serviceType="FINISH";
                }
            }
        });
        return v;
    }

    private String getValueClean() {
        String value = "no";

        if(cleanWasteTankAndToilet.isChecked())
            value ="yes";
        return value;
    }

    private String getValueExtraction() {
        String value = "no";

        if(wasteExtraction.isChecked())
            value ="yes";
        return value;
    }

    private String getValueRecharge() {
        String value = "no";

        if(chemicalRecharge.isChecked())
            value ="yes";
        return value;
    }

    public void wasteExtractionCheckBoxListener()
    {
        wasteExtraction=(CheckBox)v.findViewById(R.id.waster_extraction);
        wasteExtraction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {

                } else {

                }
            }
        });
    }

    public void cleanWasteTankAndToiletCheckBoxListener()
    {
        cleanWasteTankAndToilet=(CheckBox)v.findViewById(R.id.clean_water_and_toilet);
        cleanWasteTankAndToilet.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if (((CheckBox) v).isChecked()){

                } else
                {

                }
            }});


    }
    public void chemicalRechargeCheckBoxListener()
    {
        chemicalRecharge=(CheckBox)v.findViewById(R.id.chemical_recharge);
        chemicalRecharge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                } else {

                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        locationListenerClass = new LocationUtil(v.getContext());
    }

    @Override
    public void onStop() {
        locationListenerClass.stopUpdates();
        super.onStop();
    }

    public void calculateLocation(Context ctx)
    {
        try{
            location=locationListenerClass.getUpdatedLocation();
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            locationListenerClass.stopUpdates();
        }
        catch (Exception e)
        {
            Toast.makeText(ctx, "Could not find location.", Toast.LENGTH_LONG).show();
            Toast.makeText(ctx, "Turn on location services of the device", Toast.LENGTH_LONG).show();

        }

    }
}