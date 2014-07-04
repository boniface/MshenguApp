package zm.hashcode.android.mshengu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hashcode on 2014/06/27.
 */
public class SettingDbHelper extends SQLiteOpenHelper {
    private static final String TAG = SettingDbHelper.class.getSimpleName();

    public SettingDbHelper(Context context) {
        super(context, SettingsTable.DB_NAME, null, SettingsTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sqlDeplment = " CREATE TABLE " + DeploymentTable.TABLE
                + "( "
                + DeploymentTable.Column.ID + " integer primary key autoincrement, "
                + DeploymentTable.Column.LATITUDE + " text, "
                + DeploymentTable.Column.LONGITUDE + " text, "
                + DeploymentTable.Column.UNITID + " text, "
                + DeploymentTable.Column.SITENAME + " text "
                + ")";

        String sqlDevice = " CREATE TABLE " + DeviceTruckTable.TABLE
                + "( "
                + DeviceTruckTable.Column.ID + " integer primary key autoincrement, "
                + DeviceTruckTable.Column.TRUCKID + " text, "
                + DeviceTruckTable.Column.NUMBERPLATE + " text, "
                + DeviceTruckTable.Column.VEHICLENUMBER + " text "
                + ")";

        String sqlLocation = " CREATE TABLE " + LocationsTable.TABLE
                + "( "
                + LocationsTable.Column.ID + " integer primary key autoincrement, "
                + LocationsTable.Column.LATITUDE + " text, "
                + LocationsTable.Column.LONGITUDE + " text, "
                + LocationsTable.Column.DATETIME + " text "
                + ")";

        String sqlServicing = " CREATE TABLE " + ServicingTable.TABLE
                + "( "
                + ServicingTable.Column.ID + " integer primary key autoincrement, "
                + ServicingTable.Column.ACTION + " text, "
                + ServicingTable.Column.CHEMICALRECHARGE + " text, "
                + ServicingTable.Column.CLEANWASTE + " text, "
                + ServicingTable.Column.INCIDENT + " text, "
                + ServicingTable.Column.LATITITUDE + " text, "
                + ServicingTable.Column.LONGITUDE + " text, "
                + ServicingTable.Column.UNITID + " text, "
                + ServicingTable.Column.WATEREXTRACTION + " text "
                + ")";

        String sqlSettings = " CREATE TABLE " + SettingsTable.TABLE
                + "( "
                + SettingsTable.Column.ID + " integer primary key autoincrement, "
                + SettingsTable.Column.SITETYPE + " text, "
                + SettingsTable.Column.SITEURL + " text "
                + ")";

        String sqlSites = " CREATE TABLE " + SitesTable.TABLE
                + "( "
                + SitesTable.Column.ID + " integer primary key autoincrement, "
                + SitesTable.Column.SITEID + " text, "
                + SitesTable.Column.SITENAME + " text "
                + ")";

        String sqlTruck = " CREATE TABLE " + TruckTable.TABLE
                + "( "
                + TruckTable.Column.ID + " integer primary key autoincrement, "
                + TruckTable.Column.TRUCKID + " text, "
                + TruckTable.Column.NUMBERPLATE + " text, "
                + TruckTable.Column.VEHICLENUMBER + " text "
                + ")";

        String sqlUser = " CREATE TABLE " + UserTable.TABLE
                + "( "
                + UserTable.Column.ID + " integer primary key autoincrement, "
                + UserTable.Column.DEVICEKEY + " text, "
                + UserTable.Column.EMAIL + " text, "
                + UserTable.Column.PASSWORD + " text, "
                + UserTable.Column.USERNAME + " text "
                + " ) ";
//
        Log.d(TAG, "onCreate with SQL: " + sqlDeplment);
        Log.d(TAG, "onCreate with SQL: " + sqlDevice);
        Log.d(TAG, "onCreate with SQL: " + sqlLocation);
        Log.d(TAG, "onCreate with SQL: " + sqlServicing);
        Log.d(TAG, "onCreate with SQL: " + sqlSettings);
        Log.d(TAG, "onCreate with SQL: " + sqlSites);
        Log.d(TAG, "onCreate with SQL: " + sqlTruck);
        Log.d(TAG, "onCreate with SQL: " + sqlUser);
        db.execSQL(sqlDeplment);
        db.execSQL(sqlDevice);
        db.execSQL(sqlLocation);
        db.execSQL(sqlServicing);
        db.execSQL(sqlSettings);
        db.execSQL(sqlSites);
        db.execSQL(sqlTruck);
        db.execSQL(sqlUser);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        // Typically you do ALTER TABLE ...
        db.execSQL("drop table if exists " + DeploymentTable.TABLE);
        onCreate(db);
    }
}