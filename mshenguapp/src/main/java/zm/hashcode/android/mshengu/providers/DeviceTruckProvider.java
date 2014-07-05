package zm.hashcode.android.mshengu.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;

import zm.hashcode.android.mshengu.database.DeviceTruckDbHelper;
import zm.hashcode.android.mshengu.database.DeviceTruckTable;


public class DeviceTruckProvider extends ContentProvider {
    public DeviceTruckProvider() {
    }

    private static final String TAG = DeviceTruckProvider.class.getSimpleName();
    private DeviceTruckDbHelper dbHelper;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(DeviceTruckTable.AUTHORITY, DeviceTruckTable.TABLE, DeviceTruckTable.ALL_ROWS);
        sURIMatcher.addURI(DeviceTruckTable.AUTHORITY, DeviceTruckTable.TABLE + "/#", DeviceTruckTable.SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DeviceTruckDbHelper(getContext());
        Log.d(TAG, "onCreated");
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case DeviceTruckTable.ALL_ROWS:
                Log.d(TAG, "gotType: " + DeviceTruckTable.DEVICETRUCK_TYPE_DIR);
                return DeviceTruckTable.DEVICETRUCK_TYPE_DIR;
            case DeviceTruckTable.SINGLE_ROW:
                Log.d(TAG, "gotType: " + DeviceTruckTable.DEVICETRUCK_TYPE_ITEM);
                return DeviceTruckTable.DEVICETRUCK_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("Illegal URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri ret = null;
        // Assert correct uri
        if (sURIMatcher.match(uri) != DeviceTruckTable.ALL_ROWS) {
            throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insertWithOnConflict(DeviceTruckTable.TABLE, null,
                values, SQLiteDatabase.CONFLICT_IGNORE);
        // Was insert successful?
//        if (rowId != -1) {
//            long id = values.getAsLong(DeviceTruckTable.Column.ID);
//            ret = ContentUris.withAppendedId(uri, id);
//            Log.d(TAG, "inserted uri: " + ret);
//            // Notify that data for this uri has changed
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
        return ret;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String where;
        switch (sURIMatcher.match(uri)) {
            case DeviceTruckTable.ALL_ROWS:
                // so we count updated rows
                where = selection;
                break;
            case DeviceTruckTable.SINGLE_ROW:
                long id = ContentUris.parseId(uri);
                where = DeviceTruckTable.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( "
                        + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.update(DeviceTruckTable.TABLE, values, where,
                selectionArgs);
        if (ret > 0) {
            // Notify that data for this uri has changed
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "updated records: " + ret);
        return ret;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where;
        switch (sURIMatcher.match(uri)) {
            case DeviceTruckTable.ALL_ROWS:
// so we count deleted rows
                where = (selection == null) ? "1" : selection;
                break;
            case DeviceTruckTable.SINGLE_ROW:
                long id = ContentUris.parseId(uri);
                where = DeviceTruckTable.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( "
                        + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete(DeviceTruckTable.TABLE, where, selectionArgs);
        if (ret > 0) {
            // Notify that data for this uri has changed
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "deleted records: " + ret);
        return ret;
    }

    // SELECT username, message, created_at FROM status WHERE user='bob' ORDER
    // BY created_at DESC;
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(DeviceTruckTable.TABLE);

        switch (sURIMatcher.match(uri)) {
            case DeviceTruckTable.ALL_ROWS:
                break;

            case DeviceTruckTable.SINGLE_ROW:
                // adding the ID to the original query
                queryBuilder.appendWhere(DeviceTruckTable.Column.ID + "=" + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, DeviceTruckTable.DEFAULT_SORT);
        // register for uri changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d(TAG, "queried records: " + cursor.getCount());
        return cursor;
    }

    private void checkColumns(String[] projection) {
        String[] available = {DeviceTruckTable.Column.ID, DeviceTruckTable.Column.TRUCKID, DeviceTruckTable.Column.NUMBERPLATE, DeviceTruckTable.Column.VEHICLENUMBER};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
