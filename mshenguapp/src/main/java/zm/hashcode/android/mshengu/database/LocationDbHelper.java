package zm.hashcode.android.mshengu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hashcode on 2014/06/27.
 */
public class LocationDbHelper extends SQLiteOpenHelper {
    private static final String TAG = LocationDbHelper.class.getSimpleName();

    public LocationDbHelper(Context context) {
        super(context, LocationsTable.DB_NAME, null, LocationsTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String
                .format("create table %s (%s int primary key, %s text, %s text, %s int)",
                        LocationsTable.TABLE,
                        LocationsTable.Column.ID,
                        LocationsTable.Column.LATITUDE,
                        LocationsTable.Column.LONGITUDE,
                        LocationsTable.Column.DATETIME);
//
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        // Typically you do ALTER TABLE ...
        db.execSQL("drop table if exists " + LocationsTable.TABLE);
        onCreate(db);

    }
}
