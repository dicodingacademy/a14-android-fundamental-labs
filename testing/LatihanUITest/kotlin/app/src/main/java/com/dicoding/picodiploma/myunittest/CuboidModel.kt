package com.dicoding.picodiploma.myunittest

// Cuboid == Balok
class CuboidModel {
    private var width = 0.0
    private var length = 0.0
    private var height = 0.0

    fun getVolume() = width * length * height

    fun getSurfaceArea(): Double{
        val wl = width * length
        val wh = width * height
        val lh = length * height

        return 2 * (wl + wh + lh)
    }

    fun getCircumference() = 4 * (width + length + height)

    fun save(width: Double, length: Double, height: Double) {
        this.width = width
        this.length = length
        this.height = height
    }
}