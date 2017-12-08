package com.listdome.app.gateway.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.listdome.app.gateway.database.tables.TaskTable;

/**
 * Created by raissa on 05/12/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DataBaseHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "list_do_me.db";

    public DataBaseHelper(final Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        Log.v(TAG, "[constructor]");
    }

    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        Log.v(TAG, "[method] onCreate");
        sqLiteDatabase.execSQL(TaskTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int oldVersion, final int newVersion) {
        Log.v(TAG, "[method] onUpgrade");
    }
}
