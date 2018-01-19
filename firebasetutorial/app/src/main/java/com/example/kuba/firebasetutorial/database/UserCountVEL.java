package com.example.kuba.firebasetutorial.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Konrad on 19.01.2018.
 */

public class UserCountVEL implements ValueEventListener {
    private int userCount;

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        try {
            if (dataSnapshot.getValue() != null) {
                try {
                    String str = dataSnapshot.getValue().toString(); // your name values you will get here
                    userCount = Integer.parseInt(str);
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

    public int getUserCount(){
        return userCount;
    }
}
