package zm.hashcode.android.mshengu.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zm.hashcode.android.mshengu.connection.Connection;
import zm.hashcode.android.mshengu.database.DeviceTruckTable;
import zm.hashcode.android.mshengu.database.SettingsTable;
import zm.hashcode.android.mshengu.database.SitesTable;
import zm.hashcode.android.mshengu.database.TruckTable;
import zm.hashcode.android.mshengu.resources.TruckResources;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TrucksIntentService extends IntentService {
    String str, truckid, vn, np;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String TAG = "Trucks Service";
    private static final String ACTION_LOAD_TRUCKS = "zm.hashcode.android.mshengu.services.action.LOADTRUCKS";
    private static final String ACTION_SET_DEVICE_TRACK = "zm.hashcode.android.mshengu.services.action.SETDEVICETRUCK";

    // TODO: Rename parameters
    private static final String TRUCK_NUMBERPLATE = "zm.hashcode.android.mshengu.services.extra.NUMBERPLATE";
    private static final String EXTRA_PARAM2 = "zm.hashcode.android.mshengu.services.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionLoadTrucks(Context context) {
        Intent intent = new Intent(context, TrucksIntentService.class);
        intent.setAction(ACTION_LOAD_TRUCKS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSetDeviceTruck(Context context, String numberPlate) {
        Intent intent = new Intent(context, TrucksIntentService.class);
        intent.setAction(ACTION_SET_DEVICE_TRACK);
        intent.putExtra(TRUCK_NUMBERPLATE, numberPlate);
        context.startService(intent);
    }

    public TrucksIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOAD_TRUCKS.equals(action)) {
                handleActionLoadTrucks();
            } else if (ACTION_SET_DEVICE_TRACK.equals(action)) {
                final String numberPlate = intent.getStringExtra(TRUCK_NUMBERPLATE);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionSetDeviceTruck(numberPlate);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroyed");
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionLoadTrucks() {
        Cursor trucksCursor = null;
        trucksCursor = getContentResolver().query(TruckTable.CONTENT_URI, null, null, null, TruckTable.DEFAULT_SORT);
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
        List<TruckResources> trucks = new Connection(url).getTrucks();

        if (trucksCursor.moveToFirst()) {
            getContentResolver().delete(TruckTable.CONTENT_URI, null, null);
            for (TruckResources truck : trucks) {
                ContentValues values = new ContentValues();
                values.put(TruckTable.Column.NUMBERPLATE, truck.getNumberPlate());
                values.put(TruckTable.Column.TRUCKID, truck.getId());
                values.put(TruckTable.Column.VEHICLENUMBER, truck.getVehicleNumber());
                getContentResolver().insert(TruckTable.CONTENT_URI, values);
            }
        } else {
            for (TruckResources truck : trucks) {
                ContentValues values = new ContentValues();
                values.put(TruckTable.Column.NUMBERPLATE, truck.getNumberPlate());
                values.put(TruckTable.Column.TRUCKID, truck.getId());
                values.put(TruckTable.Column.VEHICLENUMBER, truck.getVehicleNumber());
                getContentResolver().insert(TruckTable.CONTENT_URI, values);
            }
        }


    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSetDeviceTruck(String numberPlate) {
        Map<String, String> truck = getTruck(numberPlate);
        Cursor cursor = null;
        cursor = getContentResolver().query(DeviceTruckTable.CONTENT_URI, null, null, null, DeviceTruckTable.DEFAULT_SORT);

        if (cursor.moveToFirst()) {
            // WORK ON DELETE
            getContentResolver().delete(DeviceTruckTable.CONTENT_URI, null, null);

            ContentValues values = new ContentValues();
            values.put(DeviceTruckTable.Column.NUMBERPLATE, truck.get("numberPlate"));
            values.put(DeviceTruckTable.Column.TRUCKID, truck.get("truckId"));
            values.put(DeviceTruckTable.Column.VEHICLENUMBER, truck.get("vehicleNumber"));

            getContentResolver().insert(DeviceTruckTable.CONTENT_URI, values);

        } else {
            ContentValues values = new ContentValues();
            values.put(DeviceTruckTable.Column.NUMBERPLATE, truck.get("numberPlate"));
            values.put(DeviceTruckTable.Column.TRUCKID, truck.get("truckId"));
            values.put(DeviceTruckTable.Column.VEHICLENUMBER, truck.get("vehicleNumber"));

            getContentResolver().insert(DeviceTruckTable.CONTENT_URI, values);

        }
    }

    private Map<String, String> getTruck(String numberPlate) {
        Map<String, String> truck = new HashMap<String, String>();
        Cursor cursor;
        cursor = getContentResolver().query(TruckTable.CONTENT_URI, null, null, null, TruckTable.DEFAULT_SORT);
        if (cursor.moveToFirst()) {
            do {
                np = cursor.getString(cursor.getColumnIndexOrThrow(TruckTable.Column.NUMBERPLATE));
                if (numberPlate.equalsIgnoreCase(np)) {
                    truckid = cursor.getString(cursor.getColumnIndexOrThrow(TruckTable.Column.TRUCKID));
                    truck.put("truckId", truckid);
                    vn = cursor.getString(cursor.getColumnIndexOrThrow(TruckTable.Column.VEHICLENUMBER));
                    truck.put("vehicleNumber", vn);

                    truck.put("numberPlate", np);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return truck;
    }
}
