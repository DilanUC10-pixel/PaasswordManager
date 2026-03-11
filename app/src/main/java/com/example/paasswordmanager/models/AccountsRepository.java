package com.example.paasswordmanager.models;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class AccountsRepository {

    private AccountDao accountDao;
    private LiveData<List<Account>> allAccounts;

    public AccountsRepository(Application application) {
        AccountsRoomDB db = AccountsRoomDB.getDatabase(application);
        accountDao = db.accountDao();
        allAccounts = accountDao.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }

    public LiveData<List<Account>> getAccountsByCategory(String category) {
        return accountDao.getAccountsByCategory(category);
    }

    public void insert(Account account) {
        AccountsRoomDB.databaseWriteExecutor.execute(() -> {
            accountDao.insert(account);
        });
    }

    public void update(Account account) {
        AccountsRoomDB.databaseWriteExecutor.execute(() -> {
            accountDao.update(account);
        });
    }

    public void delete(Account account) {
        AccountsRoomDB.databaseWriteExecutor.execute(() -> {
            accountDao.delete(account);
        });
    }
}
