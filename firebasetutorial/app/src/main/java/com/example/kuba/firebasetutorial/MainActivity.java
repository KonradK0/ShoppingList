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
                                StartView();
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

    void StartView() {
        startActivity(new Intent(this, LoggedInScreen.class));
    }

    public void onClickRegister(View view) {
        System.out.println("usercounter: " + userCounter);
        startActivity(new Intent(this, RegisterScreen.class));
    }

    //    private ListView mUserList;
//
//    private ArrayList<String> mUsernames = new ArrayList<>();
//    private ArrayList<String> mKeys = new ArrayList<>();
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.registration_view);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        mUserList = (ListView) findViewById(R.id.user_list);
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);
//        mUserList.setAdapter(arrayAdapter);
//
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String value = dataSnapshot.getValue(String.class);
//
//                mUsernames.add(value);
//
//                String key = dataSnapshot.getKey();
//                mKeys.add(key);
//
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                String value = dataSnapshot.getValue(String.class);
//                String key = dataSnapshot.getKey();
//                int index = mKeys.indexOf(key);
//
//                mUsernames.set(index, value);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    //    private Button mFirebaseBtn;
//    private EditText mNameField;
//    private EditText loginField;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.registration_view);
//
//        mFirebaseBtn = (Button) findViewById(R.id.firebase_btn);
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        mNameField = (EditText) findViewById(R.id.name_field);
//        loginField = (EditText) findViewById(R.id.email_field);
//
//        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 1 - Create a child in root object
//                // 2 - Assign some value to that child object
//                String name = mNameField.getText().toString().trim();
//                String email = loginField.getText().toString().trim();
//
//                HashMap<String, String> dataMap = new HashMap<>();
//                dataMap.put("Name", name);
//                dataMap.put("Email", email);
//
//                databaseReference.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()) {
//                            Toast.makeText(MainActivity.this, "Stored...", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(MainActivity.this, "Error...", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });
//    }
}
