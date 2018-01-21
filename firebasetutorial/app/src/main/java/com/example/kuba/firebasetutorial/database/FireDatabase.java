package com.example.kuba.firebasetutorial.database;

import android.util.Log;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.Product;
import com.example.kuba.firebasetutorial.ShoppingList;
import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.activities.all_products_from_database.AllProductsFromDatabaseModel;
import com.example.kuba.firebasetutorial.activities.all_products_from_database.AllProductsFromDatabaseView;
import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenControler;
import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenView;
import com.example.kuba.firebasetutorial.activities.main_activity.MainActivityController;
import com.example.kuba.firebasetutorial.activities.messages_screen.MessagesScreenView;
import com.example.kuba.firebasetutorial.activities.write_new_message_screen.WriteNewMessageScreenController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 19.01.2018.
 */

public final class FireDatabase implements Database {

    private static FireDatabase instance;
    private static FirebaseDatabase firebaseDatabase;

    private FireDatabase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
    }

    @Override
    public void addNewUser(String login, String password){
        DatabaseReference newPostRef = firebaseDatabase.getReference().child("users").push();
        User user = new User(login, password, new ArrayList<ShoppingList>(), new ArrayList<Message>(), new ArrayList<Message>());
        newPostRef.setValue(user);
    }

    @Override
    public void addNewList(LoggedInScreenView view, String uid, String listName, final int listsCount) {
        DatabaseReference newListRef = firebaseDatabase.getReference().child("users").child(uid).child("shoppingLists").child(String.valueOf(listsCount));
        List<Product> productList = new ArrayList<>();
        newListRef.setValue(new ShoppingList(String.valueOf(listsCount), listName, productList));
        view.getAllShoppingLists();
    }

    @Override
    public void getAllLists(final LoggedInScreenView view, final LoggedInScreenControler controler, final String userId) {
        firebaseDatabase.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int childrenCount = 0;
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    User user = dsp.getValue(User.class);
                    if (dsp.getKey().equals(userId)) {
                        Log.i("znaleziono usera" , " uwaga: " + dsp.getValue(User.class).getLogin());
                        DataSnapshot shoppingListRef = dsp.child("shoppingLists");
                        for(DataSnapshot dspShop : shoppingListRef.getChildren()) {
                            ShoppingList list = dspShop.getValue(ShoppingList.class);
                            Log.i("znaleziono liste: ", list.getName());
                            view.setShoppingListBox(dspShop.getValue(ShoppingList.class).getName(), dspShop.getValue(ShoppingList.class).getListid(), childrenCount);
                            childrenCount++;
                        }
                        break;
                    }
                }
                controler.setListCount(childrenCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllProducts(final AllProductsFromDatabaseView view){
        firebaseDatabase.getReference().child("products").addValueEventListener(new ValueEventListener() {
            int childrencounter;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tmp = snapshot.child("productname").getValue().toString();
                    Log.e("child id: ", snapshot.getKey());
                    //products.add(new Product(snapshot.getKey(),snapshot.child("productname").getValue().toString()));
                    Log.e("child productname: ", tmp);
                    view.setFoundProductBox(tmp, childrencounter);
                    childrencounter++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addProductToList(final String userId, final String productName, final String listId) {
        final DatabaseReference listRef = firebaseDatabase.getReference().child("users").child(userId).child("shoppingLists")
                .child(listId).child("productList");
        listRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int childrencounter;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                boolean found = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tmp = snapshot.child("productname").getValue().toString();
                    Log.e("child id: ", snapshot.getKey());
                    Log.e("child name: ", tmp);
                    if (tmp.equals(productName)) {
                        found = true;
                        break;
                    }
                    childrencounter++;
                }
                if (!found) {
                    Product newProduct = new Product(String.valueOf(childrencounter), productName);
                    listRef.child(String.valueOf(childrencounter)).setValue(newProduct);
                    Log.e("UWAGA:", "new product added");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getUsersMessages(final MessagesScreenView view, String uid, String direction, final String login, final Map<String, ArrayList<Message>> userMessages) {
        final DatabaseReference messages = firebaseDatabase.getReference().child("users")
                .child(uid)
                .child(direction);
        messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    String from = login.equals(message.getFromName()) ? message.getToName() : message.getFromName();
                    if (userMessages.get(from) == null) {
                        userMessages.put(from, new ArrayList<Message>());
                    }
                    userMessages.get(from).add(message);
                }
                view.setupMessageBox();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void sendMessage(final WriteNewMessageScreenController controller, final String recipentName, final String uid, final String login, final String messageText) {
        firebaseDatabase.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getValue(User.class).getLogin().equals(recipentName)) {
                        String recipientUid = postSnapshot.getKey();
                        User recipient = postSnapshot.getValue(User.class);
                        Message message = new Message(uid, login, recipientUid, recipient.getLogin(), messageText, String.valueOf(new Date().getTime()));
                        firebaseDatabase
                                .getReference()
                                .child("users")
                                .child(recipientUid)
                                .child("messagesReceived")
                                .child(String.valueOf(recipient.getMessagesReceivedCount() + 1)).setValue(message);
                        firebaseDatabase
                                .getReference("users")
                                .child(uid)
                                .child("messagesSent")
                                .child(String.valueOf(controller.getMessageCount() + 1))
                                .setValue(message);
                        controller.setMessageCount(controller.getMessageCount() + 1);
                        controller.startMessagesScreenActivity();
                        return;
                    }
                }
                Toast.makeText(controller.getView(), "Podany użytkownik nie istnieje", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void checkCredentials(final MainActivityController controller, final String login, final String password){
        firebaseDatabase.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            boolean userFound = false;
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("THERE WE FUCKIN 2");
                Log.e("Count ", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    String uid = postSnapshot.getKey();
                    Log.e("Get login", user.getLogin());
                    if (login.equals(user.getLogin())) {
                        if (password.equals(user.getPassword())) {
                            controller.setFound(user);
                            Log.e("PRZED CREDENTIALS", Boolean.valueOf(userFound).toString());
                            userFound = true;
                            Log.e("PO CREDENTIALS", Boolean.valueOf(userFound).toString());
                            Log.e("Get password", user.getPassword());
                            controller.startLoggedInView(uid, login);
                            break;
                        }
                    }
                    Log.e("Get uid", uid);
                }
                if (!userFound) {
                    Toast.makeText(controller.getView(), "USER NOT FOUND", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
