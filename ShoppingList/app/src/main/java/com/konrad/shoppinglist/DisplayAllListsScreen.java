package com.konrad.shoppinglist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.konrad.shoppinglist.database.AppDatabase;
import com.konrad.shoppinglist.database.ShoppingList;

public class DisplayAllListsScreen extends AppCompatActivity {
    CardView newListCardView;
    LinearLayout allLists;
    AppDatabase db;
    private long userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_lists_screen);
        allLists = findViewById(R.id.all_lists_layout);
        newListCardView = findViewById(R.id.new_list_card_view);
        db = AppDatabase.getInstance(getApplicationContext());
        userUid = getIntent().getLongExtra("USERUID", -1);
    }

    private class addNewListAsyncTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String [] objects) {
            db.shoppingListDao().insertShoppingList(new ShoppingList(0, userUid, objects[0]));
            return true;
        }
    }

    private void setNewListCardViewListener(){
        newListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setShoppingListBox(){

    }

    private void inflateShoppingListBox(){
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.shopping_list_box, allLists, false);

    }
}
