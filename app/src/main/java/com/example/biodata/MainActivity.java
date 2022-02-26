package com.example.biodata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String STATE_RESULT = "state_result";

    private LinearLayout linearLayout;
    private AnimationDrawable animationDrawable;
    private EditText edtNamaDepan, edtPhone, edtAlamat, edtDate, edtNamaBelakang;
    private Button btnSubmit;
    private TextView tvResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        edtNamaDepan = findViewById(R.id.edt_namaDepan);
        edtNamaBelakang = findViewById(R.id.edt_namaBelakang);
        edtDate = findViewById(R.id.edt_date);
        edtAlamat = findViewById(R.id.edt_alamat);
        edtPhone = findViewById(R.id.edt_phone);
        btnSubmit = findViewById(R.id.btn_submit);
        tvResult = findViewById(R.id.tv_result);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilTanggal();
            }
        });
        btnSubmit.setOnClickListener(this);

        if (savedInstanceState != null) {
            String result = savedInstanceState.getString(STATE_RESULT);
            tvResult.setText(result);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }

    public void TampilTanggal(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = "" + datePicker.getYear();
                String bulan = "" + (datePicker.getMonth() + 1);
                String hari = "" + datePicker.getDayOfMonth();
                String text =  hari + " - " + bulan + " - " + tahun;
                edtDate.setText(text);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_submit){
            String inputNamaDepan = edtNamaDepan.getText().toString().trim();
            String inputNamaBelakang = edtNamaBelakang.getText().toString().trim();
            String inputDate = edtDate.getText().toString().trim();
            String inputAlamat = edtAlamat.getText().toString().trim();
            String inputPhone = edtPhone.getText().toString().trim();

            boolean isEmptyFields = false;
            boolean isInvalidDouble = false;

            if (TextUtils.isEmpty(inputNamaDepan)){
                isEmptyFields = true;
                edtNamaDepan.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inputNamaBelakang)){
                isEmptyFields = true;
                edtNamaBelakang.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inputDate)){
                isEmptyFields = true;
                edtDate.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inputAlamat)){
                isEmptyFields = true;
                edtAlamat.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inputPhone)){
                isEmptyFields = true;
                edtPhone.setError("Field ini tidak boleh kosong");
            }

            Double phone = toDouble(inputPhone);

            if(phone == null){
                isInvalidDouble = true;
                edtPhone.setError("Field ini harus berupa nomor yang valid");
            }

            if (!isEmptyFields && !isInvalidDouble) {
                tvResult.setText("Nama Lengkap   : " + inputNamaDepan +" "+ inputNamaBelakang + "\n"
                        +"Tanggal Lahir  : " + inputDate+"\n"
                        +"Alamat         : " + inputAlamat+"\n"
                        +"No Phone       : " + inputPhone);


            }

        }

    }
    private Double toDouble(String str){
        try {
            return Double.valueOf(str);
        }catch (NumberFormatException e){
            return null;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}

