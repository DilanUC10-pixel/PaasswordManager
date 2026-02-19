package com.example.paasswordmanager.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.presenters.ConfigContract;
import com.example.paasswordmanager.presenters.ConfigPresenter;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ConfigActivity extends AppCompatActivity implements ConfigContract.View {

    private SwitchMaterial switchDarkMode;
    private SwitchMaterial switchBiometrics;
    private Button btnLogout;
    private ImageButton btnBack;
    private ConfigContract.Presenter presenter;

    private final ActivityResultLauncher<String> exportLauncher = registerForActivityResult(
            new ActivityResultContracts.CreateDocument("application/json"),
            uri -> {
                if (uri != null) {
                    presenter.onExportResult(uri);
                }
            }
    );

    private final ActivityResultLauncher<String[]> importLauncher = registerForActivityResult(
            new ActivityResultContracts.OpenDocument(),
            uri -> {
                if (uri != null) {
                    presenter.onImportResult(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchBiometrics = findViewById(R.id.switchBiometrics);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);

        presenter = new ConfigPresenter(this, this);

        btnBack.setOnClickListener(v -> finish());
        switchDarkMode.setOnClickListener(v -> presenter.onDarkModeChanged(switchDarkMode.isChecked()));
        switchBiometrics.setOnClickListener(v -> presenter.onBiometricsChanged(switchBiometrics.isChecked()));
        btnLogout.setOnClickListener(v -> presenter.onLogoutClicked());
        findViewById(R.id.cardExport).setOnClickListener(v -> presenter.onExportClicked());
        findViewById(R.id.cardImport).setOnClickListener(v -> presenter.onImportClicked());

        presenter.onViewCreated();
    }

    @Override
    public void setDarkModeSwitch(boolean isChecked) {
        switchDarkMode.setChecked(isChecked);
    }

    @Override
    public void setBiometricsSwitch(boolean isChecked) {
        switchBiometrics.setChecked(isChecked);
    }

    @Override
    public void showPasswordConfirmationDialog(int actionType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.prompt_password_title);

        final EditText input = new EditText(this);
        input.setHint("Contraseña maestra");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setPadding(50, 40, 50, 40);
        builder.setView(input);

        builder.setPositiveButton(R.string.btn_confirm, (dialog, which) -> {
            presenter.onPasswordConfirmed(input.getText().toString(), actionType);
        });
        builder.setNegativeButton(R.string.btn_cancel, (dialog, which) -> {
            // Revert switch if it was a switch toggle
            if (actionType == 0) setBiometricsSwitch(false);
            if (actionType == 3) setBiometricsSwitch(true);
            dialog.cancel();
        });

        builder.show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void applyTheme(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void launchExport(String fileName) {
        exportLauncher.launch(fileName);
    }

    @Override
    public void launchImport() {
        importLauncher.launch(new String[]{"*/*"});
    }

    @Override
    public void navigateToWelcome() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
