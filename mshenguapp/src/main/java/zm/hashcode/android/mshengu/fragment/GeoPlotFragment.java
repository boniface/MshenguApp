package zm.hashcode.android.mshengu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zm.hashcode.android.mshengu.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GeoPlotFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GeoPlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class GeoPlotFragment extends Fragment {
    View v,layout_green,layout_red;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.geoplot,container,false);
        return v;
    }
}