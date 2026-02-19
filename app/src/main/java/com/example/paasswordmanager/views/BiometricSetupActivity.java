package com.example.paasswordmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.presenters.BiometricSetupContract;
import com.example.paasswordmanager.presenters.BiometricSetupPresenter;

public class BiometricSetupActivity extends AppCompatActivity implements BiometricSetupContract.View {

    private BiometricSetupContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_setup);

        presenter = new BiometricSetupPresenter(this, this);

        Button btnUseBiometric = findViewById(R.id.btnUseBiometric);
        Button btnSkipBiometric = findViewById(R.id.btnSkipBiometric);
        Button btnCancel = findViewById(R.id.btnCancelSetup);

        btnUseBiometric.setOnClickListener(v -> presenter.onUseBiometricsClicked(true));
        btnSkipBiometric.setOnClickListener(v -> presenter.onUseBiometricsClicked(false));
        btnCancel.setOnClickListener(v -> navigateToMainMenu());
    }

    @Override
    public void navigateToMainMenu() {
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }
}
