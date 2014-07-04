package zm.hashcode.android.mshengu.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import zm.hashcode.android.mshengu.database.SettingsTable;
import zm.hashcode.android.mshengu.database.SitesTable;
import zm.hashcode.android.mshengu.fragment.DeploymentFragment;

/**
 * Created by hashcode on 2014/07/03.
 */
public class SiteDropDownIntents extends IntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static final String RESULTS = "results";
    private ArrayList<String> results = new ArrayList<String>();

    public SiteDropDownIntents() {
        super("SiteDropDownIntents");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println(" THIS INTENT HAS BEEN CALLED");

        Cursor cursor;
        cursor = getContentResolver().query(SitesTable.CONTENT_URI, null, null, null, SitesTable.DEFAULT_SORT);
        if (cursor.moveToFirst()) {
            {
                do {
                    String siteName = cursor.getString(cursor.getColumnIndexOrThrow(SitesTable.Column.SITENAME));
                    results.add(siteName);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        System.out.println(" THE SIZE OF THE RESULTS IS "+ results.size());



        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(DeploymentFragment.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putStringArrayListExtra(RESULTS, results);
        sendBroadcast(broadcastIntent);
    }

}