package com.example.paasswordmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.managers.LoginSingleton;
import com.example.paasswordmanager.presenters.LoginContract;
import com.example.paasswordmanager.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ImageButton btnBack;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);

        presenter = new LoginPresenter(this);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            presenter.onLoginClicked(email, password);
        });

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showEmptyFieldsError() {
        Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidCredentialsError() {
        Toast.makeText(this, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMainMenu() {
        String targetActivityName = getIntent().getStringExtra("TARGET_ACTIVITY");
        
        if (targetActivityName != null) {
            try {
                Class<?> targetClass = Class.forName(targetActivityName);
                Intent intent = new Intent(this, targetClass);
                startActivity(intent);
                finish();
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Default behavior
        if (!LoginSingleton.getInstance().isSetupCompleted()) {
            startActivity(new Intent(this, BiometricSetupActivity.class));
        } else {
            startActivity(new Intent(this, MainMenuActivity.class));
        }
        finish();
    }
}
