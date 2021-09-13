package com.dicoding.picodiploma.mynotesapp.db

import android.provider.BaseColumns

/**
 * Created by dicoding on 10/12/2017.
 */

internal class DatabaseContract {

    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
        }

    }
}
