package com.example.alpha.reader_materialdesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HadLoginActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_had_login);
        button = findViewById(R.id.loginOff);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("loginData", MODE_PRIVATE).edit();
                editor.putString("login", "false");
                editor.apply();
                finish();
            }
        });
    }
}
