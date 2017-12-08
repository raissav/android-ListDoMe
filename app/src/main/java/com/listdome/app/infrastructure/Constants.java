package com.listdome.app.infrastructure;

/**
 * Created by raissa on 05/12/2017.
 */

public final class Constants {

    public static final class Database {
        private Database() {}

        public static final String CREATE_TABLE = "CREATE TABLE ";
        public static final String ALTER_TABLE = "ALTER TABLE ";
        public static final String TABLE_INNER_JOIN = " INNER JOIN ";
        public static final String TABLE_SEPARATOR_OPEN = " ( ";
        public static final String TABLE_SEPARATOR_CLOSE = " ) ";
        public static final String COLUMN_SEPARATOR = ", ";
        public static final String COLUMN_DOT = ".";
        public static final String COLUMN_ADD = " ADD ";
        public static final String COLUMN_TYPE_PK = " INTEGER PRIMARY KEY AUTOINCREMENT";
        public static final String COLUMN_TYPE_FK = " FOREIGN KEY (";
        public static final String COLUMN_TYPE_FK_REFERENCE = ") REFERENCES ";
        public static final String COLUMN_TYPE_INTEGER = " INTEGER ";
        public static final String COLUMN_TYPE_STRING = " TEXT ";
        public static final String COLUMN_TYPE_BLOB = " BLOB ";
        public static final String ORDER_BY_DESC = " DESC";
        public static final String ORDER_BY_ASC = " ASC";
    }

    public static final class Errors {
        private Errors() {}

        public static final int INVALID_STATUS = 10001;
    }
}
