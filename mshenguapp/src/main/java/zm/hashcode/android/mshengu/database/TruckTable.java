package zm.hashcode.android.mshengu.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hashcode on 2014/06/27.
 */
public class TruckTable {
    // DB specific constants
    public static final String DB_NAME = "mshengu.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "trucks";
    // Provider specific constants
    // content://zm.hashcode.android.mshengu.providers.trucks
    public static final String AUTHORITY = "zm.hashcode.android.mshengu.providers.trucks";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int ALL_ROWS = 1;
    public static final int SINGLE_ROW = 2;
    public static final String TRUCK_TYPE_ITEM = "vnd.android.cursor.item/vnd.zm.hashcode.android.mshengu.provider.truck";
    public static final String TRUCK_TYPE_DIR = "vnd.android.cursor.dir/vnd.zm.hashcode.android.mshengu.provider.truck";
    public static final String DEFAULT_SORT = Column.NUMBERPLATE + " DESC";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String TRUCKID = "truckid";
        public static final String NUMBERPLATE = "numberplate";
        public static final String VEHICLENUMBER = "vehicleNumber";
    }

}
