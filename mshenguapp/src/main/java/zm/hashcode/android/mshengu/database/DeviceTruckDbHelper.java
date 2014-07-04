package zm.hashcode.android.mshengu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hashcode on 2014/06/27.
 */
public class DeviceTruckDbHelper extends SQLiteOpenHelper {
    private static final String TAG = DeviceTruckDbHelper.class.getSimpleName();

    public DeviceTruckDbHelper(Context context) {
        super(context, TruckTable.DB_NAME, null, DeviceTruckTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = " CREATE TABLE " + DeviceTruckTable.TABLE
                + "( "
                + DeviceTruckTable.Column.ID + " integer primary key autoincrement, "
                + DeviceTruckTable.Column.TRUCKID + " text, "
                + DeviceTruckTable.Column.NUMBERPLATE + " text, "
                + DeviceTruckTable.Column.VEHICLENUMBER + " text "
                + ")";
//
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        // Typically you do ALTER TABLE ...
        db.execSQL("drop table if exists " + DeviceTruckTable.TABLE);
        onCreate(db);

    }
}
