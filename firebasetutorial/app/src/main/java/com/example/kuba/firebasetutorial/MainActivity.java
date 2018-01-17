package com.example.kuba.firebasetutorial;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mRegisterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailField = (EditText) findViewById(R.id.email_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    // Intent user account
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
//        setContentView(R.layout.activity_main);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mUserList = (ListView) findViewById(R.id.user_list);
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);
//        mUserList.setAdapter(arrayAdapter);
//
//        mDatabase.addChildEventListener(new ChildEventListener() {
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
//    private EditText mEmailField;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mFirebaseBtn = (Button) findViewById(R.id.firebase_btn);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mNameField = (EditText) findViewById(R.id.name_field);
//        mEmailField = (EditText) findViewById(R.id.email_field);
//
//        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 1 - Create a child in root object
//                // 2 - Assign some value to that child object
//                String name = mNameField.getText().toString().trim();
//                String email = mEmailField.getText().toString().trim();
//
//                HashMap<String, String> dataMap = new HashMap<>();
//                dataMap.put("Name", name);
//                dataMap.put("Email", email);
//
//                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
