package com.example.kuba.firebasetutorial.all_products_from_database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuba.firebasetutorial.Product;
import com.example.kuba.firebasetutorial.R;
import com.example.kuba.firebasetutorial.SearchProductsScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Kuba on 20/01/2018.
 */

public class AllProductsFromDatabaseView extends AppCompatActivity {

    LinearLayout found;
    DatabaseReference db;
    List<Product> products;
    DatabaseReference listRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_products);
        found = findViewById(R.id.search_lin_layout);
        db = FirebaseDatabase.getInstance().getReference();
        listRef = db.child("users").child(getIntent().getStringExtra("USERID")).child("shoppingLists")
                .child(getIntent().getStringExtra("LISTID")).child("productList");
        getAllProductsFromDatabase();
    }

    private void getAllProductsFromDatabase() {
        DatabaseReference ref = db.child("products");
        ref.addValueEventListener(new ValueEventListener() {
            int childrencounter;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tmp = snapshot.child("productname").getValue().toString();
                    Log.e("child id: ", snapshot.getKey());
                    //products.add(new Product(snapshot.getKey(),snapshot.child("productname").getValue().toString()));
                    Log.e("child productname: ", tmp);
                    setFoundProductBox(tmp, childrencounter);
                    childrencounter++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setFoundProductBox(String productName, int childIndex) {
        LinearLayout productBox = inflateProductBox(productName, childIndex);
        setOnProductBoxOnClick(productBox);
    }

    private LinearLayout inflateProductBox(String productName, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_product_box, found, true);
        LinearLayout productBox = (LinearLayout) found.getChildAt(childIndex);
        CheckBox box = (CheckBox) productBox.getChildAt(0);
        box.setChecked(false);
        TextView view = (TextView) productBox.getChildAt(1);
        Log.e("TEXT: ", view.getText().toString());
        view.setText(productName);
        return productBox;
    }

    private void setOnProductBoxOnClick(final LinearLayout productBox) {
        productBox.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) productBox.getChildAt(0);
                Log.e("Check", String.valueOf(checkBox.isChecked()));
                TextView productNameTextView = (TextView) productBox.getChildAt(1);
                String productName = productNameTextView.getText().toString();
                Log.e("Product Name: ", productName);
                if (checkBox.isChecked()) {
                    addProductToList(productName, listRef);
                }
                //TODO dodanie do listy zakupów i wyszukiwanego produktu do bazy
            }
        });
    }

    public void addProductToList(final String productName, final DatabaseReference listRef) {
        listRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int childrencounter;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                boolean found = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tmp = snapshot.child("productname").getValue().toString();
                    Log.e("child id: ", snapshot.getKey());
                    //products.add(new Product(snapshot.getKey(),snapshot.child("name").getValue().toString()));
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

    public void onClickUpdateAndReturnToList(View view) {
        Intent intent = new Intent(this, SearchProductsScreen.class);
        intent.putExtra("LOGIN", getIntent().getStringExtra("LOGIN"));
        intent.putExtra("USERID", getIntent().getStringExtra("USERID"));
        intent.putExtra("LISTID", getIntent().getStringExtra("LISTID"));
        intent.putExtra("LISTNAME", getIntent().getStringExtra("LISTNAME"));

        startActivity(intent);
    }
}
