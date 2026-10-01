package com.example.ptskelompok6;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAMA = "extra_nama";

    TextView textJudul;
    TextView textSubJudul;
    EditText editTextNama;
    ProgressBar progressBar;
    Button buttonLanjut;
    Button buttonThemeToggle;
    Spinner spinnerFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textJudul = findViewById(R.id.textJudul);
        textSubJudul = findViewById(R.id.textSubJudul);
        editTextNama = findViewById(R.id.editTextNama);
        progressBar = findViewById(R.id.progressBar);
        buttonLanjut = findViewById(R.id.buttonLanjut);
        buttonThemeToggle = findViewById(R.id.buttonThemeToggle);
        spinnerFont = findViewById(R.id.spinnerFont);

        ThemeHelper.updateToggleIcon(buttonThemeToggle, this);

        if (buttonThemeToggle != null) {
            buttonThemeToggle.setOnClickListener(v -> ThemeHelper.toggleTheme(MainActivity.this));
        }

        setupFontSpinner();
    }

    private void setupFontSpinner() {
        if (spinnerFont == null) return;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.font_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFont.setAdapter(adapter);

        spinnerFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String fontName = parent.getItemAtPosition(position).toString();
                changeFont(fontName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void changeFont(String fontName) {
        Typeface typeface;
        switch (fontName) {
            case "Monospace":
                typeface = Typeface.MONOSPACE;
                break;
            case "Serif":
                typeface = Typeface.SERIF;
                break;
            case "Cursive":
                typeface = Typeface.create("cursive", Typeface.NORMAL);
                break;
            case "Sans-Serif":
            default:
                typeface = Typeface.SANS_SERIF;
                break;
        }

        if (textJudul != null) textJudul.setTypeface(typeface, Typeface.BOLD);
        if (textSubJudul != null) textSubJudul.setTypeface(typeface);
        if (editTextNama != null) editTextNama.setTypeface(typeface);
        if (buttonLanjut != null) buttonLanjut.setTypeface(typeface);
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
        ThemeHelper.updateToggleIcon(buttonThemeToggle, this);
    }

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
