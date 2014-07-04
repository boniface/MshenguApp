package zm.hashcode.android.mshengu.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import zm.hashcode.android.mshengu.R;
import zm.hashcode.android.mshengu.location.LocationUtil;


public class ServiceFragment extends Fragment {
    CheckBox pumpOut, washBucket, suctionOut, rechargeBucket, scrubFloor, cleanPerimeter;
    String pump_out = "no", wash_bucket = "no", suction_out = "no", recharge_bucket = "no", scrub_floor = "no", clean_perimeter = "no";
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


        return v;
    }
}