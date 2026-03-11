package com.example.paasswordmanager.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.managers.LoginSingleton;
import com.example.paasswordmanager.models.Account;
import com.example.paasswordmanager.viewmodels.AccountViewModel;
import java.util.ArrayList;
import java.util.List;

public class ViewRecordsActivity extends AppCompatActivity implements AccountAdapter.OnAccountActionListener {

    private RecyclerView rvAccounts;
    private AccountAdapter adapter;
    private ImageButton btnBack;
    private TextView tvWelcomeUser;
    private AccountViewModel accountViewModel;
    private String currentUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        btnBack = findViewById(R.id.btnBack);
        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        rvAccounts = findViewById(R.id.rvAccounts);
        rvAccounts.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar ViewModel
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        currentUserRole = LoginSingleton.getInstance().getCurrentUser().getRole();
        tvWelcomeUser.setText("Bienvenido, " + LoginSingleton.getInstance().getCurrentUser().getName());
        tvWelcomeUser.setTextColor("Root".equals(currentUserRole) ? Color.RED : Color.GREEN);

        // Observar cambios en la base de datos (MVVM)
        accountViewModel.getAllAccounts().observe(this, accounts -> {
            adapter = new AccountAdapter(accounts, currentUserRole, this);
            rvAccounts.setAdapter(adapter);
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    public void onEdit(int position) {
        Account account = adapter.getAccountAt(position);
        Intent intent = new Intent(this, EditAccountActivity.class);
        intent.putExtra("ACCOUNT_ID", account.getId()); // Usamos el ID de Room
        startActivity(intent);
    }

    @Override
    public void onDelete(int position) {
        Account account = adapter.getAccountAt(position);
        new AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de borrar la cuenta " + account.getSiteName() + "?")
            .setPositiveButton("Eliminar", (dialog, which) -> accountViewModel.delete(account))
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
