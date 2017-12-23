package com.listdome.app.gateway.database.tables;

import com.listdome.app.infrastructure.Constants.Database;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by raissa on 23/12/2017.
 */

public class IdeaTable {

    private IdeaTable() {
        // restrict instantiation
    }

    public static final String TABLE_NAME = "idea";
    public static final String TABLE_NAME_ALIAS = "ide";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String DATE_CREATE = "date_create";

    private static final List<String> ALL_COLUMNS = Collections
            .unmodifiableList(Arrays.asList(ID, NAME, DATE_CREATE));

    private static final List<String> ALL_COLUMNS_NAMED = Collections
            .unmodifiableList(Arrays.asList(
                    TABLE_NAME + Database.COLUMN_DOT + ID,
                    TABLE_NAME + Database.COLUMN_DOT + NAME,
                    TABLE_NAME + Database.COLUMN_DOT + DATE_CREATE));

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
                    + DATE_CREATE + Database.COLUMN_TYPE_STRING + Database.TABLE_SEPARATOR_CLOSE;
}
