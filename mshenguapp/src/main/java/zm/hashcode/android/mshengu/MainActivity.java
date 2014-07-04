package zm.hashcode.android.mshengu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.viewpagerindicator.TitlePageIndicator;

import zm.hashcode.android.mshengu.fragment.DeploymentFragment;
import zm.hashcode.android.mshengu.fragment.GeoPlotFragment;
import zm.hashcode.android.mshengu.fragment.ServiceFragment;


public class MainActivity extends FragmentActivity {
    private DeploymentFragment deploymentFragment;
    private GeoPlotFragment geoplotFragment;
    private ServiceFragment serviceFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Swapping fragments-start
        FragmentPagerAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        FragmentManager manager = getSupportFragmentManager();
        serviceFragment = (ServiceFragment) manager.findFragmentById(R.id.service);
        deploymentFragment = (DeploymentFragment) manager.findFragmentById(R.id.deployment);
        geoplotFragment = (GeoPlotFragment) manager.findFragmentById(R.id.geoplot);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            EditText unitId = (EditText) findViewById(R.id.unitID);
            unitId.setText(scanResult.getContents());
            EditText unitIdDeployment = (EditText) findViewById(R.id.unitIdDeployment);
            unitIdDeployment.setText(scanResult.getContents());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.map:
                Intent intent1 = new Intent(this, Map.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                return true;

            case R.id.menu_settings:
                Intent intent = new Intent(this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
