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

import zm.hashcode.android.mshengu.database.TruckDbHelper;
import zm.hashcode.android.mshengu.database.TruckTable;

import static zm.hashcode.android.mshengu.database.TruckTable.TRUCK_TYPE_DIR;

public class UsersProvider extends ContentProvider {
    public UsersProvider() {
    }

    private static final String TAG = TrucksProvider.class.getSimpleName();
    private TruckDbHelper dbHelper;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(TruckTable.AUTHORITY, TruckTable.TABLE, TruckTable.ALL_ROWS);
        sURIMatcher.addURI(TruckTable.AUTHORITY, TruckTable.TABLE + "/#", TruckTable.SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new TruckDbHelper(getContext());
        Log.d(TAG, "onCreated");
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case TruckTable.ALL_ROWS:
                Log.d(TAG, "gotType: " + TRUCK_TYPE_DIR);
                return TRUCK_TYPE_DIR;
            case TruckTable.SINGLE_ROW:
                Log.d(TAG, "gotType: " + TruckTable.TRUCK_TYPE_ITEM);
                return TruckTable.TRUCK_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("Illegal URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri ret = null;
        // Assert correct uri
        if (sURIMatcher.match(uri) != TruckTable.ALL_ROWS) {
            throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insertWithOnConflict(TruckTable.TABLE, null,
                values, SQLiteDatabase.CONFLICT_IGNORE);
        // Was insert successful?
        if (rowId != -1) {
            long id = values.getAsLong(TruckTable.Column.ID);
            ret = ContentUris.withAppendedId(uri, id);
            Log.d(TAG, "inserted uri: " + ret);
            // Notify that data for this uri has changed
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ret;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String where;
        switch (sURIMatcher.match(uri)) {
            case TruckTable.ALL_ROWS:
                // so we count updated rows
                where = selection;
                break;
            case TruckTable.SINGLE_ROW:
                long id = ContentUris.parseId(uri);
                where = TruckTable.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( "
                        + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.update(TruckTable.TABLE, values, where,
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
            case TruckTable.ALL_ROWS:
// so we count deleted rows
                where = (selection == null) ? "1" : selection;
                break;
            case TruckTable.SINGLE_ROW:
                long id = ContentUris.parseId(uri);
                where = TruckTable.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( "
                        + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete(TruckTable.TABLE, where, selectionArgs);
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
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(TruckTable.TABLE);

        switch (sURIMatcher.match(uri)) {
            case TruckTable.ALL_ROWS:
                break;

            case TruckTable.SINGLE_ROW:
                // adding the ID to the original query
                queryBuilder.appendWhere(TruckTable.Column.ID + "=" + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }

        String orderBy = (TextUtils.isEmpty(sortOrder)) ? TruckTable.DEFAULT_SORT : sortOrder;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, orderBy);
        // register for uri changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d(TAG, "queried records: " + cursor.getCount());
        return cursor;
    }

    private void checkColumns(String[] projection) {
        String[] available = { TruckTable.Column.ID,TruckTable.Column.TRUCKID, TruckTable.Column.NUMBERPLATE, TruckTable.Column.VEHICLENUMBER};
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
