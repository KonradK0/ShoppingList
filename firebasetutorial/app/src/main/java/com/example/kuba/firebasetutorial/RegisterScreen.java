package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.kuba.firebasetutorial.MainActivity.userCounter;

/**
 * Created by Kuba on 17/01/2018.
 */

public class RegisterScreen extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_view);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        myRef = database.getReference();
    }

    public void onClickRegister(View view) {
        userCounter++;
        myRef.child("numberofusers").setValue(userCounter);
        EditText login = findViewById(R.id.loginEditText);
        EditText password =  findViewById(R.id.passwordEditText);

        DatabaseReference newPostRef = ref.push();
        User user = new User(String.valueOf(userCounter), login.getText().toString(), password.getText().toString());
        newPostRef.setValue(user);

        startActivity(new Intent(this, MainActivity.class));
    }

}
