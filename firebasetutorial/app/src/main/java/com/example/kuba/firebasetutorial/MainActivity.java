package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private EditText loginField;
    private EditText passwordField;
    private Button registerBtn;

    public static int userCounter;

    boolean userFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("THERE WE FUCKIN 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_view);
        //userFound = false;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("numberofusers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            String str = dataSnapshot.getValue().toString(); // your name values you will get here
                            userCounter = Integer.parseInt(str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("tag is null");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onClickLogin(View view) {
        userFound = false;
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!userFound) {
                    System.out.println("THERE WE FUCKIN 2");
                    loginField = findViewById(R.id.loginEditText);
                    passwordField = findViewById(R.id.pwEditText);
                    registerBtn = findViewById(R.id.register_btn);
                    Log.e("Count ", "" + snapshot.getChildrenCount());
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        if (loginField.getText().toString().equals(user.login)) {
                            if (passwordField.getText().toString().equals(user.password)) {
                                userFound = true;
                                StartView(user.uid);
                                break;
                            }
                        }
                        Log.e("Get login", user.login);
                        Log.e("Get password", user.password);
                        Log.e("Get uid", user.uid);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
    }

    void StartView(String user) {
        Intent intent = new Intent(this, LoggedInScreen.class);
        intent.putExtra("USERID", user);
        startActivity(intent);
    }

    public void onClickRegister(View view) {
        System.out.println("usercounter: " + userCounter);
        startActivity(new Intent(this, RegisterScreen.class));
    }
}