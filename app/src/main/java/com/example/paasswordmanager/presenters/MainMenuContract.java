package com.example.paasswordmanager.presenters;

public interface MainMenuContract {
    interface View {
        void navigateTo(Class<?> targetActivity);
        void navigateToLogin(Class<?> targetActivity);
        void showBiometricPrompt();
        void showPasswordConfirmationDialog();
        void showToast(String message);
    }

    interface Presenter {
        void onViewCreated();
        void onMenuItemClicked(Class<?> targetActivity, boolean requiresRoot);
        void onSettingsClicked();
        void onBiometricSuccess();
        void onPasswordConfirmed(String password);
    }
}
