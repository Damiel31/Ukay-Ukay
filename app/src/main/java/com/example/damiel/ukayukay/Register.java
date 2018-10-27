package com.example.damiel.ukayukay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText txtuser,txtname,txtpass;
    Button btncreate;
    TextView cancel;

    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtuser = (EditText) findViewById(R.id.txtusernameR);
        txtname = (EditText) findViewById(R.id.txtnameR);
        txtpass = (EditText) findViewById(R.id.txtpasswordR);
        btncreate = (Button) findViewById(R.id.btncreateR);
        cancel = (TextView) findViewById(R.id.cancelR);

        db = new DbHelper(this);

        btncreate.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btncreateR:
                int count = 2;

                if(txtuser.getText().toString().equals("")){
                    txtuser.setError("Please enter username");
                    count--;
                }
                if (txtname.getText().toString().equals("")){
                    txtname.setError("Please enter name");
                    count--;
                }
                if (txtpass.getText().toString().equals("")) {
                    txtpass.setError("please enter your password");
                    count--;
                }
                if (count == 2){
                    HashMap<String, String> map_user = new HashMap<>();
                    map_user.put("Username", txtuser.getText().toString());
                    map_user.put("Password", txtpass.getText().toString());
                    map_user.put("Name", txtname.getText().toString());
                    if (db.createUser(map_user)> 0){
                        startActivity(new Intent(this, Login.class));
                        Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(this, "Account already existed", Toast.LENGTH_SHORT).show();
                    }

                }

                break;

            case R.id.cancelR:
                this.finish();

        }
    }
}
