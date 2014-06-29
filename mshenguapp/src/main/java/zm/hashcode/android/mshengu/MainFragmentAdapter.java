package zm.hashcode.android.mshengu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import zm.hashcode.android.mshengu.fragment.DeploymentFragment;
import zm.hashcode.android.mshengu.fragment.GeoPlotFragment;
import zm.hashcode.android.mshengu.fragment.ServiceFragment;
import com.viewpagerindicator.IconPagerAdapter;

/**
 * Created by hashcode on 2014/05/30.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "Service", "Deploy", "Geo Plot" };
    public int mCount= CONTENT.length;
    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getIconResId(int index)
    {
        return 0;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment=new ServiceFragment();
        switch(position)
        {
            case 0: fragment=new ServiceFragment();
                break;
            case 1: fragment=new DeploymentFragment();
                break;
            case 2: fragment=new GeoPlotFragment();
                break;


        }

        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length].toUpperCase();
    }

    @Override
    public int getCount() {
        return mCount;
    }
}
