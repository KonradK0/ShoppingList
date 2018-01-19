package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class LoggedInScreen extends AppCompatActivity {
    CardView newListCardView;
    LinearLayout allLists;
    Database db;
    DatabaseReference dbListsRef;
    private static long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_view);
        allLists = findViewById(R.id.all_lists_layout);
        newListCardView = findViewById(R.id.new_list_card_view);
        userId = getIntent().getLongExtra("USERID", -1);
        db = FireDatabase.getInstance();
        dbListsRef = db.getFirebaseDatabase().getReference().child("lists");

        setNewListCardViewListener();
        getAllShoppingLists();
    }

    private class addNewListAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String[] strings) {
            DatabaseReference newRef = dbListsRef.push();
            newRef.setValue(new ShoppingList("0", getIntent().getStringExtra("USERID"), new ArrayList<Product>()));
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

    private void setOnExistingListCardViewListener(CardView singleListCardView, final String listName) {
        singleListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchProductsScreen.class);
                intent.putExtra("USERID", userId);
                intent.putExtra("LISTNAME", listName);
                startActivity(intent);
            }
        });
    }


        @Override
        protected List<ShoppingList> doInBackground(Long[] userId) {
           /// to trzeba ogarnac
           /// return Arrays.asList(databaseReference.shoppingListDao().getUsersLists(userId[0]));
            return null;
        }

        @Override
        protected void onPostExecute(List<ShoppingList> shoppingLists) {
            super.onPostExecute(shoppingLists);
            int i = 0;
            for (ShoppingList shoppingList : shoppingLists) {
                setShoppingListBox(shoppingList.getListid(), i++);
            }
        }
    }

    public void setShoppingListBox(String listName, int childIndex) {
        CardView singleListCardView = inflateShoppingListBox(listName, childIndex);
        setOnExistingListCardViewListener(singleListCardView, listName);
    }

    private CardView inflateShoppingListBox(String listName, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.shopping_list_box, allLists, true);
        CardView singleListCardView = (CardView) allLists.getChildAt(childIndex);
        TextView listNameTextView = (TextView) singleListCardView.getChildAt(0);
        listNameTextView.setText(listName);
        return singleListCardView;
    }

    private void getAllShoppingLists() {
//        dbListsRef ;
//        int i = 0;
//        for (ShoppingList shoppingList : shoppingLists) {
//            setShoppingListBox(shoppingList.getListid(), i++);
//        }
    }
}