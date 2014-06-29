package zm.hashcode.android.mshengu.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hashcode on 2014/06/27.
 */
public class ServicingTable {
    // DB specific constants
    public static final String DB_NAME = "mshengu.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "services";
    // Provider specific constants
    // content://zm.hashcode.android.mshengu.providers.service
    public static final String AUTHORITY = "zm.hashcode.android.mshengu.providers.service";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int ALL_ROWS = 1;
    public static final int SINGLE_ROW = 2;
    public static final String SERVICE_TYPE_ITEM ="vnd.android.cursor.item/vnd.zm.hashcode.android.mshengu.provider.services";
    public static final String SERVICE_TYPE_DIR = "vnd.android.cursor.dir/vnd.zm.hashcode.android.mshengu.provider.services";
    public static final String DEFAULT_SORT = Column.UNITID + " DESC";

    public class Column {
        public static final String ID= BaseColumns._ID;
        public static final String UNITID = "unitid";
        public static final String LATITITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ACTION = "action";
        public static final String INCIDENT = "incident";
        public static final String WATEREXTRACTION = "waterextraction";
        public static final String CLEANWASTE = "cleanwaste";
        public static final String CHEMICALRECHARGE = "chemicalrecharge";

    }

}
