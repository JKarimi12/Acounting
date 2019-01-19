package karimi.javad.accounting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnNewOperation, btnNewpaperNotebook, btnTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNewOperation = findViewById(R.id.btn_new_operation);
        btnNewpaperNotebook = findViewById(R.id.btn_newspaper);
        btnTotal = findViewById(R.id.btn_total);
        btnNewOperation.setOnClickListener(this);
        btnNewpaperNotebook.setOnClickListener(this);
        btnTotal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_new_operation :
                intent = new Intent(this, NewOperationActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_newspaper :
                intent = new Intent(this, NewspaperNotebookActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_total :
                intent = new Intent(this, TotalNotebookActivity.class);
                startActivity(intent);
                break;
        }
    }
}
