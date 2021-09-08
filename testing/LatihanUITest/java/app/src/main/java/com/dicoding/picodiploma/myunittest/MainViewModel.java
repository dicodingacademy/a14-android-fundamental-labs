package com.dicoding.picodiploma.myunittest;

class MainViewModel {
    private final CuboidModel cuboidModel;

    MainViewModel(CuboidModel cuboidModel) {
        this.cuboidModel = cuboidModel;
    }

    void save(double l, double w, double h) {
        cuboidModel.save(l, w, h);
    }

    public double getCircumference() {
        return cuboidModel.getCircumference();
    }

    public double getSurfaceArea() {
        return cuboidModel.getSurfaceArea();
    }

    public double getVolume() {
        return cuboidModel.getVolume();
    }
}
