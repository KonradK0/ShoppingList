package com.konrad.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.konrad.shoppinglist.database.AppDatabase;
import com.konrad.shoppinglist.database.User;

import static com.konrad.shoppinglist.database.AppDatabase.database;
public class RegisterScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
    }

    public void onClickRegister(View view){
        EditText loginEdit = findViewById(R.id.editText);
        String login = loginEdit.getText().toString();
        EditText passwordEdit = findViewById(R.id.editText2);
        String password = passwordEdit.getText().toString();

        database.userDao().insertUser(new User(1,"abc", "def"));
        database.userDao().insertUser(new User(2,"ghi", "jkl"));

        TextView loginTextView = findViewById(R.id.textView);
        loginTextView.setText(database.userDao().findByLogin("abc").getLogin());

    }
}
