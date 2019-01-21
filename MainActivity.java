package karimi.javad.accounting;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import karimi.javad.accounting.db.entity.Operation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnNewOperation, btnNewpaperNotebook, btnTotal;
    private MainViewModel viewModel;
    private List<Operation> operationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        btnNewOperation = findViewById(R.id.btn_new_operation);
        btnNewpaperNotebook = findViewById(R.id.btn_newspaper);
        btnTotal = findViewById(R.id.btn_total);
        btnNewOperation.setOnClickListener(this);
        btnNewpaperNotebook.setOnClickListener(this);
        btnTotal.setOnClickListener(this);
        operationList = new ArrayList<>();
        viewModel.getLiveData().observe(this, new Observer<List<Operation>>() {
            @Override
            public void onChanged(@Nullable List<Operation> operations) {
                operationList = operations;
                Collections.sort(operationList);
            }
        });
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_us :
                return true;
            case R.id.export_newspaper_notebook :
                exportNewspaperNotebook();
                return true;
            case R.id.export_total_notebook :
                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void exportNewspaperNotebook() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File sd = Environment.getExternalStorageDirectory();
                String csvFile = "دفتر روزنامه.xls";
                File directory = new File(sd.getAbsolutePath());
                if (!directory.isDirectory()) {
                    directory.mkdirs();
                }
                try {

                    //file path
                    File file = new File(directory, csvFile);
                    WorkbookSettings wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("fa", "IR"));
                    WritableWorkbook workbook;
                    workbook = Workbook.createWorkbook(file, wbSettings);
                    //Excel sheet name. 0 represents first sheet
                    WritableSheet sheet = workbook.createSheet("userList", 0);
                    // column and row
                    sheet.addCell(new Label(0, 0, "ردیف"));
                    sheet.addCell(new Label(1, 0, "روز"));
                    sheet.addCell(new Label(2, 0, "ماه"));
                    sheet.addCell(new Label(3, 0, "بدهکار"));
                    sheet.addCell(new Label(4, 0, "بستانکار"));
                    sheet.addCell(new Label(5, 0, "مبلغ"));
                    sheet.addCell(new Label(6, 0, "شرح عملیات"));

                    for(int i = 0; i < operationList.size(); i++){
                        Operation operation = operationList.get(i);
                        String row = Integer.toString(i + 1);
                        String day = Integer.toString(operation.getDay());
                        String month = Integer.toString(operation.getMonth());
                        String bed = operation.getDebtor();
                        String bes = operation.getCreditor();
                        String money = operation.getMoney();
                        String desc = operation.getDescription();
                        sheet.addCell(new Label(0, i + 1, row));
                        sheet.addCell(new Label(1, i + 1, day));
                        sheet.addCell(new Label(2, i + 1, month));
                        sheet.addCell(new Label(3, i + 1, bed));
                        sheet.addCell(new Label(4, i + 1, bes));
                        sheet.addCell(new Label(5, i + 1, money));
                        sheet.addCell(new Label(6, i + 1, desc));
                    }
                    //closing cursor
                    workbook.write();
                    workbook.close();
//                    Toast.makeText(getApplication(),
//                            "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("why?????", e.getMessage());
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).start();
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



    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission","Permission is granted1");
                return true;
            } else {

                Log.v("permission","Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permission","Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission","Permission is granted2");
                return true;
            } else {

                Log.v("permission","Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permission","Permission is granted2");
            return true;
        }
    }
}
