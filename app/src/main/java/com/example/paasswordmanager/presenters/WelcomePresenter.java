package com.example.paasswordmanager.presenters;

import com.example.paasswordmanager.managers.LoginSingleton;

public class WelcomePresenter implements WelcomeContract.Presenter {

    private final WelcomeContract.View view;

    public WelcomePresenter(WelcomeContract.View view) {
        this.view = view;
    }

    @Override
    public void onGetStartedClicked() {
        if (LoginSingleton.getInstance().isEmpty()) {
            view.navigateToRegistration();
        } else {
            view.navigateToMainMenu();
        }
    }
}
