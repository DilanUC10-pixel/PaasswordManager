package com.example.paasswordmanager.presenters;

import android.net.Uri;

public interface ConfigContract {

    interface View {
        void setDarkModeSwitch(boolean isChecked);
        void setBiometricsSwitch(boolean isChecked);
        void showPasswordConfirmationDialog(int actionType);
        void showToast(String message);
        void applyTheme(boolean isDarkMode);
        void launchExport(String fileName);
        void launchImport();
        void navigateToWelcome();
    }

    interface Presenter {
        void onViewCreated();
        void onDarkModeChanged(boolean isChecked);
        void onBiometricsChanged(boolean isChecked);
        void onLogoutClicked();
        void onExportClicked();
        void onImportClicked();
        void onPasswordConfirmed(String password, int actionType);
        void onExportResult(Uri uri);
        void onImportResult(Uri uri);
        void onBackClicked();
    }
}
