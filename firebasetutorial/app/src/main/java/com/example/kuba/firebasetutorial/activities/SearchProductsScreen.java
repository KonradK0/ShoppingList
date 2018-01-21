package com.example.kuba.firebasetutorial.activities;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.Product;
import com.example.kuba.firebasetutorial.R;
import com.example.kuba.firebasetutorial.ShoppingList;
import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.activities.all_products_from_database.AllProductsFromDatabaseView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchProductsScreen extends AppCompatActivity {

    LinearLayout found;
    DatabaseReference db;
    DatabaseReference listRef;
    DatabaseReference copyListRef;
    ShoppingList currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_list);
        TextView x = findViewById(R.id.listNameTextView);
        x.setText(getIntent().getStringExtra("LISTNAME"));
        // searchText = findViewById(R.id.search_text);
        found = findViewById(R.id.search_lin_layout);
        db = FirebaseDatabase.getInstance().getReference();
        listRef = db.child("users").child(getIntent().getStringExtra("USERID")).child("shoppingLists")
                .child(getIntent().getStringExtra("LISTID")).child("productList");
        copyListRef = db.child("users").child(getIntent().getStringExtra("USERID")).child("shoppingLists")
                .child(getIntent().getStringExtra("LISTID"));
        copyListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentList = dataSnapshot.getValue(ShoppingList.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getProductsFromList();
        Button addProductBtn = findViewById(R.id.addProductBtnId);
        setNewProductListener(addProductBtn);
        Button addOwnersBtn = findViewById(R.id.addOwnersId);
        setNewOwnerListener(addOwnersBtn);
    }

    private void setNewOwnerListener(Button addOwnersBtn) {
        addOwnersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddOwnerPopup(v.getRootView());
            }
        });
    }

    private void setNewProductListener(Button addProductBtn) {
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddListPopup(v.getRootView());
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
        box.setChecked(true);
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
                if (!checkBox.isChecked()) {
                    removeProductFromList(productName);
                    productBox.removeAllViewsInLayout();
                }
            }
        });
    }

    private void removeProductFromList(final String productName) {
        listRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int childrencounter;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tmp = snapshot.child("productname").getValue().toString();
                    Log.e("child id: ", snapshot.getKey());
                    //products.add(new Product(snapshot.getKey(),snapshot.child("name").getValue().toString()));
                    Log.e("child name: ", tmp);
                    if (tmp.equals(productName)) {
                        snapshot.getRef().removeValue();
                        Log.e("UWAGA:", "product deleted");
                        break;
                    }
                    childrencounter++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getProductsFromList() {
        listRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int childrencounter;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrencounter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.e("child name: ", snapshot.child("productname").getValue().toString());
                    if (childrencounter >= 1) {
                        setFoundProductBox(String.valueOf(snapshot.child("productname").getValue()), childrencounter - 1);
                    }
                    childrencounter++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onClickShowAllProductsFromDatabase(View view) {
        Intent intent = new Intent(getApplicationContext(), AllProductsFromDatabaseView.class);
        putExtrasAndStartActivity(intent);
    }

    public void onClickGoBackToListView(View view) {
        Intent intent = new Intent(getApplicationContext(), LoggedInScreen.class);
        putExtrasAndStartActivity(intent);
    }

    private void showAddOwnerPopup(View parent) {
        final CardView popUpCardView = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_owner_popup_view, null, false);
        final LinearLayout popUpLinLayout = (LinearLayout) popUpCardView.getChildAt(0);
        Button button = (Button) popUpLinLayout.getChildAt(1);
        final PopupWindow popup = new PopupWindow(popUpLinLayout, 600,
                400, true);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Intent intent = new Intent(getApplicationContext(), SearchProductsScreen.class);
                putExtrasAndStartActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userLoginText = (EditText) popUpLinLayout.getChildAt(0);
                final String userLogin = userLoginText.getText().toString();
                new addNewOwnerAsyncTask().execute(userLogin);
                popup.dismiss();
            }
        });
        popup.setContentView(popUpCardView);
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.setOutsideTouchable(true);

        parent.getBackground().setColorFilter(getResources().getColor(R.color.cardview_dark_background), PorterDuff.Mode.DARKEN);
        popup.showAtLocation(parent, Gravity.CENTER_HORIZONTAL, 10, 10);
    }

    private void showAddListPopup(View parent) {
        final CardView popUpCardView = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_product_popup_view, null, false);
        final LinearLayout popUpLinLayout = (LinearLayout) popUpCardView.getChildAt(0);
        Button button = (Button) popUpLinLayout.getChildAt(1);
        final PopupWindow popup = new PopupWindow(popUpLinLayout, 600,
                400, true);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Intent intent = new Intent(getApplicationContext(), SearchProductsScreen.class);
                putExtrasAndStartActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText listNameText = (EditText) popUpLinLayout.getChildAt(0);
                final String productName = listNameText.getText().toString();
                new addNewProductAsyncTask().execute(productName);
                popup.dismiss();
            }
        });
        popup.setContentView(popUpCardView);
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.setOutsideTouchable(true);

        parent.getBackground().setColorFilter(getResources().getColor(R.color.cardview_dark_background), PorterDuff.Mode.DARKEN);
        popup.showAtLocation(parent, Gravity.CENTER_HORIZONTAL, 10, 10);
    }

    private class addNewProductAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(final String[] strings) {
            final DatabaseReference newListRef = db.child("products");
            newListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int childrenCount = 0;
                    DatabaseReference lastChildRef = newListRef.child(String.valueOf(dataSnapshot.getChildrenCount()));
                    boolean productExists = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        System.out.println("strings[0]: " + strings[0] + "  " + String.valueOf(snapshot.child("productname").getValue()));
                        if (strings[0].equals(String.valueOf(snapshot.child("productname").getValue()))) {
                            productExists = true;
                            break;
                        }
                        childrenCount++;
                    }
                    if (!productExists) {
                        lastChildRef.setValue(new Product(strings[0]));
                    }
                    new AllProductsFromDatabaseView().addProductToList(strings[0], listRef);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return true;
        }
    }

    private void putExtrasAndStartActivity(Intent intent) {
        intent.putExtra("LOGIN", getIntent().getStringExtra("LOGIN"));
        intent.putExtra("USERID", getIntent().getStringExtra("USERID"));
        intent.putExtra("LISTID", getIntent().getStringExtra("LISTID"));
        intent.putExtra("LISTNAME", getIntent().getStringExtra("LISTNAME"));
        intent.putExtra("MESSAGECOUNT", getIntent().getStringExtra("MESSAGECOUNT"));
        startActivity(intent);
    }

    private class addNewOwnerAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(final String[] strings) {
            final DatabaseReference newListRef = db.child("users");
            newListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean userExists = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //System.out.println("strings[0]: " + strings[0] + "  " + String.valueOf(snapshot.child("productname").getValue()));
                        String key = snapshot.getKey();
                        User user = snapshot.getValue(User.class);
                        Log.e("String.valueOf(snapshot.child(key).child(\"login\").getValue()): ", user.getLogin());
                        if (strings[0].equals(String.valueOf(user.getLogin()))) {
                            DataSnapshot addListSnapshot = snapshot.child("shoppingLists");
                            DatabaseReference addListRef = snapshot.child("shoppingLists").getRef();
                            addListRef.child(String.valueOf(addListSnapshot.getChildrenCount())).setValue(currentList);
                            userExists = true;
                            break;
                        }
                    }
                    if (!userExists) {
                        Toast toast = Toast.makeText(getApplicationContext(), "No " + strings[0] + " user in DB.", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), strings[0] + " user added as an owner", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    new AllProductsFromDatabaseView().addProductToList(strings[0], listRef);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return true;
        }
    }
}
