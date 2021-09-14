package com.dicoding.picodiploma.mysharedpreference;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvName, tvAge, tvPhoneNo, tvEmail, tvIsLoveMU;
    private Button btnSave;
    private UserPreference mUserPreference;

    private boolean isPreferenceEmpty = false;
    private UserModel userModel;

    private final int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        tvPhoneNo = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        tvIsLoveMU = findViewById(R.id.tv_is_love_mu);
        btnSave = findViewById(R.id.btn_save);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My User Preference");
        }

        mUserPreference = new UserPreference(this);

        showExistingPreference();

        btnSave.setOnClickListener(this);

    }

    /*
    Menampilkan preference yang ada
     */
    private void showExistingPreference() {
        userModel = mUserPreference.getUser();
        populateView(userModel);
        checkForm(userModel);
    }

    /*
    Set tampilan menggunakan preferences
    */
    private void populateView(UserModel userModel) {
        tvName.setText(userModel.getName().isEmpty() ? "Tidak Ada" : userModel.getName());
        tvAge.setText(String.valueOf(userModel.getAge()).isEmpty() ? "Tidak Ada" : String.valueOf(userModel.getAge()));
        tvIsLoveMU.setText(userModel.isLove() ? "Ya" : "Tidak");
        tvEmail.setText(userModel.getEmail().isEmpty() ? "Tidak Ada" : userModel.getEmail());
        tvPhoneNo.setText(userModel.getPhoneNumber().isEmpty() ? "Tidak Ada" : userModel.getPhoneNumber());
    }

    private void checkForm(UserModel userModel) {
        if (!userModel.getName().isEmpty()) {
            btnSave.setText(getString(R.string.change));
            isPreferenceEmpty = false;
        } else {
            btnSave.setText(getString(R.string.save));
            isPreferenceEmpty = true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            Intent intent = new Intent(MainActivity.this, FormUserPreferenceActivity.class);
            if (isPreferenceEmpty) {
                intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_ADD);
            } else {
                intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_EDIT);
            }
            intent.putExtra("USER", userModel);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    /*
    Akan dipanggil ketika formuserpreferenceactivity ditutup
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == FormUserPreferenceActivity.RESULT_CODE) {
                userModel = data.getParcelableExtra(FormUserPreferenceActivity.EXTRA_RESULT);
                populateView(userModel);
                checkForm(userModel);
            }
        }
    }
}
