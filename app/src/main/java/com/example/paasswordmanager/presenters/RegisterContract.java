package com.example.paasswordmanager.presenters;

public interface RegisterContract {
    interface View {
        void showNameError(String message);
        void showEmailError(String message);
        void showPhoneError(String message);
        void showPasswordError(String message);
        void showConfirmPasswordError(String message);
        void showSuccess(String message);
        void navigateToBiometricSetup();
        void navigateToLogin();
    }

    interface Presenter {
        void onRegisterClicked(String name, String email, String phone, String password, String confirmPassword);
    }
}
