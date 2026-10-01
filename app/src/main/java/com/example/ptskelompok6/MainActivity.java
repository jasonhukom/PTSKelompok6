package com.example.ptskelompok6;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Layar pertama (Project 1 - User Interface).
 * Menampilkan judul kelompok dan form input nama sebelum
 * berpindah ke layar interaksi (FormActivity).
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAMA = "extra_nama";

    EditText editTextNama;
    ProgressBar progressBar;
    Button buttonLanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNama = findViewById(R.id.editTextNama);
        progressBar = findViewById(R.id.progressBar);
        buttonLanjut = findViewById(R.id.buttonLanjut);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (progressBar != null) {
            progressBar.setProgress(0);
        }
        if (buttonLanjut != null) {
            buttonLanjut.setEnabled(true);
        }
    }

    /** Dipanggil saat tombol "Lanjut" ditekan. */
    public void lanjutkan(View view) {
        String inputNama = editTextNama.getText().toString().trim();
        final String nama = TextUtils.isEmpty(inputNama) ? "Kelompok Enam" : inputNama;

        buttonLanjut.setEnabled(false);
        progressBar.setProgress(0);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            int progress = 0;

            @Override
            public void run() {
                if (progress <= 100) {
                    progressBar.setProgress(progress);
                    progress++;
                    handler.postDelayed(this, 15);
                } else {
                    buttonLanjut.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Selamat datang, " + nama + "!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, FormActivity.class);
                    intent.putExtra(EXTRA_NAMA, nama);
                    startActivity(intent);
                }
            }
        });
    }
}
