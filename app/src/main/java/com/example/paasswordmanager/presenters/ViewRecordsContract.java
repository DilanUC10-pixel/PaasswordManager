package com.example.paasswordmanager.presenters;

import com.example.paasswordmanager.models.Account;
import java.util.List;

public interface ViewRecordsContract {
    interface View {
        void showWelcomeMessage(String name, String role);
        void displayAccounts(List<Account> accounts, String role);
        void showDeleteConfirmation(int position);
        void navigateToEditAccount(int position);
        void updateList();
    }

    interface Presenter {
        void loadUserData();
        void onEditAccountClicked(int position);
        void onDeleteAccountClicked(int position);
        void confirmDeleteAccount(int position);
    }
}
