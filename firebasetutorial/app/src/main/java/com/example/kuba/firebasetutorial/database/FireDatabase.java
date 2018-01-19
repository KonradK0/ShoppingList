package com.example.kuba.firebasetutorial.database;

import android.util.Log;

import com.example.kuba.firebasetutorial.User;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Konrad on 19.01.2018.
 */

public final class FireDatabase implements Database {

    private static FireDatabase instance = new FireDatabase();
    private static FirebaseDatabase firebaseDatabase;

    private FireDatabase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
    }

    @Override
    public int getUserCount() {
        UserCountVEL userCountVEL = new UserCountVEL();
        firebaseDatabase.getReference().child("numberofusers").addValueEventListener(userCountVEL);
        return userCountVEL.getUserCount();
    }

    @Override
    public int getListsCount() {
        ListCountVEL listCountVEL = new ListCountVEL();
        firebaseDatabase.getReference().child("numberoflists").addValueEventListener(listCountVEL);
        return listCountVEL.getListCount();
    }

    @Override
    public User checkCredentials(String name, String password, boolean userFoundPrevious) {
        CredentialsVEL users = new CredentialsVEL(userFoundPrevious, name, password);
        firebaseDatabase.getReference().child("users").addValueEventListener(users);
        if (users.credentialsMatched()) {
            Log.i("credentials: ", "CREDENTIALS MATCHED");
            return users.getUser();
        }
        Log.i("credentials: ", "CREDENTIALS NOT MATCHED");
        return User.empty();
    }

    @Override
    public int incrementUserCounter() {
        int newUserCount = getUserCount() + 1;
        firebaseDatabase.getReference().child("numberofusers").setValue(newUserCount);
        return getUserCount();
    }

    public static FireDatabase getInstance() {
        if(instance == null){
            instance = new FireDatabase();
        }
        return instance;
    }


}
