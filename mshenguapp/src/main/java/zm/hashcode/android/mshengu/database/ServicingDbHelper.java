package zm.hashcode.android.mshengu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hashcode on 2014/06/27.
 */
public class ServicingDbHelper extends SQLiteOpenHelper {
    private static final String TAG = ServicingDbHelper.class.getSimpleName();

    public ServicingDbHelper(Context context) {
        super(context, ServicingTable.DB_NAME, null, ServicingTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String
                .format("create table %s (%s int primary key, %s text, %s text, %s int)",
                        ServicingTable.TABLE,
                        ServicingTable.Column.ID,
                        ServicingTable.Column.ACTION,
                        ServicingTable.Column.CHEMICALRECHARGE,
                        ServicingTable.Column.CLEANWASTE,
                        ServicingTable.Column.INCIDENT,
                        ServicingTable.Column.LATITITUDE,
                        ServicingTable.Column.LONGITUDE,
                        ServicingTable.Column.UNITID,
                        ServicingTable.Column.WATEREXTRACTION
                );
//
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        // Typically you do ALTER TABLE ...
        db.execSQL("drop table if exists " + ServicingTable.TABLE);
        onCreate(db);

    }
}
