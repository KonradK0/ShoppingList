package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Database db;
    private EditText loginField;
    private EditText passwordField;
    public boolean userFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("THERE WE FUCKIN 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_view);
        db = FireDatabase.getInstance();
        loginField = findViewById(R.id.loginEditText);
        passwordField = findViewById(R.id.pwEditText);
        userFound = false;
    }

    public void onClickLogin(View view) {
        userFound = false;
        db.getFirebaseDatabase().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!userFound) {
                    System.out.println("THERE WE FUCKIN 2");
                    Log.e("Count ", "" + snapshot.getChildrenCount());
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        Log.e("Get login", user.getLogin());
                        if (loginField.getText().toString().equals(user.getLogin())) {
                            if (passwordField.getText().toString().equals(user.getPassword())) {
                                Log.e("PRZED CREDENTIALS", Boolean.valueOf(userFound).toString());
                                userFound = true;
                                Log.e("PO CREDENTIALS", Boolean.valueOf(userFound).toString());
                                Log.e("Get password", user.getPassword());
                                startView(user.getUid());
                                break;
                            }
                        }
                        Log.e("Get uid", user.getUid());
                    }
                    if(!userFound) {
                        Toast.makeText(getApplicationContext(), "USER NOT FOUND", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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