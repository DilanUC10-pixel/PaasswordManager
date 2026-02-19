package com.example.paasswordmanager.presenters;

import com.example.paasswordmanager.managers.LoginSingleton;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void onLoginClicked(String email, String password) {
        if (password.isEmpty()) {
            view.showEmptyFieldsError();
            return;
        }

        if (LoginSingleton.getInstance().isValidRoot(email, password)) {
            view.navigateToMainMenu();
        } else {
            view.showInvalidCredentialsError();
        }
    }
}
