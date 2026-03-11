package com.example.paasswordmanager.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.paasswordmanager.models.Account;
import com.example.paasswordmanager.models.AccountsRepository;
import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountsRepository repository;
    private LiveData<List<Account>> allAccounts;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountsRepository(application);
        allAccounts = repository.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }

    public LiveData<List<Account>> getAccountsByCategory(String category) {
        return repository.getAccountsByCategory(category);
    }

    public void insert(Account account) {
        repository.insert(account);
    }

    public void update(Account account) {
        repository.update(account);
    }

    public void delete(Account account) {
        repository.delete(account);
    }
}
