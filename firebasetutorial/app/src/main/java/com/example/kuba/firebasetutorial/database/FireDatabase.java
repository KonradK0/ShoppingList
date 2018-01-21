package com.example.kuba.firebasetutorial.database;

import android.util.Log;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.Product;
import com.example.kuba.firebasetutorial.ShoppingList;
import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.activities.Owner;
import com.example.kuba.firebasetutorial.activities.all_products_from_database.AllProductsFromDatabaseView;
import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenControler;
import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenView;
import com.example.kuba.firebasetutorial.activities.main_activity.MainActivityController;
import com.example.kuba.firebasetutorial.activities.messages_screen.MessagesScreenView;
import com.example.kuba.firebasetutorial.activities.search_product_screen.SearchProductScreenController;
import com.example.kuba.firebasetutorial.activities.search_product_screen.SearchProductsScreenView;
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
    public void addNewUser(String login, String password) {
        DatabaseReference newPostRef = firebaseDatabase.getReference().child("users").push();
        User user = new User(login, password, new ArrayList<ShoppingList>(), new ArrayList<Message>(), new ArrayList<Message>());
        newPostRef.setValue(user);
    }

    @Override
    public void addNewList(LoggedInScreenView view, String uid, String listName, final int listsCount, String login) {
        DatabaseReference newListRef = firebaseDatabase.getReference().child("lists").push();
        List<Product> productList = new ArrayList<>();
        System.out.println("NEW LIST REF KEY: " + newListRef.getKey());
        ArrayList<Owner> ownerArrayList = new ArrayList<>();
        ownerArrayList.add(new Owner(login));
        newListRef.setValue(new ShoppingList(listName, productList, ownerArrayList));
        view.getAllShoppingLists();
    }

    @Override
    public void getAllLists(final LoggedInScreenView view, final LoggedInScreenControler controler, final String userLogin) {
        DatabaseReference fastRef = firebaseDatabase.getReference().child("lists").getRef();
        fastRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int childrenCount = 0;
                //lists
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    DataSnapshot dsp1 = dsp.child("owners");
                    boolean userExists = false;
                    // owners
                    for (DataSnapshot dsp2 : dsp1.getChildren()) {
                        if (dsp2.getValue(Owner.class).getLogin().equals(userLogin)) {
                            userExists = true;
                            break;
                        }
                    }
                    if (userExists) {
                        view.setShoppingListBox(String.valueOf(dsp.child("name").getValue()), dsp.getKey(), childrenCount);
                        Log.e("shoppinglistclass name: ", String.valueOf(dsp.child("name").getValue()));
                        childrenCount++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getAllProducts(final AllProductsFromDatabaseView view) {
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
    public void addProductToList(final String userId, final String productName, final String key) {
        final DatabaseReference listRef = firebaseDatabase.getReference().child("lists").child(key).child("productList");
        listRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int childrencounter;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                boolean found = false;
                //products
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String productFromListName = snapshot.child("productname").getValue().toString();
                    Log.e("child id: ", snapshot.getKey());
                    Log.e("child name: ", productFromListName);
                    if (productFromListName.equals(productName)) {
                        found = true;
                        break;
                    }
                    childrencounter++;
                }
                if (!found) {
                    final DatabaseReference counterRef = firebaseDatabase.getReference().child("lists").child(key).child("numberOfAddedProducts").getRef();
                    final StringBuilder stringBuilder = new StringBuilder();
                    counterRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            stringBuilder.append(dataSnapshot.getValue(String.class));
                            int number = Integer.parseInt(stringBuilder.toString());
                            counterRef.setValue(String.valueOf(number +1));
                            Product newProduct = new Product(stringBuilder.toString(), productName);
                            listRef.child(stringBuilder.toString()).setValue(newProduct);
                            Log.e("UWAGA:", "new product added");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void removeProductFromList(final String productName, String listKey) {
        DatabaseReference removeRef = firebaseDatabase.getReference().child("lists").child(listKey).child("productList");
        removeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //products
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String productNameFromList = snapshot.child("productname").getValue().toString();
                    Log.e("child id: ", snapshot.getKey());
                    //products.add(new Product(snapshot.getKey(),snapshot.child("name").getValue().toString()));
                    Log.e("child name: ", productNameFromList);
                    if (productNameFromList.equals(productName)) {
                        snapshot.getRef().removeValue();
                        Log.e("UWAGA:", "product deleted");
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getProductsFromList(final SearchProductsScreenView view, final String listKey) {
        DatabaseReference getRef = firebaseDatabase.getReference().child("lists").child(listKey).child("productList");
        getRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int childrencounter;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                //products
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.e("child name: ", snapshot.child("productname").getValue().toString());
                    view.setFoundProductBox(String.valueOf(snapshot.child("productname").getValue()), childrencounter);
                    childrencounter++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addOwnProduct(final SearchProductScreenController controller, final String productName, final String uid, final String listKey) {
        final DatabaseReference newListRef = firebaseDatabase.getReference().child("products");
        newListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int childrenCount = 0;
                DatabaseReference lastChildRef = newListRef.child(String.valueOf(dataSnapshot.getChildrenCount()));
                boolean productExists = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("strings[0]: " + productName + "  " + String.valueOf(snapshot.child("productname").getValue()));
                    if (productName.equals(String.valueOf(snapshot.child("productname").getValue()))) {
                        productExists = true;
                        break;
                    }
                    childrenCount++;
                }
                if (!productExists) {
                    lastChildRef.setValue(new Product(productName));
                }
                addProductToList(uid, productName, listKey);
                controller.restartActivity();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addNewOwner(final SearchProductsScreenView view, String listKey, final String login) {
        final DatabaseReference newListRef = firebaseDatabase.getReference().child("lists").child(listKey).child("owners");
        newListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean userExists = false;
                //Owners
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Owner owner = snapshot.getValue(Owner.class);
                    Log.e("String.valueOf(snapshot.child(key).child(\"login\").getValue()): ", owner.getLogin());
                    if (login.equals(String.valueOf(owner.getLogin()))) {
                        userExists = true;
                        break;
                    }
                }
                if (!userExists) {
                    newListRef.child(String.valueOf(dataSnapshot.getChildrenCount())).setValue(new Owner(login));
                    Toast toast = Toast.makeText(view,   login + " added as an owner of this list.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(view, login + " user already was an owner.", Toast.LENGTH_LONG);
                    toast.show();
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
    public void checkCredentials(final MainActivityController controller, final String login, final String password) {
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
