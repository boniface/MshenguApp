package zm.hashcode.android.mshengu.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import zm.hashcode.android.mshengu.connection.Connection;
import zm.hashcode.android.mshengu.connection.MobileResponseMessage;
import zm.hashcode.android.mshengu.database.DeviceTruckTable;
import zm.hashcode.android.mshengu.database.SettingsTable;
import zm.hashcode.android.mshengu.database.TruckTable;
import zm.hashcode.android.mshengu.resources.UnitDeliveryResource;
import zm.hashcode.android.mshengu.resources.UnitServiceResource;


public class PostDateIntentService extends IntentService {

    private static final String ACTION_DEPLOY = "zm.hashcode.android.mshengu.services.action.DEPLOY";
    private static final String ACTION_SERVICE = "zm.hashcode.android.mshengu.services.action.SERVICE";

    private static final String EXTRA_UNIT = "zm.hashcode.android.mshengu.services.extra.UNIT";
    private static final String EXTRA_LAT = "zm.hashcode.android.mshengu.services.extra.LAT";
    private static final String EXTRA_LONG = "zm.hashcode.android.mshengu.services.extra.LONG";
    private static final String EXTRA_SITE = "zm.hashcode.android.mshengu.services.extra.SITE";
    Boolean pump_out_bool, wash_bucket_bool, suction_out_bool, rech_bucket_bool, scrub_floor_bool, clean_peri_bool;



    public static void startActionDeploy(Context context, Bundle data) {
        Intent intent = new Intent(context, PostDateIntentService.class);
        intent.setAction(ACTION_DEPLOY);
        intent.putExtra(EXTRA_UNIT, data.getString("unit_id"));
        intent.putExtra(EXTRA_LAT, data.getString("latitude"));
        intent.putExtra(EXTRA_LONG, data.getString("longitude"));
        intent.putExtra(EXTRA_SITE, data.getString("site"));
        context.startService(intent);


    }


    public static void startActionService(Context context, Bundle data) {
        Intent intent = new Intent(context, PostDateIntentService.class);
        intent.setAction(ACTION_SERVICE);
        intent.putExtras(data);
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
                handleActionDeploy(unit, latitude, longitude, site);
            } else if (ACTION_SERVICE.equals(action)) {
                final Bundle data = intent.getExtras();
                handleActionService(data);
            }
        }
    }


    private void handleActionDeploy(String unit, String latitude, String longitude, String site) {
        String url = getUrl();
        final UnitDeliveryResource unitDeliveryResource = new UnitDeliveryResource();
        unitDeliveryResource.setUnitId(unit);
        unitDeliveryResource.setLatitude(latitude);
        unitDeliveryResource.setLongitude(longitude);
        unitDeliveryResource.setSiteId(site);
        unitDeliveryResource.setDate(new Date().toString());
        MobileResponseMessage message = new Connection(url).postDeployment(unitDeliveryResource);
        System.out.println(" THE RETURNED MESSAGE IS "+message);
    }


    private void handleActionService(Bundle data) {
        String url = getUrl();

        final UnitServiceResource unitServiceResource = new UnitServiceResource();
        unitServiceResource.setLatitude(data.getString("latitude"));
        unitServiceResource.setLongitude(data.getString("latitude"));
        unitServiceResource.setUnitId(data.getString("unit_id"));
        unitServiceResource.setServiceType(data.getString("serviceType"));
        Map<String, Boolean> tasks = new HashMap<String, Boolean>();

        if (data.getString("pump_out").equals("yes")) {
            pump_out_bool = true;
        } else {
            pump_out_bool = false;
        }
        if (data.getString("wash_bucket").equals("yes")) {
            wash_bucket_bool = true;
        } else {
            wash_bucket_bool = false;
        }

        if (data.getString("suction_out").equals("yes")) {
            suction_out_bool = true;
        } else {
            suction_out_bool = false;
        }
        if (data.getString("scrub_floor").equals("yes")) {
            scrub_floor_bool = true;
        } else {
            scrub_floor_bool = false;
        }
        if (data.getString("recharge_bucket").equals("yes")) {
            rech_bucket_bool = true;
        } else {
            rech_bucket_bool = false;
        }
        if (data.getString("clean_perimeter").equals("yes")) {
            clean_peri_bool = true;
        } else {
            clean_peri_bool = false;
        }

        tasks.put("pumpOut", pump_out_bool);
        tasks.put("washBucket", wash_bucket_bool);
        tasks.put("suctionOut", suction_out_bool);
        tasks.put("scrubFloor", scrub_floor_bool);
        tasks.put("rechargeBacket", rech_bucket_bool);
        tasks.put("cleanPerimeter", clean_peri_bool);
        unitServiceResource.setTruckId(getDeviceTruckId());

        unitServiceResource.setServices(tasks);
        unitServiceResource.setIncident(data.getString("report_incident"));
        unitServiceResource.setDate(data.getString("date"));

        MobileResponseMessage message = new Connection(url).postUnitService(unitServiceResource);

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

    private String getDeviceTruckId() {
       String truckid = "";
        Cursor cursor;
        cursor = getContentResolver().query(DeviceTruckTable.CONTENT_URI, null, null, null, DeviceTruckTable.DEFAULT_SORT);
        if (cursor.moveToFirst()) {
            do {
                truckid = cursor.getString(cursor.getColumnIndexOrThrow(DeviceTruckTable.Column.TRUCKID));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return truckid;
    }
}
