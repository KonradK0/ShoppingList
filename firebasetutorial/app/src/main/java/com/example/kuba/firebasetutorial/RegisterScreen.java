package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.Example;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Kuba on 17/01/2018.
 */

public class RegisterScreen extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_view);
        db = FireDatabase.getInstance();
    }

    public void onClickRegister(View view) {
        EditText login = findViewById(R.id.loginEditText);
        EditText password =  findViewById(R.id.passwordEditText);
        DatabaseReference newPostRef = db.getFirebaseDatabase().getReference().child("users").push();

        ArrayList<Message> messageArrayList = new ArrayList<>();
        Message message = new Message("dddd", "d", "to jest d");
        messageArrayList.add(message);
        ArrayList<ShoppingList> arrayList = new ArrayList<>();
        ArrayList<Product> productArrayList = new ArrayList<>();
        Product product = new Product("-1", "xyz");
        Product product1 = new Product("-2", "abc");
        productArrayList.add(product);
        productArrayList.add(product1);
        ShoppingList shoppingList = new ShoppingList("-1", "lista_xyz", productArrayList);
        ShoppingList shoppingList1 = new ShoppingList("-2", "lista_abc", productArrayList);
        arrayList.add(shoppingList);

        User user = new User(login.getText().toString(), password.getText().toString(), arrayList, messageArrayList);
        newPostRef.setValue(user);

        startActivity(new Intent(this, MainActivity.class));
    }

}
