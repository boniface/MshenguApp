package zm.hashcode.android.mshengu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hashcode on 2014/06/27.
 */
public class TruckDbHelper extends SQLiteOpenHelper {
    private static final String TAG = TruckDbHelper.class.getSimpleName();

    public TruckDbHelper(Context context) {
        super(context, TruckTable.DB_NAME, null, TruckTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String
                .format("create table %s (%s int primary key, %s text, %s text, %s int)",
                        TruckTable.TABLE,
                        TruckTable.Column.ID,
                        TruckTable.Column.TRUCKID,
                        TruckTable.Column.NUMBERPLATE,
                        TruckTable.Column.VEHICLENUMBER);
//
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        // Typically you do ALTER TABLE ...
        db.execSQL("drop table if exists " + TruckTable.TABLE);
        onCreate(db);

    }
}
