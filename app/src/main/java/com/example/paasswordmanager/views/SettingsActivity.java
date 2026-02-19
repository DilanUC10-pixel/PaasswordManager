package com.example.paasswordmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.presenters.RegisterContract;
import com.example.paasswordmanager.presenters.RegisterPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SettingsActivity extends AppCompatActivity implements RegisterContract.View {

    private TextInputEditText etName, etEmail, etPhone, etPassword, etConfirmPassword;
    private TextInputLayout tilName, tilEmail, tilPhone, tilPassword, tilConfirmPassword;
    private Button btnSave;
    private RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        
        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPhone = findViewById(R.id.tilPhone);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        
        btnSave = findViewById(R.id.btnSave);

        presenter = new RegisterPresenter(this, this);

        btnSave.setOnClickListener(v -> {
            presenter.onRegisterClicked(
                etName.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                etPhone.getText().toString().trim(),
                etPassword.getText().toString().trim(),
                etConfirmPassword.getText().toString().trim()
            );
        });
    }

    @Override
    public void showNameError(String message) {
        tilName.setError(message);
    }

    @Override
    public void showEmailError(String message) {
        tilEmail.setError(message);
    }

    @Override
    public void showPhoneError(String message) {
        tilPhone.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        tilPassword.setError(message);
    }

    @Override
    public void showConfirmPasswordError(String message) {
        tilConfirmPassword.setError(message);
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToBiometricSetup() {
        Intent intent = new Intent(this, BiometricSetupActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
