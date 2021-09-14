package com.dicoding.picodiploma.mybroadcastreceiver;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

/**
 * Created by dicoding on 10/13/2017.
 */

/**
 * Kelas ini digunakan untuk grant permission secara run-time
 * grant permission secara run-time diharuskan untuk permission yang termasuk dalam kategori dangerous
 */
class PermissionManager {
    public static void check(Activity activity, String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }
}
