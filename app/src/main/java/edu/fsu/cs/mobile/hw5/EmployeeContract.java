package edu.fsu.cs.mobile.hw5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

public class EmployeeContract
{
    public final static String DBNAME = "NameDatabase";
    public static final String SQL_CREATE_MAIN =
            "CREATE TABLE Users ( " +
                    " _id TEXT PRIMARY KEY, " +
                    "Name TEXT, " + "Email TEXT, " + "Gender TEXT, "+ "password TEXT, "+ "department TEXT, " +
                    "lastlogin TEXT )";

    public static final int URI_TRANSACTIONS = 1;
    public static final int URI_TRANSACTION_ID = 2;

    public static final String AUTHORITY = "edu.fsu.cs.mobile.hw5.provider";

    public class TransactionEntry implements BaseColumns {
        public static final String TABLE = "Users";

        static final String _ID = "_id";
        static final String NAME = "Name";
        static final String EMAIL = "Email";
        static final String GENDER = "Gender";
        static final String PASSWORD = "password";
        static final String DEPARTMENT = "department";
        static final String LASTLOGIN = "lastlogin";
    }

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + TransactionEntry.TABLE);

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, "NamesDatabase", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

}
