package com.example.paasswordmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.presenters.MainMenuContract;
import com.example.paasswordmanager.presenters.MainMenuPresenter;
import java.util.concurrent.Executor;

public class MainMenuActivity extends AppCompatActivity implements MainMenuContract.View {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private MainMenuContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        executor = ContextCompat.getMainExecutor(this);
        presenter = new MainMenuPresenter(this);
        setupBiometricPrompt();

        LinearLayout btnViewRecords = findViewById(R.id.btnViewRecords);
        LinearLayout btnManageAccounts = findViewById(R.id.btnManageAccounts);
        ImageButton btnSettings = findViewById(R.id.btnSettings);
        ImageButton btnBack = findViewById(R.id.btnBack);

        btnViewRecords.setOnClickListener(v -> presenter.onMenuItemClicked(ViewRecordsActivity.class, false));
        btnManageAccounts.setOnClickListener(v -> presenter.onMenuItemClicked(EditAccountActivity.class, true));
        
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, ConfigActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> onBackPressed());
        
        presenter.onViewCreated();
    }

    private void setupBiometricPrompt() {
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                presenter.onBiometricSuccess();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                showToast("Autenticación fallida");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_prompt_title))
                .setSubtitle("Usa tu huella o el método de desbloqueo de tu celular")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | 
                                        BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();
    }

    @Override
    public void navigateTo(Class<?> targetActivity) {
        if (targetActivity != null) {
            startActivity(new Intent(this, targetActivity));
        }
    }

    @Override
    public void navigateToLogin(Class<?> targetActivity) {
        Intent intent = new Intent(this, LoginActivity.class);
        if (targetActivity != null) {
            intent.putExtra("TARGET_ACTIVITY", targetActivity.getName());
        }
        startActivity(intent);
    }

    public void navigateToLogin() {
        navigateToLogin(null);
    }

    @Override
    public void showBiometricPrompt() {
        biometricPrompt.authenticate(promptInfo);
    }

    @Override
    public void showPasswordConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.prompt_password_title);

        final EditText input = new EditText(this);
        input.setHint("Contraseña maestra de la app");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setPadding(50, 40, 50, 40);
        builder.setView(input);

        builder.setPositiveButton(R.string.btn_confirm, (dialog, which) -> {
            presenter.onPasswordConfirmed(input.getText().toString());
        });
        builder.setNegativeButton(R.string.btn_cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
