package com.example.kuba.firebasetutorial.database;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Konrad on 19.01.2018.
 */

public interface Database {

    int getUserCount();
    int getListsCount();
    int incrementUserCounter();

    FirebaseDatabase getFirebaseDatabase();
}
