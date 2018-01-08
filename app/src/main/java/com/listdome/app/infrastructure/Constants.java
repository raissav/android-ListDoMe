package com.listdome.app.infrastructure;

import com.listdome.app.R;

/**
 * Created by raissa on 05/12/2017.
 */

public final class Constants {

    public static final class Database {
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
        public static final int INTERNET_NOT_AVAILABLE = 500;
        public static final int INVALID_STATUS = 10001;
    }

    public static final class Event {
        public static final String OPEN_TASKS = "OPEN_TASKS";
        public static final String OPEN_IDEAS = "OPEN_IDEAS";
        public static final String OPEN_ANALYTICS = "OPEN_ANALYTICS";
        public static final String SAVE_INSPIRATION = "SAVE_INSPIRATION";
        public static final String NEW_TASK = "NEW_TASK";
        public static final String NEW_IDEA = "NEW_IDEA";
    }

    public static final class Inspirations {
        public static final int[] inspirationList = {
                R.string.idea_inspiration_hint,
                R.string.inspiration_1,
                R.string.inspiration_2,
                R.string.inspiration_3,
                R.string.inspiration_4,
                R.string.inspiration_5,
                R.string.inspiration_6,
                R.string.inspiration_7,
                R.string.inspiration_8,
                R.string.inspiration_9,
                R.string.inspiration_10,
                R.string.inspiration_11,
                R.string.inspiration_12,
                R.string.inspiration_13,
                R.string.inspiration_14,
                R.string.inspiration_15,
                R.string.inspiration_16,
                R.string.inspiration_17,
                R.string.inspiration_18,
                R.string.inspiration_19,
                R.string.inspiration_20,
                R.string.inspiration_21,
                R.string.inspiration_22,
                R.string.inspiration_23,
                R.string.inspiration_24,
                R.string.inspiration_25,
                R.string.inspiration_26,
                R.string.inspiration_27,
                R.string.inspiration_28,
                R.string.inspiration_29,
                R.string.inspiration_30,
                R.string.inspiration_31,
                R.string.inspiration_32,
                R.string.inspiration_33,
                R.string.inspiration_34,
                R.string.inspiration_35,
                R.string.inspiration_36,
                R.string.inspiration_37,
                R.string.inspiration_38,
                R.string.inspiration_39,
                R.string.inspiration_40,
                R.string.inspiration_41,
                R.string.inspiration_42,
                R.string.inspiration_43,
                R.string.inspiration_44,
                R.string.inspiration_45,
                R.string.inspiration_46,
                R.string.inspiration_47,
                R.string.inspiration_48,
                R.string.inspiration_49,
                R.string.inspiration_50,
                R.string.inspiration_51,
                R.string.inspiration_52,
                R.string.inspiration_53,
                R.string.inspiration_54,
                R.string.inspiration_55,
                R.string.inspiration_56,
                R.string.inspiration_57,
                R.string.inspiration_58,
                R.string.inspiration_59,
                R.string.inspiration_60,
                R.string.inspiration_61,
                R.string.inspiration_62,
                R.string.inspiration_63,
                R.string.inspiration_64,
                R.string.inspiration_65,
                R.string.inspiration_66,
                R.string.inspiration_67,
                R.string.inspiration_68,
                R.string.inspiration_69,
                R.string.inspiration_70,
                R.string.inspiration_71,
                R.string.inspiration_72,
                R.string.inspiration_73,
                R.string.inspiration_74,
                R.string.inspiration_75,
                R.string.inspiration_76,
                R.string.inspiration_77,
                R.string.inspiration_78,
                R.string.inspiration_79,
                R.string.inspiration_80
        };
        public static final int[] imagesList = {
                R.drawable.inspiration_ballons,
                R.drawable.inspiration_dreams,
                R.drawable.inspiration_mountains,
                //R.drawable.inspiration_ideas,
                R.drawable.inspiration_dandelion
        };
    }
}
