package com.example.ptskelompok6;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormActivity extends AppCompatActivity {

    EditText editTextPhone;
    RadioGroup radioGroup;
    RadioButton radioRumah;
    RadioButton radioMobile;
    RadioButton radioKantor;
    TextView textGreeting;
    TextView textResult;
    TextView textCounter;
    ScrollView scrollViewResult;
    Button buttonThemeToggle;

    int jumlahTerkirim = 0;
    StringBuilder listNomor = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextPhone = findViewById(R.id.editTextPhone);
        radioGroup = findViewById(R.id.radioGroup);
        radioRumah = findViewById(R.id.radioRumah);
        radioMobile = findViewById(R.id.radioMobile);
        radioKantor = findViewById(R.id.radioKantor);
        textGreeting = findViewById(R.id.textGreeting);
        textResult = findViewById(R.id.textResult);
        textCounter = findViewById(R.id.textCounter);
        scrollViewResult = findViewById(R.id.scrollViewResult);
        buttonThemeToggle = findViewById(R.id.buttonThemeToggle);

        ThemeHelper.updateToggleIcon(buttonThemeToggle, this);

        if (buttonThemeToggle != null) {
            buttonThemeToggle.setOnClickListener(v -> ThemeHelper.toggleTheme(FormActivity.this));
        }

        String nama = getIntent().getStringExtra(MainActivity.EXTRA_NAMA);
        if (TextUtils.isEmpty(nama)) {
            nama = "Kelompok Enam";
        }
        textGreeting.setText("Halo, " + nama + "! Silahkan lengkapi data kontak Anda.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeHelper.updateToggleIcon(buttonThemeToggle, this);
    }

    public void showText(View view) {
        String phone = editTextPhone.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Nomor telepon wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        String pilih;
        if (radioRumah.isChecked()) {
            pilih = "Telp Rumah";
        } else if (radioMobile.isChecked()) {
            pilih = "Mobile";
        } else if (radioKantor.isChecked()) {
            pilih = "Telp Kantor";
        } else {
            pilih = "Belum memilih jenis telepon";
        }

        jumlahTerkirim++;

        if (listNomor.length() > 0) {
            listNomor.append("\n\n");
        }
        listNomor.append("Data ke-").append(jumlahTerkirim).append(":\n")
                 .append(pilih).append(":").append(phone);

        textResult.setText(listNomor.toString());
        textCounter.setText("Jumlah data terkirim: " + jumlahTerkirim);

        editTextPhone.setText("");
        radioGroup.clearCheck();

        if (scrollViewResult != null) {
            scrollViewResult.post(() -> scrollViewResult.fullScroll(View.FOCUS_DOWN));
        }

        Toast.makeText(this, pilih + ": " + phone, Toast.LENGTH_SHORT).show();
    }
}
