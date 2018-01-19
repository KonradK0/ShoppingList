package com.example.kuba.firebasetutorial;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 17/01/2018.
 */

public class User implements Serializable{
    //String uid;
    String login;
    String password;
    List<ShoppingList> shoppingLists;

    public User( String login, String password, List<ShoppingList> shoppingLists) {
        this.login = login;
        this.password = password;
        this.shoppingLists = shoppingLists;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }


}
