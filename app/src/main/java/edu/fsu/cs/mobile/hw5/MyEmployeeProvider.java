package edu.fsu.cs.mobile.hw5;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyEmployeeProvider extends ContentProvider {
    public EmployeeContract.MainDatabaseHelper mOpenHelper;
    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(EmployeeContract.AUTHORITY, EmployeeContract.TransactionEntry.TABLE, EmployeeContract.URI_TRANSACTIONS);
        sUriMatcher.addURI(EmployeeContract.AUTHORITY, EmployeeContract.TransactionEntry.TABLE + "/#", EmployeeContract.URI_TRANSACTION_ID);
    }

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, EmployeeContract.DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL(EmployeeCo   ntract.);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().
                delete("Users", selection,
                        selectionArgs);
    }

    @Override
    public String getType(Uri uri) {


        return "TEXT";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        //String fname = values.getAsString("FirstName");
        //String lname = values.getAsString("LastName");
        if(sUriMatcher.match(uri) != EmployeeContract.URI_TRANSACTIONS) {
            throw new IllegalArgumentException("Invalid insert URI: " + uri);
        }

        long rowId = db.insert(EmployeeContract.TransactionEntry.TABLE, "", values);
        // rowId > 0 if successful
        if(rowId <= 0) {
            throw new SQLException("Failed to insert into " + uri);
        }
        // Create new uri with rowId appended
        Uri insertedUri = ContentUris.withAppendedId(EmployeeContract.CONTENT_URI, rowId);
        // Notify ContentResolver of insertedUri
        getContext().getContentResolver().notifyChange(insertedUri, null);
        // Return insertedUri
        return insertedUri;
    }

    @Override
    public boolean onCreate() {
       mOpenHelper = new EmployeeContract.MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri table, String[] columns,
                        String selection, String[] args, String orderBy) {
        return mOpenHelper.getReadableDatabase()
                .query("Users", columns, selection, args, null,
                        null, orderBy);
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[]
                              selectionArgs) {

        return mOpenHelper.getWritableDatabase().
                update("Users", values, selection,
                        selectionArgs);
    }


}
