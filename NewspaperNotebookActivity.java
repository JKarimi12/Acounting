package karimi.javad.accounting;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import karimi.javad.accounting.db.entity.Operation;
import karimi.javad.accounting.view.recyclerView.NewspaperNotebookAdapter;

public class NewspaperNotebookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewspaperNotebookAdapter adapter;
    private NewspaperNotebookViewModel viewModel;
    private TextView txtSum1, txtSum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper_notebook);
        adapter = new NewspaperNotebookAdapter(this.getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view);
        txtSum1 = findViewById(R.id.txt_sum1);
        txtSum2 = findViewById(R.id.txt_sum2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = ViewModelProviders.of(this).get(NewspaperNotebookViewModel.class);
        viewModel.getLiveData().observe(this, new Observer<List<Operation>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@Nullable List<Operation> operations) {
                adapter.setOperationList(operations);
                int sum = 0;
                for(int i = 0; i < operations.size(); i++)
                    sum += Integer.parseInt(operations.get(i).getMoney());
                txtSum1.setText(Integer.toString(sum));
                txtSum2.setText(Integer.toString(sum));
            }
        });
    }
}
