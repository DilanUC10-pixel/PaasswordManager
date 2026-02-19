package com.example.paasswordmanager.presenters;

public interface BiometricSetupContract {
    interface View {
        void navigateToMainMenu();
    }

    interface Presenter {
        void onUseBiometricsClicked(boolean useBiometrics);
    }
}
