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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.models.Account;
import com.example.paasswordmanager.presenters.ViewRecordsContract;
import com.example.paasswordmanager.presenters.ViewRecordsPresenter;
import java.util.List;

public class ViewRecordsActivity extends AppCompatActivity implements ViewRecordsContract.View, AccountAdapter.OnAccountActionListener {

    private RecyclerView rvAccounts;
    private AccountAdapter adapter;
    private ImageButton btnBack;
    private TextView tvWelcomeUser;
    private ViewRecordsContract.Presenter presenter;

    private final ActivityResultLauncher<Intent> editAccountLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK) {
                presenter.loadUserData();
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        btnBack = findViewById(R.id.btnBack);
        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        rvAccounts = findViewById(R.id.rvAccounts);
        rvAccounts.setLayoutManager(new LinearLayoutManager(this));

        presenter = new ViewRecordsPresenter(this, this);

        btnBack.setOnClickListener(v -> finish());
        
        presenter.loadUserData();
    }

    @Override
    public void showWelcomeMessage(String name, String role) {
        tvWelcomeUser.setText("Bienvenido, " + name);
        if ("Root".equals(role)) {
            tvWelcomeUser.setTextColor(Color.RED);
        } else {
            tvWelcomeUser.setTextColor(Color.GREEN);
        }
    }

    @Override
    public void displayAccounts(List<Account> accounts, String role) {
        adapter = new AccountAdapter(accounts, role, this);
        rvAccounts.setAdapter(adapter);
    }

    @Override
    public void showDeleteConfirmation(int position) {
        new AlertDialog.Builder(this)
            .setTitle("Eliminar Cuenta")
            .setMessage("¿Estás seguro de que deseas eliminar esta cuenta?")
            .setPositiveButton("Eliminar", (dialog, which) -> presenter.confirmDeleteAccount(position))
            .setNegativeButton("Cancelar", null)
            .show();
    }

    @Override
    public void navigateToEditAccount(int position) {
        Intent intent = new Intent(this, EditAccountActivity.class);
        intent.putExtra("ACCOUNT_INDEX", position);
        editAccountLauncher.launch(intent);
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEdit(int position) {
        presenter.onEditAccountClicked(position);
    }

    @Override
    public void onDelete(int position) {
        presenter.onDeleteAccountClicked(position);
    }
}
