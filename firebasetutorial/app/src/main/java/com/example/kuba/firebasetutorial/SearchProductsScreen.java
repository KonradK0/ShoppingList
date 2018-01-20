package com.example.kuba.firebasetutorial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchProductsScreen extends AppCompatActivity {

    EditText searchText;
    LinearLayout found;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_list);
        TextView x = findViewById(R.id.listNameTextView);
        x.setText(getIntent().getStringExtra("LISTNAME"));
        searchText = findViewById(R.id.search_text);
        found = findViewById(R.id.search_lin_layout);
        db = FirebaseDatabase.getInstance().getReference();
        setSearchOnTextChanged();
    }

    public void setFoundProductBox(String productName, int childIndex){
        LinearLayout productBox = inflateProductBox(productName, childIndex);
        setOnProductBoxOnClick(productBox);
    }

    private LinearLayout inflateProductBox(String productName, int childIndex){
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_product_box, found, true);
        LinearLayout productBox = (LinearLayout) found.getChildAt(childIndex);
        CheckBox box = (CheckBox) productBox.getChildAt(0);
        box.setChecked(true);
        TextView view = (TextView) productBox.getChildAt(1);
        Log.e("TEXT: " , view.getText().toString());
        view.setText(productName);
        return productBox;
    }

    private void setOnProductBoxOnClick(LinearLayout productBox){
        productBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO dodanie do listy zakupów i wyszukiwanego produktu do bazy
            }
        });
    }

    private void getProductsFromList(){
        DatabaseReference ref = db.child("users").child(getIntent().getStringExtra("USERID")).child("shoppingLists")
                .child(getIntent().getStringExtra("LISTID")).child("productList");
        ref.addValueEventListener(new ValueEventListener() {
            int childrencounter;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Log.e("child name: ", snapshot.child("name").getValue().toString());
                    setFoundProductBox(String.valueOf(snapshot.child("name").getValue()), childrencounter);
                    childrencounter++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private class getProductsAsyncTask extends AsyncTask<String, Void, List<Product>>{
//
//        @Override
//        protected List<Product> doInBackground(String... strings) {
//            List<Product> products = new ArrayList<>();
//            products.add(new Product("0", "Mleko"));
//            //products.addAll(Arrays.asList(db.productDao().findProductsLike(strings[0])));
//            return products;
//        }
//
//        @Override
//        protected void onPostExecute(List<Product> products) {
//            super.onPostExecute(products);
//            int i = 0;
//            for (Product product : products) {
//                setFoundProductBox(String.valueOf(product.getName()), i++);
//            }
//        }
//    }

    public void setSearchOnTextChanged(){
        getProductsFromList();
        //new getProductsAsyncTask().execute();
        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
//                db = FirebaseDatabase.getInstance().getReference();
//                DatabaseReference ref = db.child("products");
//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        int boxCount = 0;
//                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                            //Log.e("child name: ", snapshot.child("name").getValue().toString());
//                            if (!s.equals("") && String.valueOf(snapshot.child("productname").getValue()).contains(s)) {
//                                setFoundProductBox(String.valueOf(snapshot.child("productname").getValue()), boxCount);
//                                Log.e("PRODUKT", String.valueOf(snapshot.child("productname").getValue()));
//                                boxCount++;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
