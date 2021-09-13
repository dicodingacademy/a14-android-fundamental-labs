package com.dicoding.picodiploma.mynotesapp.db;

import android.provider.BaseColumns;

/**
 * Created by dicoding on 10/12/2017.
 */

public class DatabaseContract {

    public static final class NoteColumns implements BaseColumns {
        public static final String TABLE_NAME = "note";

        //Note title
        public static final String TITLE = "title";
        //Note description
        public static final String DESCRIPTION = "description";
        //Note date
        public static final String DATE = "date";

    }
}
