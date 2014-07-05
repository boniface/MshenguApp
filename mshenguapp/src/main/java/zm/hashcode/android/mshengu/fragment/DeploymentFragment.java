package zm.hashcode.android.mshengu.fragment;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

import zm.hashcode.android.mshengu.R;
import zm.hashcode.android.mshengu.database.SitesTable;
import zm.hashcode.android.mshengu.location.LocationUtil;
import zm.hashcode.android.mshengu.services.PostDateIntentService;


public class DeploymentFragment extends Fragment {
    View v, layout_green, layout_red;
    LocationUtil locationListenerClass;
    Location location;
    double latitude = 0, longitude = 0;
    List<String> sites = new ArrayList<String>();
    AutoCompleteTextView actv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.deployment, container, false);

        Button scanButton = (Button) v.findViewById(R.id.scanButtonDeployment);
        Button deployButton = (Button) v.findViewById(R.id.tagButton);


        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.initiateScan();
                EditText lat = (EditText) getView().findViewById(R.id.latDeployment);
                lat.setText("");
                EditText lon = (EditText) getView().findViewById(R.id.lonDeployment);
                lon.setText("");
                autoCompleteTextBox();

                calculateLocation(getActivity().getApplicationContext());

            }
        });

        deployButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText unitIdDeployment = (EditText) getView().findViewById(R.id.unitIdDeployment);
                EditText  lat = (EditText) getView().findViewById(R.id.latDeployment);
                EditText lon = (EditText) getView().findViewById(R.id.lonDeployment);

                Bundle bundle=new Bundle();

                bundle.putString("unit_id",unitIdDeployment.getText().toString());
                bundle.putString("latitude",lat.getText().toString());
                bundle.putString("longitude",lon.getText().toString());
                bundle.putString("site",actv.getText().toString());
                PostDateIntentService.startActionDeploy(v.getContext(),bundle);

                unitIdDeployment.setText("");
                lat.setText("");
                lon.setText("");
                actv.setText("");



            }
        });


        return v;
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

    public void autoCompleteTextBox() {
        Context ctx = getActivity().getApplicationContext();
        actv = (AutoCompleteTextView) v.findViewById(R.id.actv_site);
        List<String> sites = new ArrayList<String>();

        Cursor cursor;
        cursor = getActivity().getContentResolver().query(SitesTable.CONTENT_URI, null, null, null, SitesTable.DEFAULT_SORT);
        if (cursor.moveToFirst()) {
            {
                do {
                    String siteName = cursor.getString(cursor.getColumnIndexOrThrow(SitesTable.Column.SITENAME));
                    sites.add(siteName);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        actv.setAdapter(new ArrayAdapter<String>(ctx, R.layout.sites_list, sites));

    }

    public void calculateLocation(Context ctx) {
        try {

            location = locationListenerClass.getUpdatedLocation();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            locationListenerClass.stopUpdates();
            EditText lat = (EditText) v.findViewById(R.id.latDeployment);
            lat.setText(String.valueOf(latitude));
            EditText lon = (EditText) v.findViewById(R.id.lonDeployment);
            lon.setText(String.valueOf(longitude));
        } catch (Exception e) {
            Toast.makeText(ctx, "Could not find location.", Toast.LENGTH_LONG).show();
            Toast.makeText(ctx, "Turn on location services of the device", Toast.LENGTH_LONG).show();
            EditText lat = (EditText) v.findViewById(R.id.latDeployment);
            lat.setHint("Location not found");
            EditText lon = (EditText) v.findViewById(R.id.lonDeployment);
            lon.setHint("Location not found");
        }

    }
}