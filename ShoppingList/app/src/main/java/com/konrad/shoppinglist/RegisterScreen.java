package com.konrad.shoppinglist;

import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.konrad.shoppinglist.database.AppDatabase;
import com.konrad.shoppinglist.database.User;

import java.util.concurrent.ExecutionException;

public class RegisterScreen extends AppCompatActivity {

    EditText loginEditText;
    EditText passwordEditText;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        loginEditText = findViewById(R.id.login_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        db = AppDatabase.getInstance(getApplicationContext());
    }

    private class InsertAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                db.userDao().insertUser(new User(0, strings[0], strings[1]));
                return true;
            } catch (SQLException e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if(success){
                Toast.makeText(RegisterScreen.this, R.string.registrationSuccessful, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(RegisterScreen.this, R.string.userExists, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onClickRegister(View view) throws ExecutionException, InterruptedException {
        InsertAsyncTask task = new InsertAsyncTask();
        task.execute(loginEditText.getText().toString(), passwordEditText.getText().toString());
    }

    //Tylko do debugu, wypisuje (w logu) loginy wszystkich userów
    public void onClickTestButton(View view) {
        AsyncTask findTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                //User found = instance.userDao().findByLogin("abc");
                User[] users = db.userDao().getAllUsers();
                for (User u : users) {
                    Log.i("Login : haslo", u.getLogin() + " : " + u.getPassword());
                }
                return users;
            }

            @Override
            protected void onPostExecute(Object o) {

                //loginTextView.setText(found.getLogin());
            }
        };
        findTask.execute();
    }
}
