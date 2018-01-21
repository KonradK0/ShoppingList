package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.example.kuba.firebasetutorial.main_activity.MainActivityView;
import com.example.kuba.firebasetutorial.messages_screen.MessagesScreenView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoggedInScreen extends AppCompatActivity {
    CardView newListCardView;
    LinearLayout allLists;
    Database db;
    DatabaseReference dbUsersRef;
    DatabaseReference currentRef;
    private static String userId;
    String login;
    long messageCount;
    int childrencount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_view);
        allLists = findViewById(R.id.all_lists_layout);
        newListCardView = findViewById(R.id.new_list_card_view);
        userId = getIntent().getStringExtra("USERID");
        login = getIntent().getStringExtra("LOGIN");
        messageCount = getIntent().getLongExtra("MESSAGECOUNT", -1);
        db = FireDatabase.getInstance();
        dbUsersRef = db.getFirebaseDatabase().getReference().child("users");

        setNewListCardViewListener();
        getAllShoppingLists();
    }

    public void onClickMessages(View view) {
        Intent intent = new Intent(getApplicationContext(), MessagesScreenView.class);
        intent.putExtra("LOGIN", login);
        intent.putExtra("USERID", userId);
        intent.putExtra("MESSAGECOUNT", messageCount);
        startActivity(intent);
    }

    public void onClickLogOut(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivityView.class));
    }

    private class addNewListAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String[] strings) {
            DatabaseReference newListRef = currentRef.child(String.valueOf(childrencount+1));
            List<Product> productList = new ArrayList<>();
            productList.add(new Product("-1", "nic"));
            newListRef.setValue(new ShoppingList(String.valueOf(childrencount + 1), strings[0], productList));
            getAllShoppingLists();
            return true;
        }
    }

    private void showAddListPopup(View parent) {
        final CardView popUpCardView = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_list_popup_view, null, false);
        final LinearLayout popUpLinLayout = (LinearLayout) popUpCardView.getChildAt(0);
        Button button = (Button) popUpLinLayout.getChildAt(1);
        final PopupWindow popup = new PopupWindow(popUpLinLayout, 400,
                580, true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText listNameText = (EditText) popUpLinLayout.getChildAt(0);
                final String listName = listNameText.getText().toString();
                new addNewListAsyncTask().execute(listName);
                popup.dismiss();
            }
        });
        popup.setContentView(popUpCardView);
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.setOutsideTouchable(true);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Intent intent = new Intent(getApplicationContext(), LoggedInScreen.class);
                intent.putExtra("USERID", userId);
                startActivity(intent);
            }
        });
        parent.getBackground().setColorFilter(getResources().getColor(R.color.cardview_dark_background), PorterDuff.Mode.DARKEN);
        popup.showAtLocation(parent, Gravity.CENTER_HORIZONTAL, 10, 10);
    }

    private void setNewListCardViewListener() {
        newListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddListPopup(v.getRootView());
            }
        });
    }

    private void setOnExistingListCardViewListener(CardView singleListCardView, final String listName, final String listId) {
        singleListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchProductsScreen.class);
                intent.putExtra("USERID", userId);
                intent.putExtra("LISTNAME", listName);
                intent.putExtra("LISTID", listId);
                startActivity(intent);
            }
        });
    }

    public void setShoppingListBox(String listName, String listId, int childIndex) {
        CardView singleListCardView = inflateShoppingListBox(listName, childIndex);
        setOnExistingListCardViewListener(singleListCardView, listName, listId);
    }

    private CardView inflateShoppingListBox(String listName, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.shopping_list_box, allLists, true);
        CardView singleListCardView = (CardView) allLists.getChildAt(childIndex);
        TextView listNameTextView = (TextView) singleListCardView.getChildAt(0);
        listNameTextView.setText(listName);
        return singleListCardView;
    }

    private void getAllShoppingLists() {
        dbUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencount = 0;
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    User user = dsp.getValue(User.class);
                    if (dsp.getKey().equals(userId)) {
                        Log.i("znaleziono usera" , " uwaga: " + dsp.getValue(User.class).getLogin());
                        DataSnapshot shoppingListRef = dsp.child("shoppingLists");
                        for(DataSnapshot dspShop : shoppingListRef.getChildren()) {
                            Log.i("znaleziono liste: ", dspShop.getValue(ShoppingList.class).getName());
                            setShoppingListBox(dspShop.getValue(ShoppingList.class).getName(), dspShop.getValue(ShoppingList.class).getListid(), childrencount);
                            childrencount++;
                        }
                        currentRef = dsp.getRef().child("shoppingLists");
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}