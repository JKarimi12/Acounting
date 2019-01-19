package karimi.javad.accounting.view.recyclerView;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import karimi.javad.accounting.R;
import karimi.javad.accounting.model.Account;
import karimi.javad.accounting.model.OnItemClickListener;

public class TotalNotebookAdapter extends RecyclerView.Adapter<TotalNotebookAdapter.TotalNotebookHolder> {

    private final LayoutInflater mInflater;
    private List<Account> accounts;
    OnItemClickListener clickListener;

    public TotalNotebookAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TotalNotebookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.account_item, viewGroup, false);
        final TotalNotebookHolder holder = new TotalNotebookHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TotalNotebookHolder totalNotebookHolder, int i) {
        if(accounts != null){
            totalNotebookHolder.txtAccount.setText(accounts.get(i).getName());
        }
        else{
            totalNotebookHolder.txtAccount.setText("An error occurred!");
        }
    }

    @Override
    public int getItemCount() {
        if(accounts == null)
            return 0;
        else
            return accounts.size();
    }


    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class TotalNotebookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtAccount;

        public TotalNotebookHolder(@NonNull View itemView) {
            super(itemView);
            txtAccount = itemView.findViewById(R.id.txt_account);
            txtAccount.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
        }
    }

}
