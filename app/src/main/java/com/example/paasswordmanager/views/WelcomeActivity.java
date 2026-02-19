package com.example.paasswordmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.presenters.WelcomeContract;
import com.example.paasswordmanager.presenters.WelcomePresenter;

public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.View {

    private WelcomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        presenter = new WelcomePresenter(this);

        Button btnGetStarted = findViewById(R.id.btnGetStarted);
        btnGetStarted.setOnClickListener(v -> presenter.onGetStartedClicked());
    }

    @Override
    public void navigateToRegistration() {
        startActivity(new Intent(this, SettingsActivity.class));
        finish();
    }

    @Override
    public void navigateToMainMenu() {
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }
}
