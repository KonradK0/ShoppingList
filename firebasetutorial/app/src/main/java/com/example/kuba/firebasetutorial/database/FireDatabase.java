package com.example.kuba.firebasetutorial.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Konrad on 19.01.2018.
 */

public final class FireDatabase implements Database {

    private static FireDatabase instance;
    private static FirebaseDatabase firebaseDatabase;
    private static int userCounter = getInstance().getUserCount();

    private FireDatabase() {
        instance = new FireDatabase();
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
    }

    @Override
    public int getUserCount() {
        firebaseDatabase.getReference().child("numberofusers").addValueEventListener(new ValueEventListener() {
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
        return userCounter;
    }

    @Override
    public int getListsCount() {
        ListCountVEL listCountVEL = new ListCountVEL();
        firebaseDatabase.getReference().child("numberoflists").addValueEventListener(listCountVEL);
        return listCountVEL.getListCount();
    }


    @Override
    public int incrementUserCounter() {
        System.out.println("GETUSERCOUNTER: " + userCounter);
        firebaseDatabase.getReference().child("numberofusers").setValue(getUserCount() + 1);
        userCounter++;
        return userCounter;
    }

    public static FireDatabase getInstance() {
        if (instance == null) {
            instance = new FireDatabase();
        }
        return instance;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }
}
