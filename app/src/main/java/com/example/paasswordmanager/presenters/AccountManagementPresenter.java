package com.example.paasswordmanager.presenters;

import android.app.Application;
import android.content.Context;
import com.example.paasswordmanager.managers.LoginSingleton;
import com.example.paasswordmanager.models.Account;
import com.example.paasswordmanager.models.AccountsRepository;
import com.example.paasswordmanager.models.AccountsRoomDB;
import com.example.paasswordmanager.models.Users;

public class AccountManagementPresenter implements AccountManagementContract.Presenter {

    private AccountManagementContract.View view;
    private AccountsRepository repository;

    public AccountManagementPresenter(AccountManagementContract.View view, Context context) {
        this.view = view;
        this.repository = new AccountsRepository((Application) context.getApplicationContext());
    }

    @Override
    public void loadAccountById(int id) {
        if (id > 0) {
            // Room access must be on background thread
            AccountsRoomDB.databaseWriteExecutor.execute(() -> {
                Account account = AccountsRoomDB.getDatabase(null).accountDao().getAccountByIdSync(id);
                if (account != null) {
                    Users user = LoginSingleton.getInstance().getCurrentUser();
                    if (user != null) {
                        String decryptedPass = account.getDecryptedPassword(user.getPassword());
                        // Create display copy
                        Account displayAccount = new Account(account.getSiteName(), account.getEmail(), decryptedPass, account.getRegisterDate());
                        displayAccount.setId(account.getId());
                        displayAccount.setCategory(account.getCategory());
                        displayAccount.setUsage(account.getUsage());
                        displayAccount.setUsername(account.getUsername());
                        displayAccount.setPhone(account.getPhone());
                        displayAccount.setSecretQuestion(account.getDecryptedSecretQuestion(user.getPassword()));
                        displayAccount.setPasswordHint(account.getDecryptedPasswordHint(user.getPassword()));
                        displayAccount.setAltEmail(account.getAltEmail());
                        displayAccount.setExternalAccess(account.getExternalAccess());
                        displayAccount.setNotes(account.getDecryptedNotes(user.getPassword()));
                        
                        // Back to main thread to show data
                        ((android.app.Activity)view).runOnUiThread(() -> view.showAccountData(displayAccount));
                    }
                }
            });
        }
    }

    @Override
    public void saveAccount(int id, String category, String usage, String siteName, String email, String password, String date,
                            String username, String phone, String secretQuestion, String passwordHint, String altEmail, String oauth, String notes) {
        
        if (siteName.isEmpty() || email.isEmpty() || password.isEmpty() || date.isEmpty()) {
            view.showEmptyFieldsError();
            return;
        }

        Account account = new Account(siteName, email, password, date);
        if (id > 0) account.setId(id);
        
        account.setCategory(category);
        account.setUsage(usage);
        account.setUsername(username);
        account.setPhone(phone);
        account.setSecretQuestion(secretQuestion);
        account.setPasswordHint(passwordHint);
        account.setAltEmail(altEmail);
        account.setExternalAccess(oauth);
        account.setNotes(notes);

        Users user = LoginSingleton.getInstance().getCurrentUser();
        if (user != null) {
            account.secure(user.getPassword());
        }

        if (id <= 0) {
            repository.insert(account);
            view.showSuccessMessage("Cuenta guardada en base de datos");
        } else {
            repository.update(account);
            view.showSuccessMessage("Cuenta actualizada en base de datos");
        }
        view.closeView();
    }
}
