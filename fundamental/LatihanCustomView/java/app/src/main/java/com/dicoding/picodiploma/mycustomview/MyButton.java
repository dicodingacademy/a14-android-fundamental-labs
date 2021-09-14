package com.dicoding.picodiploma.mycustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import android.util.AttributeSet;
import android.view.Gravity;

public class MyButton extends androidx.appcompat.widget.AppCompatButton {

    private Drawable enabledBackground, disabledBackground;

    private int textColor;

    // Konstruktor dari MyButton
    public MyButton(Context context) {
        super(context);
        init();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // Metode onDraw() digunakan untuk mengcustom button ketika enable dan disable
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Mengubah background dari Button
        setBackground(isEnabled() ? enabledBackground : disabledBackground);

        // Mengubah warna text pada button
        setTextColor(textColor);

        // Mengubah ukuran text pada button
        setTextSize(12.f);

        // Menjadikan object pada button menjadi center
        setGravity(Gravity.CENTER);

        // Mengubah text pada button pada kondisi enable dan disable
        setText(isEnabled() ? "Submit" : "Isi Dulu");
    }

    // pemanggilan Resource harus dilakukan saat kelas MyButton diinisialisasi, jadi harus dikeluarkan dari metode onDraw
    private void init() {
        textColor = ContextCompat.getColor(getContext(), android.R.color.background_light);
        enabledBackground = ContextCompat.getDrawable(getContext(), R.drawable.bg_button);
        disabledBackground = ContextCompat.getDrawable(getContext(), R.drawable.bg_button_disable);
    }
}
