package com.konrad.shoppinglist;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.konrad.shoppinglist.database.AppDatabase.database;

import com.konrad.shoppinglist.database.AppDatabase;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
    }

    public void onClickLogIn(View view){
        Intent intent = new Intent(this, LoggedInScreen.class);
        startActivity(intent);
    }

    public void onClickNoAccount(View view){
        Intent intent = new Intent(this, RegisterScreen.class);
        startActivity(intent);
    }
}
