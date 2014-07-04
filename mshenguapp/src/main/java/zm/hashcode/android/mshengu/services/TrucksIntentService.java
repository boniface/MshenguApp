package zm.hashcode.android.mshengu.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

import zm.hashcode.android.mshengu.connection.Connection;
import zm.hashcode.android.mshengu.database.SettingsTable;
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
    private static final String ACTION_BAZ = "zm.hashcode.android.mshengu.services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "zm.hashcode.android.mshengu.services.extra.PARAM1";
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
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TrucksIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
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
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
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
        Cursor cursor;
        cursor = getContentResolver().query(SettingsTable.CONTENT_URI, null, null, null, TruckTable.DEFAULT_SORT);
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
        int i = 0;
        for (TruckResources truck : trucks) {
            ContentValues values = new ContentValues();
            values.put(TruckTable.Column.NUMBERPLATE, truck.getNumberPlate());
            values.put(TruckTable.Column.TRUCKID, truck.getId());
            values.put(TruckTable.Column.VEHICLENUMBER, truck.getVehicleNumber());

            getContentResolver().insert(TruckTable.CONTENT_URI, values);

        }

        System.out.println(" NOTIFY USER AT THIS POINT");


//        ContentValues values = new ContentValues();
//        values.put(TruckTable.Column.ID,"123456");
//        values.put(TruckTable.Column.NUMBERPLATE,"CY 123 XYZ");
//        values.put(TruckTable.Column.TRUCKID,"123456");
//        values.put(TruckTable.Column.VEHICLENUMBER,"123456");
//
//        Uri uri = getContentResolver().insert(TruckTable.CONTENT_URI, values);
//
//        cursor = getContentResolver().query(TruckTable.CONTENT_URI,null,null,null,TruckTable.DEFAULT_SORT);
//
////        Uri res = Uri.parse()
//        if(cursor.moveToFirst())
//        {
//            do{
//
//               str=cursor.getString(cursor.getColumnIndexOrThrow(TruckTable.Column.ID));
//               truckid=cursor.getString(cursor.getColumnIndexOrThrow(TruckTable.Column.TRUCKID));
//               vn=cursor.getString(cursor.getColumnIndexOrThrow(TruckTable.Column.VEHICLENUMBER));
//               np=cursor.getString(cursor.getColumnIndexOrThrow(TruckTable.Column.NUMBERPLATE));
//
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//
//
//
//
//
//        System.out.println( " THE OUPTUT IS " +str);
//        System.out.println( " THE OUPTUT IS " +truckid);
//        System.out.println( " THE OUPTUT IS " +np);
//        System.out.println( " THE OUPTUT IS " +vn);
//


    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
