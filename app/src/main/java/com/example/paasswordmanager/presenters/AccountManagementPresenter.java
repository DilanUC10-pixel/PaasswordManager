package com.example.paasswordmanager.presenters;

import android.content.Context;
import com.example.paasswordmanager.managers.LoginSingleton;
import com.example.paasswordmanager.models.Account;

public class AccountManagementPresenter implements AccountManagementContract.Presenter {

    private AccountManagementContract.View view;
    private Context context;

    public AccountManagementPresenter(AccountManagementContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void loadAccount(int index) {
        if (index >= 0) {
            Account account = LoginSingleton.getInstance().getAccountList().get(index);
            view.showAccountData(account);
        }
    }

    @Override
    public void saveAccount(int index, String category, String usage, String siteName, String email, String password, String date,
                            String username, String phone, String secretQuestion, String passwordHint, String altEmail, String oauth, String notes) {
        
        boolean hasError = false;

        if (siteName.isEmpty()) {
            view.showFieldError("siteName");
            hasError = true;
        }
        if (email.isEmpty()) {
            view.showFieldError("email");
            hasError = true;
        }
        if (password.isEmpty()) {
            view.showFieldError("password");
            hasError = true;
        }
        if (date.isEmpty()) {
            view.showFieldError("date");
            hasError = true;
        }

        if (hasError) {
            view.showEmptyFieldsError();
            return;
        }

        Account account = new Account(siteName, email, password, date);
        account.setCategory(category);
        account.setUsage(usage);
        account.setUsername(username);
        account.setPhone(phone);
        account.setSecretQuestion(secretQuestion);
        account.setPasswordHint(passwordHint);
        account.setAltEmail(altEmail);
        account.setExternalAccess(oauth);
        account.setNotes(notes);

        if (index == -1) {
            LoginSingleton.getInstance().addAccount(context, account);
            view.showSuccessMessage("Cuenta guardada exitosamente");
        } else {
            LoginSingleton.getInstance().updateAccount(context, index, account);
            view.showSuccessMessage("Cuenta actualizada exitosamente");
        }
        view.closeView();
    }
}
