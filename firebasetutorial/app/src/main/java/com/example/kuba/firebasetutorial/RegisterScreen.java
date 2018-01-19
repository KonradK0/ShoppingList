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

        ArrayList<ShoppingList> arrayList = new ArrayList<>();
        ArrayList<Product> productArrayList = new ArrayList<>();
        Product product = new Product("-1", "xyz");
        productArrayList.add(product);
        ShoppingList shoppingList = new ShoppingList("-1", "lista_xyz", productArrayList);
        arrayList.add(shoppingList);

        User user = new User(login.getText().toString(), password.getText().toString(), arrayList);
        newPostRef.setValue(user);

        startActivity(new Intent(this, MainActivity.class));
    }

}
