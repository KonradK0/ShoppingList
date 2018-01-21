package com.example.kuba.firebasetutorial.activities.main_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.kuba.firebasetutorial.R;

public class MainActivityView extends AppCompatActivity {
    MainActivityController controller;
    private EditText loginField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("THERE WE FUCKIN 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_view);
        loginField = findViewById(R.id.loginEditText);
        passwordField = findViewById(R.id.pwEditText);
        controller = new MainActivityController(this);
    }

    public void onClickLogin(View view) {
        controller.handleCheckCredentials(loginField.getText().toString(), passwordField.getText().toString());
    }

    public void onClickRegister(View view) {
        controller.startRegisterView();
    }
}