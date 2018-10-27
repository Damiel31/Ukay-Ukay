package com.example.damiel.ukayukay;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity {

    TextView name;
    public static final String TAG_NAME = "nameKey";

    List<Employee> employeeList;
    ListView listViewEmployees;
    SQLiteDatabase mDatabase;
    EmployeeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        name = (TextView) findViewById(R.id.txtwelcome);

        String Name=getIntent().getStringExtra(TAG_NAME);
        name.setText("Welcome " + Name);

        mDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        listViewEmployees = (ListView) findViewById(R.id.listViewOrder);
        employeeList = new ArrayList<>();

        showEmployeesFromDatabase();


    }

    private void showEmployeesFromDatabase() {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees", null);

        if (cursorEmployees.moveToFirst()) {
            do {
                employeeList.add(new Employee(
                        cursorEmployees.getInt(0),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getDouble(4)
                ));

            }while (cursorEmployees.moveToNext());
            cursorEmployees.close();

            adapter = new EmployeeAdapter(this, R.layout.list_layout_orders, employeeList, mDatabase);
            listViewEmployees.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.btnlogout:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
