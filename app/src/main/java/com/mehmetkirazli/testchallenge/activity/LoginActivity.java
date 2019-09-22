package com.mehmetkirazli.testchallenge.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.mehmetkirazli.testchallenge.R;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnGiris;
    Switch swRememberMe;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnGiris = findViewById(R.id.btnGiris);
        swRememberMe = findViewById(R.id.swRememberMe);

        prefs = getSharedPreferences("login", MODE_PRIVATE);
        editor = getSharedPreferences("login", MODE_PRIVATE).edit();

        if (prefs.getBoolean("rememberMe", false))
            startActivity(new Intent(LoginActivity.this, OrderActivity.class));

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Kullanıcı Adı ve Şifre Girilmelidir", Toast.LENGTH_SHORT).show();
                else {
                    if (edtUsername.getText().toString().equals("kariyer") && edtPassword.getText().toString().equals("2019ADev")) {
                        if (swRememberMe.isChecked())
                            editor.putBoolean("rememberMe", true);
                        else
                            editor.putBoolean("rememberMe", false);
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, OrderActivity.class));
                    } else
                        Toast.makeText(getApplicationContext(), "Kullanıcı Adı veya Şifre Hatalı", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}