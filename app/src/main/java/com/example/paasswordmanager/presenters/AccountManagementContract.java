package com.example.paasswordmanager.presenters;

import com.example.paasswordmanager.models.Account;

public interface AccountManagementContract {
    interface View {
        void showAccountData(Account account);
        void showEmptyFieldsError();
        void showFieldError(String fieldName);
        void showSuccessMessage(String message);
        void closeView();
    }

    interface Presenter {
        void loadAccount(int index);
        void saveAccount(int index, String category, String usage, String siteName, String email, String password, String date,
                         String username, String phone, String secretQuestion, String passwordHint, String altEmail, String oauth, String notes);
    }
}
