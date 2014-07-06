package zm.hashcode.android.mshengu.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import zm.hashcode.android.mshengu.connection.Connection;
import zm.hashcode.android.mshengu.database.SettingsTable;
import zm.hashcode.android.mshengu.database.SitesTable;
import zm.hashcode.android.mshengu.database.TruckTable;
import zm.hashcode.android.mshengu.fragment.DeploymentFragment;
import zm.hashcode.android.mshengu.resources.SiteResource;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SitesIntentService extends IntentService {
    private ArrayList<String> results = new ArrayList<String>();
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SETSITE = "zm.hashcode.android.mshengu.services.action.SETSITE";
    private static final String ACTION_LOADSITES = "zm.hashcode.android.mshengu.services.action.LOADSITES";
    private static final String ACTION_GETSITENAMES = "zm.hashcode.android.mshengu.services.action.GETSITENAMES";

    // TODO: Rename parameters
    private static final String SITE_URL = "zm.hashcode.android.mshengu.services.extra.SITE";
    private static final String SITE_TYPE = "zm.hashcode.android.mshengu.services.extra.TYPE";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSetSite(Context context, String siteurl, String siteType) {
        Intent intent = new Intent(context, SitesIntentService.class);
        intent.setAction(ACTION_SETSITE);
        intent.putExtra(SITE_URL, siteurl);
        intent.putExtra(SITE_TYPE, siteType);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionLoadSites(Context context) {
        Intent intent = new Intent(context, SitesIntentService.class);
        intent.setAction(ACTION_LOADSITES);
        context.startService(intent);
    }

    public static void startActionGetLocalSites(Context context) {
        Intent intent = new Intent(context, SitesIntentService.class);
        intent.setAction(ACTION_GETSITENAMES);
        context.startService(intent);
    }


    public SitesIntentService() {
        super("SitesIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SETSITE.equals(action)) {
                final String siteurl = intent.getStringExtra(SITE_URL);
                final String sitetype = intent.getStringExtra(SITE_TYPE);
                handleActionSetSite(siteurl, sitetype);
            } else if (ACTION_LOADSITES.equals(action)) {
                handleActionLoadSites();
            } else if (ACTION_GETSITENAMES.equals(action)) {
                handleActionGetLocalSites();
            }
        }
    }

    private void handleActionGetLocalSites() {
        final String RESULTS = "results";
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
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSetSite(String siteurl, String sitetype) {
        Cursor cursor = null;
        cursor = getContentResolver().query(SettingsTable.CONTENT_URI, null, null, null, SettingsTable.DEFAULT_SORT);
        if (cursor.moveToFirst()) {
            // WORK ON DELETE
            getContentResolver().delete(SettingsTable.CONTENT_URI, null, null);
            ContentValues values = new ContentValues();
            values.put(SettingsTable.Column.SITEURL, siteurl);
            values.put(SettingsTable.Column.SITETYPE, sitetype);
            getContentResolver().insert(SettingsTable.CONTENT_URI, values);
        } else {
            ContentValues values = new ContentValues();
            values.put(SettingsTable.Column.SITEURL, siteurl);
            values.put(SettingsTable.Column.SITETYPE, sitetype);
            getContentResolver().insert(SettingsTable.CONTENT_URI, values);
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionLoadSites() {
        Cursor siteCursor = null;
        siteCursor = getContentResolver().query(SitesTable.CONTENT_URI, null, null, null, SitesTable.DEFAULT_SORT);
        Cursor cursor;
        cursor = getContentResolver().query(SettingsTable.CONTENT_URI, null, null, null, SettingsTable.DEFAULT_SORT);
        String url = "";
        if (cursor.moveToFirst()) {
            {
                do {
                    url = cursor.getString(cursor.getColumnIndexOrThrow(SettingsTable.Column.SITEURL));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        List<SiteResource> sites = new Connection(url).getSites(2);
        if (siteCursor.moveToFirst()) {
            getContentResolver().delete(SitesTable.CONTENT_URI, null, null);
            for (SiteResource site : sites) {
                ContentValues values = new ContentValues();
                values.put(SitesTable.Column.SITEID, site.getId());
                values.put(SitesTable.Column.SITENAME, site.getName());
                getContentResolver().insert(SitesTable.CONTENT_URI, values);
            }

        }else{
            for (SiteResource site : sites) {
                ContentValues values = new ContentValues();
                values.put(SitesTable.Column.SITEID, site.getId());
                values.put(SitesTable.Column.SITENAME, site.getName());
                getContentResolver().insert(SitesTable.CONTENT_URI, values);
            }
        }
    }
}
