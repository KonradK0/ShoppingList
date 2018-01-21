package com.example.kuba.firebasetutorial.database;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.activities.all_products_from_database.AllProductsFromDatabaseView;
import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenControler;
import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenView;
import com.example.kuba.firebasetutorial.activities.main_activity.MainActivityController;
import com.example.kuba.firebasetutorial.activities.messages_screen.MessagesScreenView;
import com.example.kuba.firebasetutorial.activities.search_product_screen.SearchProductScreenController;
import com.example.kuba.firebasetutorial.activities.search_product_screen.SearchProductsScreenView;
import com.example.kuba.firebasetutorial.activities.write_new_message_screen.WriteNewMessageScreenController;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Konrad on 19.01.2018.
 */

public interface Database {

    void addNewUser(String login, String password);
    void addNewList(LoggedInScreenView view, String uid, String listName, final int listsCount, String login);
    void getAllLists(final LoggedInScreenView view, final LoggedInScreenControler controler, final String userId);
    void getAllProducts(AllProductsFromDatabaseView view);
    void addProductToList(String userId, String productName, String key);
    void removeProductFromList(String productName, String listKey);
    void getProductsFromList(final SearchProductsScreenView view, final String listKey);
    void addOwnProduct(final SearchProductScreenController controller,  final String productName, final String uid, final String listKey);
    void addNewOwner(final SearchProductsScreenView view, String listKey, final String login);
    void getUsersMessages(final MessagesScreenView view, String uid, String direction, final String login, final Map<String, ArrayList<Message>> userMessages);
    void sendMessage(final WriteNewMessageScreenController controller, final String recipentName, final String uid, final String login, final String text);
    void checkCredentials(final MainActivityController controller, final String login, final String password);
}
