package com.dicoding.picodiploma.myreadwritefile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by dicoding on 11/23/2016.
 */

class FileHelper {

    private static final String TAG = FileHelper.class.getName();

    /**
     * Method yang digunakan untuk menuliskan data berupa string menjadi file
     *
     * @param fileModel get data file model
     * @param context   context aplikasi
     */
    static void writeToFile(FileModel fileModel, Context context) {
        try (FileOutputStream fos = context.openFileOutput(fileModel.getFilename(), Context.MODE_PRIVATE)) {
            fos.write(fileModel.getData().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Writing file failed :", e);
        }
    }

    /**
     * Method yang digunakan untuk membaca data dari file
     *
     * @param context  context aplikasi
     * @param filename nama file
     * @return data berupa string
     */
    static FileModel readFromFile(Context context, String filename) {
        FileModel fileModel = new FileModel();
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            fileModel.setFilename(filename);
            fileModel.setData(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found :", e);
        } catch (IOException e) {
            Log.e(TAG, "Can not read file :", e);
        }
        return fileModel;
    }
}
