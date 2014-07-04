package zm.hashcode.android.mshengu.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hashcode on 2014/06/27.
 */
public class UserTable {
    // DB specific constants
    public static final String DB_NAME = "mshengu.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "users";
    // Provider specific constants
    // content://zm.hashcode.android.mshengu.providers.users
    public static final String AUTHORITY = "zm.hashcode.android.mshengu.providers.users";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int ALL_ROWS = 1;
    public static final int SINGLE_ROW = 2;
    public static final String USER_TYPE_ITEM = "vnd.android.cursor.item/vnd.zm.hashcode.android.mshengu.provider.user";
    public static final String USER_TYPE_DIR = "vnd.android.cursor.dir/vnd.zm.hashcode.android.mshengu.provider.user";
    public static final String DEFAULT_SORT = Column.USERNAME + " DESC";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String USERNAME = "username";
        public static final String DEVICEKEY = "devicekey";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }

}
