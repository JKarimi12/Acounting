package karimi.javad.accounting;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import karimi.javad.accounting.db.entity.Operation;
import karimi.javad.accounting.model.Account;
import karimi.javad.accounting.model.OnItemClickListener;
import karimi.javad.accounting.view.recyclerView.TotalNotebookAdapter;

public class TotalNotebookActivity extends AppCompatActivity implements OnItemClickListener {

    private TotalNotebookViewModel viewModel;
    private RecyclerView recyclerView;
    private TotalNotebookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_notebook);
        viewModel = ViewModelProviders.of(this).get(TotalNotebookViewModel.class);

        adapter = new TotalNotebookAdapter(getApplicationContext());
        adapter.setClickListener(this);
        recyclerView = findViewById(R.id.recycler_view_account);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getLiveData().observe(this, new Observer<List<Operation>>() {
            @Override
            public void onChanged(@Nullable List<Operation> operations) {
                List<Account> accounts = new ArrayList<>();
                List<String> tmp = new ArrayList<>();
                for(int i = 0; i < operations.size(); i++) {
                    if (!tmp.contains(operations.get(i).getCreditor())) {
                        tmp.add(operations.get(i).getCreditor());
                        accounts.add(new Account(operations.get(i).getCreditor()));
                    }
                    if (!tmp.contains(operations.get(i).getDebtor())) {
                        tmp.add(operations.get(i).getDebtor());
                        accounts.add(new Account(operations.get(i).getDebtor()));
                    }
                }
                adapter.setAccounts(accounts);
            }
        });


    }

    @Override
    public void onClick(View view, int position) {
        String account = adapter.getAccounts().get(position).getName();
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
    }
}
