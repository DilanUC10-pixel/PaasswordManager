package com.example.paasswordmanager.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.example.paasswordmanager.managers.LoginSingleton;
import com.example.paasswordmanager.models.Account;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

public class ConfigPresenter implements ConfigContract.Presenter {

    private final ConfigContract.View view;
    private final Context context;
    private static final String PREFS_NAME = "AppConfig";
    private static final String KEY_DARK_MODE = "dark_mode";

    public ConfigPresenter(ConfigContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onViewCreated() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        view.setDarkModeSwitch(prefs.getBoolean(KEY_DARK_MODE, false));
        view.setBiometricsSwitch(LoginSingleton.getInstance().isUseBiometrics());
    }

    @Override
    public void onDarkModeChanged(boolean isChecked) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
        view.applyTheme(isChecked);
    }

    @Override
    public void onBiometricsChanged(boolean isChecked) {
        // Requires password to change security settings
        view.showPasswordConfirmationDialog(isChecked ? 0 : 3);
    }

    @Override
    public void onLogoutClicked() {
        LoginSingleton.getInstance().logout(context);
        view.navigateToWelcome();
    }

    @Override
    public void onExportClicked() {
        view.showPasswordConfirmationDialog(1);
    }

    @Override
    public void onImportClicked() {
        view.showPasswordConfirmationDialog(2);
    }

    @Override
    public void onPasswordConfirmed(String password, int actionType) {
        if (LoginSingleton.getInstance().isValidRoot("", password)) {
            executeAction(actionType);
        } else {
            view.showToast("Contraseña incorrecta");
            // Revert switches if needed
            if (actionType == 0) view.setBiometricsSwitch(false);
            if (actionType == 3) view.setBiometricsSwitch(true);
        }
    }

    private void executeAction(int actionType) {
        switch (actionType) {
            case 0:
                LoginSingleton.getInstance().setUseBiometrics(context, true);
                view.setBiometricsSwitch(true);
                view.showToast("Biometría activada");
                break;
            case 3:
                LoginSingleton.getInstance().setUseBiometrics(context, false);
                view.setBiometricsSwitch(false);
                view.showToast("Biometría desactivada");
                break;
            case 1:
                view.launchExport("backup.json");
                break;
            case 2:
                view.launchImport();
                break;
        }
    }

    @Override
    public void onExportResult(Uri uri) {
        try (OutputStream os = context.getContentResolver().openOutputStream(uri)) {
            String json = new Gson().toJson(LoginSingleton.getInstance().getAccountList());
            os.write(json.getBytes());
            view.showToast("Exportación exitosa");
        } catch (Exception e) {
            view.showToast("Error al exportar");
        }
    }

    @Override
    public void onImportResult(Uri uri) {
        try (InputStream is = context.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            ArrayList<Account> imported = new Gson().fromJson(sb.toString(), new TypeToken<ArrayList<Account>>(){}.getType());
            if (imported != null) {
                for (Account acc : imported) LoginSingleton.getInstance().addAccount(context, acc);
                view.showToast("Importación exitosa");
            }
        } catch (Exception e) {
            view.showToast("Error al importar");
        }
    }

    @Override
    public void onBackClicked() {
        // Navigation logic can be here too
    }
}
