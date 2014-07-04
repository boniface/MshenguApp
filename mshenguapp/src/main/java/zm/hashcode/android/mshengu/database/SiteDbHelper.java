package zm.hashcode.android.mshengu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hashcode on 2014/06/27.
 */
public class SiteDbHelper extends SQLiteOpenHelper {
    private static final String TAG = SiteDbHelper.class.getSimpleName();

    public SiteDbHelper(Context context) {
        super(context, SitesTable.DB_NAME, null, SitesTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = " CREATE TABLE " + SitesTable.TABLE
                + "( "
                + SitesTable.Column.ID + " integer primary key autoincrement, "
                + SitesTable.Column.SITEID + " text, "
                + SitesTable.Column.SITENAME + " text "
                + ")";
//
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        // Typically you do ALTER TABLE ...
        db.execSQL("drop table if exists " + SitesTable.TABLE);
        onCreate(db);

    }
}
