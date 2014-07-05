package zm.hashcode.android.mshengu.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import java.util.Date;

import zm.hashcode.android.mshengu.connection.Connection;
import zm.hashcode.android.mshengu.connection.MobileResponseMessage;
import zm.hashcode.android.mshengu.database.SettingsTable;
import zm.hashcode.android.mshengu.resources.UnitDeliveryResource;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PostDateIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_DEPLOY = "zm.hashcode.android.mshengu.services.action.DEPLOY";
    private static final String ACTION_SERVICE = "zm.hashcode.android.mshengu.services.action.SERVICE";

    // TODO: Rename parameters
    private static final String EXTRA_UNIT = "zm.hashcode.android.mshengu.services.extra.UNIT";
    private static final String EXTRA_LAT = "zm.hashcode.android.mshengu.services.extra.LAT";
    private static final String EXTRA_LONG = "zm.hashcode.android.mshengu.services.extra.LONG";
    private static final String EXTRA_SITE = "zm.hashcode.android.mshengu.services.extra.SITE";
//    private static final String EXTRA_PARAM2 = "zm.hashcode.android.mshengu.services.extra.PARAM2";
    private static final String EXTRA_PARAM2 = "zm.hashcode.android.mshengu.services.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *   bundle.putString("unit_id",unitIdDeployment.getText().toString());
     bundle.putString("latitude",lat.getText().toString());
     bundle.putString("longitude",lon.getText().toString());
     bundle.putString("site",actv.getText().toString());
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionDeploy(Context context, Bundle data) {
        Intent intent = new Intent(context, PostDateIntentService.class);
        intent.setAction(ACTION_DEPLOY);
        intent.putExtra(EXTRA_UNIT, data.getString("unit_id"));
        intent.putExtra(EXTRA_LAT, data.getString("latitude"));
        intent.putExtra(EXTRA_LONG, data.getString("longitude"));
        intent.putExtra(EXTRA_SITE, data.getString("site"));
        context.startService(intent);


    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionService(Context context, Bundle data) {
        Intent intent = new Intent(context, PostDateIntentService.class);
        intent.setAction(ACTION_SERVICE);
//        intent.putExtra(EXTRA_PARAM1, data.getString(""));
//        intent.putExtra(EXTRA_PARAM2, param2);
//        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public PostDateIntentService() {
        super("PostDateIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DEPLOY.equals(action)) {
                final String unit = intent.getStringExtra(EXTRA_UNIT);
                final String latitude = intent.getStringExtra(EXTRA_LAT);
                final String longitude = intent.getStringExtra(EXTRA_LONG);
                final String site = intent.getStringExtra(EXTRA_SITE);
                handleActionDeploy(unit, latitude,longitude,site);
            } else if (ACTION_SERVICE.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeploy(String unit, String latitude, String longitude, String site) {
        String url = getUrl();
        final UnitDeliveryResource unitDeliveryResource = new UnitDeliveryResource();
        unitDeliveryResource.setUnitId(unit);
        unitDeliveryResource.setLatitude(latitude);
        unitDeliveryResource.setLongitude(longitude);
        unitDeliveryResource.setSiteId(site);
        unitDeliveryResource.setDate(new Date().toString());
        MobileResponseMessage message = new Connection(url).postDeployment(unitDeliveryResource);
        System.out.println("The Message about the POST IS "+message.getMessage());

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String getUrl(){
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
        return url;
    }
}
