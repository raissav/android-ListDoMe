package com.listdome.app.gateway.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;
import com.listdome.app.gateway.database.tables.TaskTable;
import com.listdome.app.infrastructure.Constants.Database;
import com.listdome.app.infrastructure.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by raissa on 08/12/2017.
 */

public class TaskDao extends BaseDao<Task> {

    private static final String TAG = TaskDao.class.getSimpleName();

    public TaskDao(final Context context) {
        super(context);
    }

    @Override
    public Task get(final String id) {
        Log.v(TAG, "[method] get - id: " + id);

        Task result = null;

        final SQLiteDatabase db = getDBForRead();

        try {
            final Cursor cursor = db.query(
                    TaskTable.TABLE_NAME,               // table
                    TaskTable.getAllColumns(),          // column names
                    (TaskTable.ID + " = ? "),           // selection
                    new String[]{id},                   // selections values
                    null,                      // group by
                    null,                       // having
                    null,                      // order by
                    null);                       // limit

            result = getTask(cursor);

        } finally {
            closeDB();
        }

        return result;
    }

    /**
     * Get the list of task from a given status.
     * @param status
     * @return
     */
    public List<Task> getByStatus(final TaskStatus status) {
        Log.v(TAG, "[method] getByStatus - status: " + status.getId());

        List<Task> result = new ArrayList<>();

        final SQLiteDatabase db = getDBForRead();

        try {
            final Cursor cursor = db.query(
                    TaskTable.TABLE_NAME,                           // table
                    TaskTable.getAllColumns(),                      // column names
                    (TaskTable.STATUS + " = ? "),                   // selection
                    new String[]{status.getId().toString()},        // selections values
                    null,                                  // group by
                    null,                                   // having
                    TaskTable.TABLE_NAME +
                            Database.COLUMN_DOT +
                            TaskTable.IMPORTANT +
                            Database.ORDER_BY_DESC,                 // order by
                    null);                                    // limit

            result = getTaskList(cursor);

        } finally {
            closeDB();
        }

        return result;
    }

    public List<Task> getTasksByStatusInterval(final TaskStatus status, final Date begin, final Date end) {
        Log.v(TAG, "[method] getTasksByStatusInterval - status: " + status);

        List<Task> result = new ArrayList<>();

        final SQLiteDatabase db = getDBForRead();

        try {
            final Cursor cursor = db.query(
                    TaskTable.TABLE_NAME,                                       // table
                    TaskTable.getAllColumns(),                                  // column names
                    (TaskTable.STATUS + " = ? AND " +
                    TaskTable.DATE_END + " BETWEEN ? AND ?"),                   // selection
                    new String[]{status.getId().toString(),                     // selections values
                    DateHelper.parseDateToString(begin, DateHelper.patternEn),
                    DateHelper.parseDateToString(end, DateHelper.patternEn)},
                    null,                                              // group by
                    null,                                               // having
                    null,                                              // order by
                    null);                                               // limit

            result = getTaskList(cursor);

        } finally {
            closeDB();
        }

        return result;
    }

    @Override
    public List<Task> getAll() {
        Log.v(TAG, "[method] getAll");

        List<Task> result = new ArrayList<>();

        final SQLiteDatabase db = getDBForRead();

        try {
            final Cursor cursor = db.query(
                    TaskTable.TABLE_NAME,                       // table
                    TaskTable.getAllColumns(),                  // column names
                    null,                              // selection
                    null,                           // selections values
                    null,                               // group by
                    null,                                // having
                    TaskTable.TABLE_NAME +
                            Database.COLUMN_DOT +
                            TaskTable.IMPORTANT +
                            Database.ORDER_BY_DESC +
                            Database.COLUMN_SEPARATOR +
                            TaskTable.TABLE_NAME +
                            Database.COLUMN_DOT +
                            TaskTable.DATE_BEGIN +
                            Database.ORDER_BY_ASC +
                            Database.COLUMN_SEPARATOR +
                            TaskTable.TABLE_NAME +
                            Database.COLUMN_DOT +
                            TaskTable.DATE_END +
                            Database.ORDER_BY_ASC,              // order by
                    null);                                 // limit

            result = getTaskList(cursor);

        } finally {
            closeDB();
        }

        return result;
    }

    @Override
    public long add(final Task task) {
        Log.v(TAG, "[method] add - task: " + task);

        long result = -1;

        final SQLiteDatabase db = getDBForWrite();

        try {
            final ContentValues values = parseToValues(task);

            try {
                result = db.insertOrThrow(TaskTable.TABLE_NAME, null, values);
                Log.v(TAG, "[result] add task - Success");
            } catch (final SQLException ex) {
                Log.e(TAG, "[result] add task - Error", ex);
            }
        } finally {
            closeDB();
        }

        return result;
    }

    @Override
    public boolean addAll(final List<Task> taskList) {

        long result = -1;

        if (taskList != null && !taskList.isEmpty()) {
            Log.v(TAG, "[method] addAll - taskList: " + taskList.size());

            final SQLiteDatabase db = getDBForWrite();

            try {
                db.beginTransaction();

                try {
                    for (final Task task : taskList) {
                        final ContentValues values = parseToValues(task);
                        result = db.insertOrThrow(TaskTable.TABLE_NAME, null, values);
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
    public boolean delete(final Task task) {
        Log.v(TAG, "[method] delete - task: " + task);

        int result = -1;

        final SQLiteDatabase db = getDBForWrite();

        try {
            result = db.delete(TaskTable.TABLE_NAME,            // table name
                    TaskTable.ID + " = ? ",         // selections
                    new String[]{task.getId().toString()});     // selections values
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
            result = db.delete(TaskTable.TABLE_NAME, null, null);
        } finally {
            closeDB();
        }

        return result != 0;
    }

    @Override
    public boolean update(final Task task) {
        Log.v(TAG, "[method] update task: " + task.getId());

        int result = -1;

        final SQLiteDatabase db = getDBForWrite();

        try {
            final ContentValues values = parseToValues(task);

            result = db.update(TaskTable.TABLE_NAME,                     // table name
                    values,                                              // values
                    TaskTable.ID + " = ? ",                  // selections
                    new String[]{task.getId().toString()});              // selections values
        } finally {
            closeDB();
        }

        return result != 0;
    }

    private static Task parseToObj(final Cursor cursor) {
        final Task task = new Task();

        task.setId(cursor.getLong(cursor.getColumnIndex(TaskTable.ID)));
        task.setName(cursor.getString(cursor.getColumnIndex(TaskTable.NAME)));
        task.setImportant(cursor.getInt(cursor.getColumnIndex(TaskTable.IMPORTANT)) == 1);
        task.setStatus(TaskStatus.getById(cursor.getLong(cursor.getColumnIndex(TaskTable.STATUS))));
        task.setDateBegin(DateHelper.parseStringToDate(
                cursor.getString(cursor.getColumnIndex(TaskTable.DATE_BEGIN)), DateHelper.patternEn));
        task.setDateEnd(DateHelper.parseStringToDate(
                cursor.getString(cursor.getColumnIndex(TaskTable.DATE_END)), DateHelper.patternEn));

        return task;
    }

    @Override
    public ContentValues parseToValues(final Task task) {
        Log.v(TAG, "[method] parseToValues");

        final ContentValues values = new ContentValues();

        values.put(TaskTable.NAME, task.getName());
        values.put(TaskTable.IMPORTANT, task.isImportant() ? 1 : 0);
        values.put(TaskTable.STATUS, task.getStatus().getId());
        values.put(TaskTable.DATE_BEGIN, DateHelper.parseDateToString(
                task.getDateBegin(), DateHelper.patternEn));
        values.put(TaskTable.DATE_END, DateHelper.parseDateToString(
                task.getDateEnd(), DateHelper.patternEn));

        return values;
    }

    @Nullable
    private List<Task> getTaskList(final Cursor cursor) {
        List<Task> taskList = null;

        if (cursor != null) {
            taskList = new ArrayList<>();

            try {
                while (cursor.moveToNext()) {
                    final Task task = parseToObj(cursor);
                    taskList.add(task);
                }
            } finally {
                cursor.close();
            }
        }
        return taskList;
    }

    @Nullable
    private Task getTask(final Cursor cursor) {
        Task task = null;

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    task = parseToObj(cursor);
                }
            } finally {
                cursor.close();
            }
        }
        return task;
    }

}
