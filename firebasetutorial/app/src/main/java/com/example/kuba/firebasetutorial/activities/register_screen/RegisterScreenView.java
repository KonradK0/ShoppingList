package com.example.kuba.firebasetutorial.activities.register_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.Product;
import com.example.kuba.firebasetutorial.R;
import com.example.kuba.firebasetutorial.ShoppingList;
import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.example.kuba.firebasetutorial.activities.main_activity.MainActivityView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


/**
 * Created by Kuba on 17/01/2018.
 */

public class RegisterScreenView extends AppCompatActivity {
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_view);
        db = FireDatabase.getInstance();
    }

    public void onClickRegister(View view) {
        EditText login = findViewById(R.id.loginEditText);
        EditText password = findViewById(R.id.passwordEditText);
        db.addNewUser(login.getText().toString(), password.getText().toString());
        startActivity(new Intent(this, MainActivityView.class));
    }

}
