package com.example.paasswordmanager.presenters;

import android.content.Context;
import com.example.paasswordmanager.managers.LoginSingleton;
import com.example.paasswordmanager.models.Users;

public class ViewRecordsPresenter implements ViewRecordsContract.Presenter {

    private ViewRecordsContract.View view;
    private Context context;

    public ViewRecordsPresenter(ViewRecordsContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void loadUserData() {
        Users currentUser = LoginSingleton.getInstance().getCurrentUser();
        if (currentUser != null) {
            view.showWelcomeMessage(currentUser.getName(), currentUser.getRole());
            view.displayAccounts(LoginSingleton.getInstance().getAccountList(), currentUser.getRole());
        }
    }

    @Override
    public void onEditAccountClicked(int position) {
        view.navigateToEditAccount(position);
    }

    @Override
    public void onDeleteAccountClicked(int position) {
        view.showDeleteConfirmation(position);
    }

    @Override
    public void confirmDeleteAccount(int position) {
        LoginSingleton.getInstance().deleteAccount(context, position);
        view.updateList();
    }
}
