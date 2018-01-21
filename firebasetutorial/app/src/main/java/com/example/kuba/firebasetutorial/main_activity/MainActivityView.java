package com.example.kuba.firebasetutorial.main_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.LoggedInScreen;
import com.example.kuba.firebasetutorial.R;
import com.example.kuba.firebasetutorial.RegisterScreen;
import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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