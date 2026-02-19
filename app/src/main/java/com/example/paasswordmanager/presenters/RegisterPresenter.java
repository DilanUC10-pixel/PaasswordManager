package com.example.paasswordmanager.presenters;

import android.content.Context;
import android.util.Patterns;
import com.example.paasswordmanager.managers.LoginSingleton;
import com.example.paasswordmanager.models.Users;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private Context context;

    public RegisterPresenter(RegisterContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onRegisterClicked(String name, String email, String phone, String password, String confirmPassword) {
        boolean isValid = true;

        if (name.isEmpty()) {
            view.showNameError("El nombre es obligatorio");
            isValid = false;
        }

        if (email.isEmpty()) {
            view.showEmailError("El correo es obligatorio");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Formato de correo no válido");
            isValid = false;
        }

        if (phone.isEmpty()) {
            view.showPhoneError("El teléfono es obligatorio");
            isValid = false;
        }

        if (password.isEmpty()) {
            view.showPasswordError("La contraseña es obligatoria");
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            view.showConfirmPasswordError("Debe confirmar la contraseña");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            view.showConfirmPasswordError("Las contraseñas no coinciden");
            isValid = false;
        }

        if (!isValid) return;

        // Create the user
        Users newUser = new Users(name, email, phone, password, "Root");
        LoginSingleton.getInstance().addUser(context, newUser);
        
        // Auto-login: Set as current user immediately
        LoginSingleton.getInstance().setCurrentUser(newUser);
        LoginSingleton.getInstance().saveData(context);

        view.showSuccess("¡Registro exitoso!");
        view.navigateToBiometricSetup();
    }
}
