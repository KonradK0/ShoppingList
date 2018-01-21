package com.example.kuba.firebasetutorial.database;

import android.util.Log;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.main_activity.MainActivityController;
import com.example.kuba.firebasetutorial.messages_screen.MessagesScreenView;
import com.example.kuba.firebasetutorial.write_new_message_screen.WriteNewMessageScreenController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
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

//

    @Override
    public int getListsCount() {
        ListCountVEL listCountVEL = new ListCountVEL();
        firebaseDatabase.getReference().child("numberoflists").addValueEventListener(listCountVEL);
        return listCountVEL.getListCount();
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
