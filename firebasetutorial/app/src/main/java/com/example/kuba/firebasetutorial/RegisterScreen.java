package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Kuba on 17/01/2018.
 */

public class RegisterScreen extends AppCompatActivity {
    Database db;
    DatabaseReference myRef;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_view);
        db = FireDatabase.getInstance();
    }

    public void onClickRegister(View view) {

        EditText login = findViewById(R.id.loginEditText);
        EditText password =  findViewById(R.id.passwordEditText);

        DatabaseReference newPostRef = db.getFirebaseDatabase().getReference().child("users").push();
        User user = new User(newPostRef.getKey(), login.getText().toString(), password.getText().toString());
        newPostRef.setValue(user);

        startActivity(new Intent(this, MainActivity.class));
    }

}
