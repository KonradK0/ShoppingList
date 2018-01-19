package com.example.kuba.firebasetutorial.database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.kuba.firebasetutorial.LoggedInScreen;
import com.example.kuba.firebasetutorial.MainActivity;
import com.example.kuba.firebasetutorial.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Konrad on 19.01.2018.
 */

public class CredentialsVEL implements ValueEventListener  {
    boolean credentialsMatched;
    String name;
    String password;
    User user;

    public CredentialsVEL(boolean credentialsMatched, String name, String password) {
        this.credentialsMatched = credentialsMatched;
        this.name = name;
        this.password = password;
    }

    @Override
    public void onDataChange(DataSnapshot snapshot) {
        if (!credentialsMatched) {
            System.out.println("THERE WE FUCKIN 2");
            Log.e("Count ", "" + snapshot.getChildrenCount());
            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                user = postSnapshot.getValue(User.class);
                Log.e("Get login", user.getLogin());
                if (name.equals(user.getLogin())) {
                    if (password.equals(user.getPassword())) {
                        Log.e("PRZED CREDENTIALS", Boolean.valueOf(credentialsMatched).toString());
                        credentialsMatched = true;
                        Log.e("PO CREDENTIALS", Boolean.valueOf(credentialsMatched).toString());
                        Log.e("Get password", user.getPassword());
                        break;
                    }
                }
                Log.e("Get uid", user.getUid());
            }
        }
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public boolean credentialsMatched() {
        return credentialsMatched;
    }

    public User getUser() {
        return user;
    }
}
