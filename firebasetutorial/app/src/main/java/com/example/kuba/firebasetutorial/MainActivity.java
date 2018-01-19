package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Database db;
    private EditText loginField;
    private EditText passwordField;
    boolean userFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("THERE WE FUCKIN 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_view);
        db = FireDatabase.getInstance();
        loginField = findViewById(R.id.loginEditText);
        passwordField = findViewById(R.id.pwEditText);
        //userFound = false;
    }

    public void onClickLogin(View view) {
        userFound = false;
        User found = db.checkCredentials(loginField.getText().toString(), passwordField.getText().toString(), userFound);
        Log.i("userLogin", found.getLogin());
        Log.i("userPw", found.getPassword());
        if(found.isEmpty()){
            Toast.makeText(getApplicationContext(), "USER NOT FOUND", Toast.LENGTH_LONG).show();
        }
        else {
            startView(found.getUid());
        }
    }

    void startView(String userID) {
        Intent intent = new Intent(this, LoggedInScreen.class);
        intent.putExtra("USERID", userID);
        startActivity(intent);
    }

    public void onClickRegister(View view) {
        startActivity(new Intent(this, RegisterScreen.class));
    }
}