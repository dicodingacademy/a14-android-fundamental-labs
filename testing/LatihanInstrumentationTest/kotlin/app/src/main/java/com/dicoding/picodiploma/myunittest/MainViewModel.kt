package com.dicoding.picodiploma.myunittest

internal class MainViewModel(private val cuboidModel: CuboidModel) {

    val circumference: Double
        get() = cuboidModel.circumference

    val surfaceArea: Double
        get() = cuboidModel.surfaceArea

    val volume: Double
        get() = cuboidModel.volume

    fun save(l: Double, w: Double, h: Double) {
        cuboidModel.save(l, w, h)
    }
}
