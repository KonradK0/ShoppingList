package com.example.kuba.firebasetutorial.activities.logged_in_screen;

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

import com.example.kuba.firebasetutorial.Product;
import com.example.kuba.firebasetutorial.R;
import com.example.kuba.firebasetutorial.ShoppingList;
import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.activities.SearchProductsScreen;
import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.example.kuba.firebasetutorial.activities.main_activity.MainActivityView;
import com.example.kuba.firebasetutorial.activities.messages_screen.MessagesScreenView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoggedInScreenView extends AppCompatActivity {
    CardView newListCardView;
    LinearLayout allLists;
    LoggedInScreenControler controler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_view);
        allLists = findViewById(R.id.all_lists_layout);
        newListCardView = findViewById(R.id.new_list_card_view);
        controler = new LoggedInScreenControler(this);
        setNewListCardViewListener();
        getAllShoppingLists();
    }

    public void onClickMessages(View view) {
        controler.startNewMessagesActivity();
    }

    public void onClickLogOut(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivityView.class));
    }

    private void showAddListPopup(View parent) {
        final CardView popUpCardView = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_list_popup_view, null, false);
        final LinearLayout popUpLinLayout = (LinearLayout) popUpCardView.getChildAt(0);
        Button button = (Button) popUpLinLayout.getChildAt(1);
        final PopupWindow popup = new PopupWindow(popUpLinLayout, 300,
                580, true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText listNameText = (EditText) popUpLinLayout.getChildAt(0);
                final String listName = listNameText.getText().toString();
                controler.handleAddNewList(listName);
                popup.dismiss();
            }
        });
        popup.setContentView(popUpCardView);
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.setOutsideTouchable(true);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                controler.restartActivity();
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
                controler.startSearchProductsActivity(listName, listId);
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

    public void getAllShoppingLists() {
        controler.handleGetAllLists();
    }
}