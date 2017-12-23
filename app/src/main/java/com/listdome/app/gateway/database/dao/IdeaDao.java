package com.listdome.app.gateway.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.listdome.app.entity.Idea;
import com.listdome.app.gateway.database.tables.IdeaTable;
import com.listdome.app.infrastructure.Constants.Database;
import com.listdome.app.infrastructure.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raissa on 23/12/2017.
 */

public class IdeaDao extends BaseDao<Idea> {

    private static final String TAG = IdeaDao.class.getSimpleName();

    public IdeaDao(final Context context) {
        super(context);
    }

    @Override
    public Idea get(final String id) {
        Log.v(TAG, "[method] get - id: " + id);

        Idea result = null;

        final SQLiteDatabase db = getDBForRead();

        try {
            final Cursor cursor = db.query(
                    IdeaTable.TABLE_NAME,               // table
                    IdeaTable.getAllColumns(),          // column names
                    (IdeaTable.ID + " = ? "),           // selection
                    new String[]{id},                   // selections values
                    null,                      // group by
                    null,                       // having
                    null,                      // order by
                    null);                       // limit

            result = getIdea(cursor);

        } finally {
            closeDB();
        }

        return result;
    }

    @Override
    public List<Idea> getAll() {
        Log.v(TAG, "[method] getAll");

        List<Idea> result;

        final SQLiteDatabase db = getDBForRead();

        try {
            final Cursor cursor = db.query(
                    IdeaTable.TABLE_NAME,                       // table
                    IdeaTable.getAllColumns(),                  // column names
                    null,                              // selection
                    null,                           // selections values
                    null,                               // group by
                    null,                                // having
                    IdeaTable.TABLE_NAME +
                            Database.COLUMN_DOT +
                            IdeaTable.ID +
                            Database.ORDER_BY_ASC,              // order by
                    null);                                 // limit

            result = getIdeaList(cursor);

        } finally {
            closeDB();
        }

        return result;
    }

    @Override
    public long add(final Idea idea) {
        Log.v(TAG, "[method] add - idea: " + idea);

        long result = -1;

        final SQLiteDatabase db = getDBForWrite();

        try {
            final ContentValues values = parseToValues(idea);

            try {
                result = db.insertOrThrow(IdeaTable.TABLE_NAME, null, values);
                Log.v(TAG, "[result] add idea - Success");
            } catch (final SQLException ex) {
                Log.e(TAG, "[result] add idea - Error", ex);
            }
        } finally {
            closeDB();
        }

        return result;
    }

    @Override
    public boolean addAll(final List<Idea> ideaList) {

        long result = -1;

        if (ideaList != null && !ideaList.isEmpty()) {
            Log.v(TAG, "[method] addAll - ideaList: " + ideaList.size());

            final SQLiteDatabase db = getDBForWrite();

            try {
                db.beginTransaction();

                try {
                    for (final Idea idea : ideaList) {
                        final ContentValues values = parseToValues(idea);
                        result = db.insertOrThrow(IdeaTable.TABLE_NAME, null, values);
                    }
                    db.setTransactionSuccessful();
                    Log.v(TAG, "[result] add all - Success");

                } catch (final SQLException ex) {
                    Log.e(TAG, "[result] add all - Error", ex);
                } finally {
                    db.endTransaction();
                }
            } finally {
                closeDB();
            }
        }

        return result != -1;
    }

    @Override
    public boolean delete(final Idea idea) {
        Log.v(TAG, "[method] delete - idea: " + idea);

        int result = -1;

        final SQLiteDatabase db = getDBForWrite();

        try {
            result = db.delete(IdeaTable.TABLE_NAME,            // table name
                    IdeaTable.ID + " = ? ",         // selections
                    new String[]{idea.getId().toString()});     // selections values
        } finally {
            closeDB();
        }

        return result != 0;
    }

    @Override
    public boolean deleteAll() {
        Log.v(TAG, "[method] deleteAll");

        int result = -1;

        final SQLiteDatabase db = getDBForWrite();

        try {
            result = db.delete(IdeaTable.TABLE_NAME, null, null);
        } finally {
            closeDB();
        }

        return result != 0;
    }

    @Override
    public boolean update(final Idea idea) {
        Log.v(TAG, "[method] update idea: " + idea.getId());

        int result = -1;

        final SQLiteDatabase db = getDBForWrite();

        try {
            final ContentValues values = parseToValues(idea);

            result = db.update(IdeaTable.TABLE_NAME,                     // table name
                    values,                                              // values
                    IdeaTable.ID + " = ? ",                  // selections
                    new String[]{idea.getId().toString()});              // selections values
        } finally {
            closeDB();
        }

        return result != 0;
    }

    private static Idea parseToObj(final Cursor cursor) {
        final Idea idea = new Idea();

        idea.setId(cursor.getLong(cursor.getColumnIndex(IdeaTable.ID)));
        idea.setName(cursor.getString(cursor.getColumnIndex(IdeaTable.NAME)));
        idea.setDateCreate(DateHelper.parseStringToDate(
                cursor.getString(cursor.getColumnIndex(IdeaTable.DATE_CREATE)), DateHelper.patternEn));

        return idea;
    }

    @Override
    public ContentValues parseToValues(final Idea idea) {
        Log.v(TAG, "[method] parseToValues");

        final ContentValues values = new ContentValues();

        values.put(IdeaTable.NAME, idea.getName());
        values.put(IdeaTable.DATE_CREATE, DateHelper.parseDateToString(
                idea.getDateCreate(), DateHelper.patternEn));

        return values;
    }

    @Nullable
    private List<Idea> getIdeaList(final Cursor cursor) {
        List<Idea> ideaList = null;

        if (cursor != null) {
            ideaList = new ArrayList<>();

            try {
                while (cursor.moveToNext()) {
                    final Idea idea = parseToObj(cursor);
                    ideaList.add(idea);
                }
            } finally {
                cursor.close();
            }
        }
        return ideaList;
    }

    @Nullable
    private Idea getIdea(final Cursor cursor) {
        Idea idea = null;

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    idea = parseToObj(cursor);
                }
            } finally {
                cursor.close();
            }
        }
        return idea;
    }

}
