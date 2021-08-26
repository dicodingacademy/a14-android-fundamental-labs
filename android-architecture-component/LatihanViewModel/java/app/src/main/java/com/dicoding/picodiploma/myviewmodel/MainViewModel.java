package com.dicoding.picodiploma.myviewmodel;

import androidx.lifecycle.ViewModel;

// Meng-extend kelas ViewModel ke MainViewModel
public class MainViewModel extends ViewModel {

    // Membuat variable penampung, untuk dipertahankan datanya
    int result = 0;

    // Metode calculate untuk menghitung volume
    void calculate(String width, String height, String length) {

        // Mengubah hasil result dari hasil perkalian lebar, tinggi dan panjang.
        result = Integer.parseInt(width) * Integer.parseInt(height) * Integer.parseInt(length);
    }
}
