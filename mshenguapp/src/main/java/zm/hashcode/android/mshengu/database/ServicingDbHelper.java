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
        String sql = " CREATE TABLE " + ServicingTable.TABLE
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
