package com.konrad.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
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
