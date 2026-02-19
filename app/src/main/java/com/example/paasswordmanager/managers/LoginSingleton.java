package com.example.paasswordmanager.managers;

import android.content.Context;
import com.example.paasswordmanager.models.Account;
import com.example.paasswordmanager.models.Users;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class LoginSingleton {

    private static LoginSingleton instance;
    private ArrayList<Users> userList;
    private ArrayList<Account> accountList;
    private Users currentUser;
    private boolean useBiometrics = false;
    private boolean setupCompleted = false;
    
    private final JsonFileManager fileManager;
    private static final String USERS_FILE = "users_data.json";
    private static final String ACCOUNTS_FILE = "accounts_data.json";
    private static final String CONFIG_FILE = "config_data.json";
    private static final String SETUP_FILE = "setup_status.json";

    // Predefined Limited User
    private static final Users LIMITED_USER = new Users("Usuario Invitado", "limited@example.com", "000000000", "none", "Limited");

    private LoginSingleton() {
        this.fileManager = new JsonFileManager();
        this.userList = new ArrayList<>();
        this.accountList = new ArrayList<>();
    }

    public static LoginSingleton getInstance() {
        if (instance == null) {
            instance = new LoginSingleton();
        }
        return instance;
    }

    public void init(Context context) {
        ArrayList<Users> loadedUsers = fileManager.loadFromFile(context, USERS_FILE, new TypeToken<ArrayList<Users>>(){}.getType());
        this.userList = (loadedUsers != null) ? loadedUsers : new ArrayList<>();

        ArrayList<Account> loadedAccounts = fileManager.loadFromFile(context, ACCOUNTS_FILE, new TypeToken<ArrayList<Account>>(){}.getType());
        this.accountList = (loadedAccounts != null) ? loadedAccounts : new ArrayList<>();

        Boolean loadedBio = fileManager.loadFromFile(context, CONFIG_FILE, Boolean.class);
        this.useBiometrics = (loadedBio != null) ? loadedBio : false;

        Boolean loadedSetup = fileManager.loadFromFile(context, SETUP_FILE, Boolean.class);
        this.setupCompleted = (loadedSetup != null) ? loadedSetup : false;

        // Por defecto al iniciar, siempre es Limited
        this.currentUser = LIMITED_USER;
    }

    public void saveData(Context context) {
        fileManager.saveToFile(context, USERS_FILE, userList);
        fileManager.saveToFile(context, ACCOUNTS_FILE, accountList);
        fileManager.saveToFile(context, CONFIG_FILE, useBiometrics);
        fileManager.saveToFile(context, SETUP_FILE, setupCompleted);
    }

    public void logout(Context context) {
        // Al cerrar sesión, volvemos a Limited
        this.currentUser = LIMITED_USER;
    }

    public boolean isValidRoot(String email, String pass) {
        for (Users user : userList) {
            if ("Root".equals(user.getRole()) && (email.isEmpty() || user.getEmail().equals(email)) && user.getPassword().equals(pass)) {
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void addUser(Context context, Users user) {
        this.userList.add(user);
        saveData(context);
    }

    public boolean isSetupCompleted() {
        return setupCompleted;
    }

    public void setSetupCompleted(Context context, boolean setupCompleted) {
        this.setupCompleted = setupCompleted;
        saveData(context);
    }

    public boolean isEmpty() {
        for (Users u : userList) {
            if ("Root".equals(u.getRole())) return false;
        }
        return true;
    }

    public Users getCurrentUser() { return currentUser; }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }
    
    public boolean isRoot() {
        return currentUser != null && "Root".equals(currentUser.getRole());
    }

    public void setUseBiometrics(Context context, boolean useBiometrics) {
        this.useBiometrics = useBiometrics;
        saveData(context);
    }

    public boolean isUseBiometrics() { return useBiometrics; }

    public void addAccount(Context context, Account account) {
        this.accountList.add(account);
        saveData(context);
    }

    public ArrayList<Account> getAccountList() { return accountList; }

    public void updateAccount(Context context, int index, Account account) {
        if (index >= 0 && index < accountList.size()) {
            accountList.set(index, account);
            saveData(context);
        }
    }

    public void deleteAccount(Context context, int index) {
        if (index >= 0 && index < accountList.size()) {
            accountList.remove(index);
            saveData(context);
        }
    }
}
