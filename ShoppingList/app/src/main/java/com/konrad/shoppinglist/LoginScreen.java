package com.konrad.shoppinglist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.konrad.shoppinglist.database.AppDatabase;
import com.konrad.shoppinglist.database.User;

import java.io.File;

public class LoginScreen extends AppCompatActivity {
    EditText loginEdit;
    EditText passwordEdit;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        loginEdit = findViewById(R.id.login_text);
        passwordEdit = findViewById(R.id.password_text);
        db = AppDatabase.getInstance(getApplicationContext());
    }

    private class logInAsyncTask extends AsyncTask <String, Void, User>{

        @Override
        protected User doInBackground(String... strings) {
            User user = db.userDao().findByLogin(strings[0]);
            return user == null ? User.empty() : user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if(user.isEmpty()){
                Toast.makeText(LoginScreen.this, R.string.wrongUsername, Toast.LENGTH_LONG).show();
                return;
            }
            if(!user.getPassword().equals(passwordEdit.getText().toString())) {
                Toast.makeText(LoginScreen.this, R.string.wrongPassword, Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(LoginScreen.this, DisplayAllListsScreen.class);
            intent.putExtra("USERID", user.getUid());
            startActivity(intent);
        }
    }

    public void onClickLogIn(View view){
        new logInAsyncTask().execute(loginEdit.getText().toString());
    }

    public void onClickNoAccount(View view){
        startActivity(new Intent(this, RegisterScreen.class));
    }
}
