package zm.hashcode.android.mshengu.database;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by hashcode on 2014/06/27.
 */
public class LocationsTable {
    // DB specific constants
    public static final String DB_NAME = "mshengu.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "locations";
    // Provider specific constants
    // content://zm.hashcode.android.mshengu.providers.locations
    public static final String AUTHORITY = "zm.hashcode.android.mshengu.providers.locations";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int ALL_ROWS = 1;
    public static final int SINGLE_ROW = 2;
    public static final String LOCATION_TYPE_ITEM ="vnd.android.cursor.item/vnd.zm.hashcode.android.mshengu.provider.location";
    public static final String LOCATION_TYPE_DIR = "vnd.android.cursor.dir/vnd.zm.hashcode.android.mshengu.provider.location";
    public static final String DEFAULT_SORT = Column.DATETIME + " DESC";

    public class Column {
        public static final String ID= BaseColumns._ID;
        public static final String LATITUDE= "truckid";
        public static final String LONGITUDE = "numberplate";
        public static final String DATETIME = "datetime";
    }

}
