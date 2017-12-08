package com.listdome.app.gateway.database.tables;

import com.listdome.app.infrastructure.Constants;
import com.listdome.app.infrastructure.Constants.Database;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by raissa on 08/12/2017.
 */

public class TaskTable {

    private TaskTable() {
        // restrict instantiation
    }

    public static final String TABLE_NAME = "task";
    public static final String TABLE_NAME_ALIAS = "tsk";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String IMPORTANT = "important";
    public static final String STATUS = "status";
    public static final String DATE_BEGIN = "date_begin";
    public static final String DATE_END = "date_end";

    private static final List<String> ALL_COLUMNS = Collections
            .unmodifiableList(Arrays.asList(ID, NAME, IMPORTANT, STATUS, DATE_BEGIN, DATE_END));

    private static final List<String> ALL_COLUMNS_NAMED = Collections
            .unmodifiableList(Arrays.asList(
                    TABLE_NAME + Database.COLUMN_DOT + ID,
                    TABLE_NAME + Database.COLUMN_DOT + NAME,
                    TABLE_NAME + Database.COLUMN_DOT + IMPORTANT,
                    TABLE_NAME + Database.COLUMN_DOT + STATUS,
                    TABLE_NAME + Database.COLUMN_DOT + DATE_BEGIN,
                    TABLE_NAME + Database.COLUMN_DOT + DATE_END));

    public static String[] getAllColumns() {
        return ALL_COLUMNS.toArray(new String[ALL_COLUMNS.size()]);
    }

    public static String[] getAllColumnsNamed() {
        return ALL_COLUMNS_NAMED.toArray(new String[ALL_COLUMNS_NAMED.size()]);
    }

    public static final String SQL_CREATE =
            Database.CREATE_TABLE + TABLE_NAME + Database.TABLE_SEPARATOR_OPEN
                    + ID + Database.COLUMN_TYPE_PK + Database.COLUMN_SEPARATOR
                    + NAME + Database.COLUMN_TYPE_STRING + Database.COLUMN_SEPARATOR
                    + IMPORTANT + Database.COLUMN_TYPE_INTEGER + Database.COLUMN_SEPARATOR
                    + STATUS + Database.COLUMN_TYPE_INTEGER + Database.COLUMN_SEPARATOR
                    + DATE_BEGIN + Database.COLUMN_TYPE_STRING + Database.COLUMN_SEPARATOR
                    + DATE_END + Database.COLUMN_TYPE_STRING + Database.TABLE_SEPARATOR_CLOSE;
}
