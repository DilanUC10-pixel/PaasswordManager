package com.example.paasswordmanager.presenters;

public interface WelcomeContract {
    interface View {
        void navigateToRegistration();
        void navigateToMainMenu();
    }

    interface Presenter {
        void onGetStartedClicked();
    }
}
