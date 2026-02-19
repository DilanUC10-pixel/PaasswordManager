package com.example.paasswordmanager.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.models.Account;
import com.example.paasswordmanager.presenters.AccountManagementContract;
import com.example.paasswordmanager.presenters.AccountManagementPresenter;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditAccountActivity extends AppCompatActivity implements AccountManagementContract.View {

    private Spinner spCategory;
    private RadioGroup rgUsage;
    private EditText etUrl, etAccountName, etAccountEmail, etUsername, etAccountPassword, etAccountPhone, etAltEmail, etRegisterDate;
    private EditText etSecretQuestion, etPasswordHint, etOAuth, etNotes;
    private CheckBox cbHasUsername, cbHasPhone, cbHasAltEmail, cbHasSecurity, cbHasExtra;
    private TextInputLayout tilUrl, tilSiteName, tilEmail, tilPassword, tilRegisterDate;
    private TextInputLayout tilUsername, tilPhone, tilAltEmail, tilSecretQuestion, tilPasswordHint, tilOAuth, tilNotes;
    private Button btnUpdateAccount;
    private ImageButton btnBack;
    private TextView tvEditTitle;
    private int accountIndex = -1;
    private AccountManagementContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        presenter = new AccountManagementPresenter(this, this);

        // Initialize views
        spCategory = findViewById(R.id.spCategoryEdit);
        rgUsage = findViewById(R.id.rgUsageEdit);
        
        tilUrl = findViewById(R.id.tilUrlEdit);
        etUrl = findViewById(R.id.etUrlEdit);
        
        tilSiteName = findViewById(R.id.tilSiteName);
        etAccountName = findViewById(R.id.etAccountNameEdit);
        
        tilEmail = findViewById(R.id.tilEmail);
        etAccountEmail = findViewById(R.id.etAccountEmailEdit);
        
        tilPassword = findViewById(R.id.tilPassword);
        etAccountPassword = findViewById(R.id.etAccountPasswordEdit);
        
        tilRegisterDate = findViewById(R.id.tilRegisterDate);
        etRegisterDate = findViewById(R.id.etRegisterDateEdit);

        tilUsername = findViewById(R.id.tilUsername);
        etUsername = findViewById(R.id.etUsernameEdit);
        
        tilPhone = findViewById(R.id.tilPhone);
        etAccountPhone = findViewById(R.id.etAccountPhoneEdit);
        
        tilAltEmail = findViewById(R.id.tilAltEmail);
        etAltEmail = findViewById(R.id.etAltEmailEdit);
        
        tilSecretQuestion = findViewById(R.id.tilSecretQuestion);
        etSecretQuestion = findViewById(R.id.etSecretQuestion);
        
        tilPasswordHint = findViewById(R.id.tilPasswordHint);
        etPasswordHint = findViewById(R.id.etPasswordHint);
        
        tilOAuth = findViewById(R.id.tilOAuth);
        etOAuth = findViewById(R.id.etOAuth);
        
        tilNotes = findViewById(R.id.tilNotes);
        etNotes = findViewById(R.id.etNotes);
        
        cbHasUsername = findViewById(R.id.cbHasUsernameEdit);
        cbHasPhone = findViewById(R.id.cbHasPhoneEdit);
        cbHasAltEmail = findViewById(R.id.cbHasAltEmailEdit);
        cbHasSecurity = findViewById(R.id.cbHasSecurityEdit);
        cbHasExtra = findViewById(R.id.cbHasExtraEdit);

        btnUpdateAccount = findViewById(R.id.btnUpdateAccount);
        btnBack = findViewById(R.id.btnBack);
        tvEditTitle = findViewById(R.id.tvEditTitle);

        btnBack.setOnClickListener(v -> finish());

        setupVisibilityListeners();
        setupTextWatchers();

        if (getIntent().hasExtra("ACCOUNT_INDEX")) {
            accountIndex = getIntent().getIntExtra("ACCOUNT_INDEX", -1);
            presenter.loadAccount(accountIndex);
            tvEditTitle.setText(R.string.title_edit_account);
            btnUpdateAccount.setText(R.string.btn_update_account);
        } else {
            tvEditTitle.setText(R.string.title_add_account);
            btnUpdateAccount.setText(R.string.btn_save_account);
            // Set current date for new account
            etRegisterDate.setText(getCurrentDate());
        }

        btnUpdateAccount.setOnClickListener(v -> {
            clearErrors();
            String usage = "App";
            int usageId = rgUsage.getCheckedRadioButtonId();
            if (usageId == R.id.rbWebEdit) usage = "Web";
            else if (usageId == R.id.rbBothEdit) usage = "Ambas";

            presenter.saveAccount(
                accountIndex,
                spCategory.getSelectedItem().toString(),
                usage,
                etAccountName.getText().toString().trim(),
                etAccountEmail.getText().toString().trim(),
                etAccountPassword.getText().toString().trim(),
                etRegisterDate.getText().toString().trim(),
                etUsername.getText().toString().trim(),
                etAccountPhone.getText().toString().trim(),
                etSecretQuestion.getText().toString().trim(),
                etPasswordHint.getText().toString().trim(),
                etAltEmail.getText().toString().trim(),
                etOAuth.getText().toString().trim(),
                etNotes.getText().toString().trim()
            );
        });
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    private void setupTextWatchers() {
        etAccountName.addTextChangedListener(new SimpleTextWatcher(tilSiteName));
        etAccountEmail.addTextChangedListener(new SimpleTextWatcher(tilEmail));
        etAccountPassword.addTextChangedListener(new SimpleTextWatcher(tilPassword));
        etRegisterDate.addTextChangedListener(new SimpleTextWatcher(tilRegisterDate));
    }

    private void clearErrors() {
        tilSiteName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilRegisterDate.setError(null);
    }

    private void setupVisibilityListeners() {
        rgUsage.setOnCheckedChangeListener((group, checkedId) -> {
            tilUrl.setVisibility((checkedId == R.id.rbWebEdit || checkedId == R.id.rbBothEdit) ? View.VISIBLE : View.GONE);
        });

        cbHasUsername.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tilUsername.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        cbHasPhone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tilPhone.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        cbHasAltEmail.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tilAltEmail.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        cbHasSecurity.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tilSecretQuestion.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            tilPasswordHint.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        cbHasExtra.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tilOAuth.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            tilNotes.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void showAccountData(Account account) {
        etAccountName.setText(account.getSiteName());
        etAccountEmail.setText(account.getEmail());
        etAccountPassword.setText(account.getPassword());
        etRegisterDate.setText(account.getRegisterDate());

        if (account.getUsername() != null && !account.getUsername().isEmpty()) {
            cbHasUsername.setChecked(true);
            etUsername.setText(account.getUsername());
        }

        if (account.getPhone() != null && !account.getPhone().isEmpty()) {
            cbHasPhone.setChecked(true);
            etAccountPhone.setText(account.getPhone());
        }

        if (account.getAltEmail() != null && !account.getAltEmail().isEmpty()) {
            cbHasAltEmail.setChecked(true);
            etAltEmail.setText(account.getAltEmail());
        }

        if ((account.getSecretQuestion() != null && !account.getSecretQuestion().isEmpty()) || 
            (account.getPasswordHint() != null && !account.getPasswordHint().isEmpty())) {
            cbHasSecurity.setChecked(true);
            etSecretQuestion.setText(account.getSecretQuestion());
            etPasswordHint.setText(account.getPasswordHint());
        }

        if ((account.getExternalAccess() != null && !account.getExternalAccess().isEmpty()) || 
            (account.getNotes() != null && !account.getNotes().isEmpty())) {
            cbHasExtra.setChecked(true);
            etOAuth.setText(account.getExternalAccess());
            etNotes.setText(account.getNotes());
        }
        
        // Handle usage radio buttons
        if ("Web".equals(account.getUsage())) {
            ((RadioButton)findViewById(R.id.rbWebEdit)).setChecked(true);
            tilUrl.setVisibility(View.VISIBLE);
        } else if ("Ambas".equals(account.getUsage())) {
            ((RadioButton)findViewById(R.id.rbBothEdit)).setChecked(true);
            tilUrl.setVisibility(View.VISIBLE);
        } else {
            ((RadioButton)findViewById(R.id.rbAppEdit)).setChecked(true);
        }
    }

    @Override
    public void showEmptyFieldsError() {
        Toast.makeText(this, "Por favor, completa los campos obligatorios resaltados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFieldError(String fieldName) {
        switch (fieldName) {
            case "siteName":
                tilSiteName.setError("Este campo es obligatorio");
                break;
            case "email":
                tilEmail.setError("Este campo es obligatorio");
                break;
            case "password":
                tilPassword.setError("Este campo es obligatorio");
                break;
            case "date":
                tilRegisterDate.setError("Este campo es obligatorio");
                break;
        }
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeView() {
        setResult(RESULT_OK);
        finish();
    }

    private static class SimpleTextWatcher implements TextWatcher {
        private final TextInputLayout layout;

        public SimpleTextWatcher(TextInputLayout layout) {
            this.layout = layout;
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            layout.setError(null);
        }
        @Override public void afterTextChanged(Editable s) {}
    }
}
