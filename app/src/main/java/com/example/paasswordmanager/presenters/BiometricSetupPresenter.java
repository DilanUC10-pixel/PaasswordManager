package com.example.paasswordmanager.presenters;

import android.content.Context;
import com.example.paasswordmanager.managers.LoginSingleton;

public class BiometricSetupPresenter implements BiometricSetupContract.Presenter {

    private final BiometricSetupContract.View view;
    private final Context context;

    public BiometricSetupPresenter(BiometricSetupContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onUseBiometricsClicked(boolean useBiometrics) {
        LoginSingleton.getInstance().setUseBiometrics(context, useBiometrics);
        LoginSingleton.getInstance().setSetupCompleted(context, true);
        view.navigateToMainMenu();
    }
}
