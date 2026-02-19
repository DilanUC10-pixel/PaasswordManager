package com.example.paasswordmanager.presenters;

import com.example.paasswordmanager.managers.LoginSingleton;

public class MainMenuPresenter implements MainMenuContract.Presenter {

    private final MainMenuContract.View view;
    private Class<?> pendingActivity;

    public MainMenuPresenter(MainMenuContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewCreated() {
    }

    @Override
    public void onMenuItemClicked(Class<?> targetActivity, boolean requiresRoot) {
        this.pendingActivity = targetActivity;
        
        if (requiresRoot) {
            if (LoginSingleton.getInstance().isRoot()) {
                // Si ya es Root, entra directo (ya se logueó previamente)
                view.navigateTo(targetActivity);
            } else {
                // Si no es Root, forzamos el inicio de sesión pasando la actividad pendiente
                view.navigateToLogin(targetActivity);
            }
        } else {
            // Acceso a registros: Forzamos SIEMPRE el patrón/biometría del celular
            view.showBiometricPrompt();
        }
    }

    @Override
    public void onSettingsClicked() {
    }

    @Override
    public void onBiometricSuccess() {
        view.navigateTo(pendingActivity);
    }

    @Override
    public void onPasswordConfirmed(String password) {
        if (LoginSingleton.getInstance().isValidRoot("", password)) {
            view.navigateTo(pendingActivity);
        } else {
            view.showToast("Credenciales incorrectas");
        }
    }
}
