package com.dicoding.picodiploma.myviewmodel

import androidx.lifecycle.ViewModel

// Meng-extend kelas ViewModel ke MainViewModel
class MainViewModel : ViewModel() {
    // Membuat variable penampung, untuk dipertahankan datanya
    var result = 0

    // Metode calculate untuk menghitung volume
    fun calculate(width: String, height: String, length: String) {
        // Mengubah hasil result dari hasil perkalian lebar, tinggi dan panjang.
        result = width.toInt() * height.toInt() * length.toInt()
    }
}