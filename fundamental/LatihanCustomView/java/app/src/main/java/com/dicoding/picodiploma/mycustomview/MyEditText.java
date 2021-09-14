package com.dicoding.picodiploma.mycustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

public class MyEditText extends AppCompatEditText implements View.OnTouchListener {

    private Drawable mClearButtonImage;

    public MyEditText(Context context) {
        super(context);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Menambahkan hint pada editText
        setHint("Masukkan nama Anda");

        // Menambahkan text aligmnet pada editText
        setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
    }

    private void init() {
        // Menginisialisasi gambar clear button
        mClearButtonImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_black_24dp);

        // Menambahkan aksi kepada clear button
        setOnTouchListener(this);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    showClearButton();
                } else {
                    hideClearButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    // Menampilkan clear button
    private void showClearButton() {
        // Sets the Drawables (if any) to appear to the left of,
        // above, to the right of, and below the text.
        setCompoundDrawablesWithIntrinsicBounds(
                null,               // Start of text.
                null,               // Top of text.
                mClearButtonImage,  // End of text.
                null);              // Below text.
    }

    // Menghilangkan clear button
    private void hideClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(
                null,             // Start of text.
                null,             // Top of text.
                null,             // End of text.
                null);            // Below text.
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if ((getCompoundDrawables()[2] != null)) {
            float clearButtonStart;
            float clearButtonEnd;
            boolean isClearButtonClicked = false;
            if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
                if (event.getX() < clearButtonEnd) {
                    isClearButtonClicked = true;
                }
            } else {
                clearButtonStart = (getWidth() - getPaddingEnd() - mClearButtonImage.getIntrinsicWidth());
                if (event.getX() > clearButtonStart) {
                    isClearButtonClicked = true;
                }
            }
            if (isClearButtonClicked) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mClearButtonImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_black_24dp);
                    showClearButton();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mClearButtonImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_black_24dp);
                    if (getText() != null) {
                        getText().clear();
                    }
                    hideClearButton();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
