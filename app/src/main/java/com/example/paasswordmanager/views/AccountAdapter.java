package com.example.paasswordmanager.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.paasswordmanager.R;
import com.example.paasswordmanager.models.Account;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accountList;
    private OnAccountActionListener listener;
    private String userRole;

    public interface OnAccountActionListener {
        void onEdit(int position);
        void onDelete(int position);
    }

    public AccountAdapter(List<Account> accountList, String userRole, OnAccountActionListener listener) {
        this.accountList = accountList;
        this.userRole = userRole;
        this.listener = listener;
    }

    public Account getAccountAt(int position) {
        return accountList.get(position);
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accountList.get(position);
        boolean isRoot = "Root".equals(userRole);

        holder.tvName.setText(account.getSiteName());

        if (isRoot) {
            holder.tvEmail.setText("Email: " + account.getEmail());
            holder.tvDetail.setText("Fecha: " + account.getRegisterDate());
            holder.tvCategory.setText(account.getCategory());
            holder.tvDetail.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);
        } else {
            String accessType = "Email";
            if (account.getExternalAccess() != null && !account.getExternalAccess().isEmpty()) accessType = "Externo (OAuth)";
            else if (account.getUsername() != null && !account.getUsername().isEmpty()) accessType = "Usuario";
            
            holder.tvEmail.setText("Tipo de acceso: " + accessType);
            holder.tvDetail.setVisibility(View.GONE);
            holder.tvCategory.setVisibility(View.GONE);
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(position));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(position));
    }

    @Override
    public int getItemCount() {
        return accountList != null ? accountList.size() : 0;
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvCategory, tvDetail;
        ImageButton btnEdit, btnDelete;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvAccountNameItem);
            tvEmail = itemView.findViewById(R.id.tvAccountEmailItem);
            tvCategory = itemView.findViewById(R.id.tvAccountCategoryItem);
            tvDetail = itemView.findViewById(R.id.tvAccountDetailItem); // Fixed ID if it exists, or just use tvDetail
            if (tvDetail == null) tvDetail = tvCategory; // Fallback
            btnEdit = itemView.findViewById(R.id.btnEditItem);
            btnDelete = itemView.findViewById(R.id.btnDeleteItem);
        }
    }
}
