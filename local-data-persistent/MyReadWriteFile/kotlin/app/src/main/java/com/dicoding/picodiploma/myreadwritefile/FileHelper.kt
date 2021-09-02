package com.dicoding.picodiploma.myreadwritefile

import android.content.Context
import android.util.Log
import java.io.*

/**
 * Created by dicoding on 11/23/2016.
 */

internal object FileHelper {

    private val TAG = FileHelper::class.java.name

    /**
     * Method yang digunakan untuk menuliskan data berupa string menjadi file
     *
     * @param fileModel get data file model
     * @param context  context aplikasi
     */
    fun writeToFile(fileModel: FileModel, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(fileModel.filename.toString(), Context.MODE_PRIVATE))
            outputStreamWriter.write(fileModel.data.toString())
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e(TAG, "File write failed :", e)
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

        try {
            val inputStream = context.openFileInput(filename)

            if (inputStream != null) {
                val receiveString = inputStream.bufferedReader().use(BufferedReader::readText)
                inputStream.close()
                fileModel.data = receiveString
                fileModel.filename = filename
            }
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "File not found :", e)
        } catch (e: IOException) {
            Log.e(TAG, "Can not read file :", e)
        }

        return fileModel
    }
}
