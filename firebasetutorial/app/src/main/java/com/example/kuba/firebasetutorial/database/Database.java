package com.example.kuba.firebasetutorial.database;

import com.example.kuba.firebasetutorial.User;

/**
 * Created by Konrad on 19.01.2018.
 */

public interface Database {

    int getUserCount();
    int getListsCount();
    User checkCredentials(String name, String password, boolean userFound);
    int incrementUserCounter();
}
