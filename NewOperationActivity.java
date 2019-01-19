package karimi.javad.accounting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import karimi.javad.accounting.db.AcountingDatabase;
import karimi.javad.accounting.db.dao.OperationDao;
import karimi.javad.accounting.db.entity.Operation;

public class NewOperationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnInsert;
    private EditText edtDay, edtMonth, edtDebtor, edtCreditor, edtMoney, edtDesc;
    AcountingDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_operation);

        database = AcountingDatabase.getINSTANSE(this.getApplicationContext());

        btnInsert = findViewById(R.id.btn_insert);
        edtDay = findViewById(R.id.edt_day);
        edtMonth = findViewById(R.id.edt_month);
        edtDebtor = findViewById(R.id.edt_debtor);
        edtCreditor = findViewById(R.id.edt_creditor);
        edtMoney = findViewById(R.id.edt_money);
        edtDesc = findViewById(R.id.edt_desc);

        btnInsert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (edtDay.getText().toString().matches("")) {
            edtDay.requestFocus();
            Toast.makeText(this, "لطفا قسمت‌ها ناقص را تکمیل فرمایید.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtMonth.getText().toString().matches("")) {
            edtMonth.requestFocus();
            Toast.makeText(this, "لطفا قسمت‌ها ناقص را تکمیل فرمایید.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtDebtor.getText().toString().matches("")) {
            edtDebtor.requestFocus();
            Toast.makeText(this, "لطفا قسمت‌ها ناقص را تکمیل فرمایید.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtCreditor.getText().toString().matches("")) {
            edtCreditor.requestFocus();
            Toast.makeText(this, "لطفا قسمت‌ها ناقص را تکمیل فرمایید.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtMoney.getText().toString().matches("")) {
            edtMoney.requestFocus();
            Toast.makeText(this, "لطفا قسمت‌ها ناقص را تکمیل فرمایید.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtDesc.getText().toString().matches("")) {
            edtDesc.requestFocus();
            Toast.makeText(this, "لطفا قسمت‌ها ناقص را تکمیل فرمایید.", Toast.LENGTH_SHORT).show();
            return;
        }


        int day = Integer.parseInt(edtDay.getText().toString());
        int month = Integer.parseInt(edtMonth.getText().toString());
        String debtor = edtDebtor.getText().toString();
        String creditor = edtCreditor.getText().toString();
        String money = edtMoney.getText().toString();
        String desc = edtDesc.getText().toString();


        if (day > 31 || day < 0) {
            edtDay.requestFocus();
            Toast.makeText(this, "لطفا اطلاعات صحیح وارد نمایید.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (month > 12 || day < 0) {
            edtMonth.requestFocus();
            Toast.makeText(this, "لطفا اطلاعات صحیح وارد نمایید.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (month > 6 && day > 30) {
            edtDay.requestFocus();
            Toast.makeText(this, "لطفا اطلاعات صحیح وارد نمایید.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < money.length(); i++) {
            if (!Character.isDigit(money.charAt(i))) {
                edtMoney.requestFocus();
                Toast.makeText(this, "لطفا اطلاعات صحیح وارد نمایید.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Operation operation = new Operation(day, month, debtor, creditor, desc, money);
        insert(operation);
        Toast.makeText(this, "اطلاعات با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void insert(Operation word) {
        new insertAsyncTask(database.operationDao()).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Operation, Void, Void> {

        private OperationDao mAsyncTaskDao;

        insertAsyncTask(OperationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Operation... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}