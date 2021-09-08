package com.dicoding.picodiploma.myreadwritefile

import android.content.Context

/**
 * Created by dicoding on 11/23/2016.
 */

internal object FileHelper {

    /**
     * Method yang digunakan untuk menuliskan data berupa string menjadi file
     *
     * @param fileModel get data file model
     * @param context  context aplikasi
     */
    fun writeToFile(fileModel: FileModel, context: Context) {
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    /**
     * Method yang digunakan untuk membaca data dari file
     *
     * @param context  context aplikasi
     * @param filename nama file
     * @return data berupa string
     */
    fun readFromFile(context: Context, filename: String): FileModel {
        val fileModel = FileModel()
        fileModel.filename = filename
        fileModel.data = context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                 "$some\n$text"
            }
        }
        return fileModel
    }
}
