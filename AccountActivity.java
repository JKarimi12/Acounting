package karimi.javad.accounting;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import karimi.javad.accounting.db.AcountingDatabase;
import karimi.javad.accounting.db.entity.Operation;
import karimi.javad.accounting.view.recyclerView.AccountAdapter;

public class AccountActivity extends AppCompatActivity {

    private TextView txtAccountName, txtRemine;
    private RecyclerView recyclerView;
    private AccountAdapter adapter;
    private String account;
    private AccountViewModel viewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        setContentView(R.layout.activity_account);
        txtAccountName = findViewById(R.id.txt_account_name);
        txtRemine = findViewById(R.id.txt_r);
        recyclerView = findViewById(R.id.recycler_view_account);
        Bundle bundle = getIntent().getExtras();
        account = bundle.get("account").toString();
        txtAccountName.setText("نام حساب : " + account);
        adapter = new AccountAdapter(this, account);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getLiveData().observe(this, new Observer<List<Operation>>() {
            @Override
            public void onChanged(@Nullable List<Operation> operations) {
                List<Operation> list = new ArrayList<>();
                for(int i = 0; i < operations.size(); i++)
                    if(operations.get(i).getCreditor().equals(account) || operations.get(i).getDebtor().equals(account))
                        list.add(operations.get(i));
                adapter.setOperationList(list);
            }
        });

        adapter.getRemineLivedata().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                txtRemine.setText(Integer.toString(Math.abs(integer)) );
            }
        });



    }
}

