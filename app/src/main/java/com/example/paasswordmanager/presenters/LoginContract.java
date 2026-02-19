package com.example.paasswordmanager.presenters;

public interface LoginContract {
    interface View {
        void showEmptyFieldsError();
        void showInvalidCredentialsError();
        void navigateToMainMenu();
    }

    interface Presenter {
        void onLoginClicked(String email, String password);
    }
}
