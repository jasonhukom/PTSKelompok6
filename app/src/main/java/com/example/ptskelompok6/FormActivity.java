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

    DatabaseHelper databaseHelper;
    String currentUserNama;

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

        databaseHelper = new DatabaseHelper(this);

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

        currentUserNama = getIntent().getStringExtra(MainActivity.EXTRA_NAMA);
        if (TextUtils.isEmpty(currentUserNama)) {
            currentUserNama = "Kelompok Enam";
        }
        textGreeting.setText("Halo, " + currentUserNama + "! \nSilahkan lengkapi data kontak Anda.");

        loadHistoryFromDatabase();
        FontHelper.applyFontToActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeHelper.updateToggleIcon(buttonThemeToggle, this);
        loadHistoryFromDatabase();
        FontHelper.applyFontToActivity(this);
    }

    private void loadHistoryFromDatabase() {
        if (databaseHelper == null) return;

        String historyData = databaseHelper.getAllHistoryString();
        int totalCount = databaseHelper.getHistoryCount();

        if (TextUtils.isEmpty(historyData)) {
            textResult.setText(R.string.hasil_kosong);
        } else {
            textResult.setText(historyData);
        }

        textCounter.setText("Jumlah data terkirim: " + totalCount);

        if (scrollViewResult != null) {
            scrollViewResult.post(() -> scrollViewResult.fullScroll(View.FOCUS_DOWN));
        }
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

        String selectedFont = FontHelper.getFont(this);

        databaseHelper.insertHistory(currentUserNama, pilih, phone, selectedFont);

        editTextPhone.setText("");
        radioGroup.clearCheck();

        loadHistoryFromDatabase();

        Toast.makeText(this, pilih + ": " + phone, Toast.LENGTH_SHORT).show();
    }
}
