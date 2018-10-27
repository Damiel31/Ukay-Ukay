package com.example.damiel.ukayukay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText txtuser,txtpass;
    Button btnlogin;
    TextView reg;
    public static final String TAG_NAME = "nameKey";



    SharedPreferences shared;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtuser = (EditText) findViewById(R.id.txtusernameL);
        txtpass = (EditText) findViewById(R.id.txtpasswordL);
        btnlogin = (Button) findViewById(R.id.btnLoginL);
        reg = (TextView) findViewById(R.id.txtregiserL);

        shared = getSharedPreferences("USER", Context.MODE_PRIVATE);
        db = new DbHelper(this);

        btnlogin.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginL:
                int count = 2;
                String ad = "admin";
                if (txtuser.getText().equals("")){
                    txtuser.setError("Please enter username");
                    count--;
                }
                if (txtpass.getText().equals("")){
                    txtpass.setError("Please enter password");
                    count--;
                }
                if (txtuser.getText().toString().equals(ad) && txtpass.getText().toString().equals(ad)){
                    startActivity(new Intent(this, MainActivity.class));
                    count--;

                }
                if (count == 2){
                    long Userid = db.checkuser(txtuser.getText().toString(),txtpass.getText().toString());
                    if (Userid > 0){
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putLong("UserId", Userid);
                        editor.commit();
                        Intent intent = new Intent(Login.this, UserHome.class);
                        intent.putExtra(TAG_NAME,txtuser.getText().toString());
                        startActivity(intent);
                        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(this, "User doesnt exist", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case  R.id.txtregiserL:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
