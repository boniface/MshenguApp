package zm.hashcode.android.mshengu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;

import zm.hashcode.android.mshengu.R;
import zm.hashcode.android.mshengu.services.LocationIntentService;


public class DeploymentFragment extends Fragment {
    View v,layout_green,layout_red;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.deployment,container,false);

        Button scanButton = (Button) v.findViewById(R.id.scanButtonDeployment);



        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.initiateScan();
                EditText lat = (EditText) getView().findViewById(R.id.latDeployment);
                lat.setText("asdasd");
                EditText lon = (EditText) getView().findViewById(R.id.lonDeployment);
                lon.setText("sdsdsad");
//                autoCompleteTextBox();

//                calculateLocation(getActivity().getApplicationContext());

            }
        });

        return v;
    }
}