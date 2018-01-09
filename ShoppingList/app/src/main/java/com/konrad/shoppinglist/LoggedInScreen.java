package com.konrad.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoggedInScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_screen);
        LinearLayout searched = (LinearLayout) findViewById(R.id.searchedListView);
        //TODO pobranie z bazy co trzeba
        int responseSize = 0;
        for(int i = 0; i < responseSize; i++) {
            LayoutInflater.from(this).inflate(R.layout.list_row, searched, true);
            LinearLayout layout = (LinearLayout) searched.getChildAt(0);
            CheckBox box = (CheckBox) layout.getChildAt(0);
            box.setChecked(false);
            TextView view = (TextView) layout.getChildAt(1);
            String responseFromDB = "";
            view.setText(responseFromDB);
        }
        // TODO to samo z już dodanymi produktami
    }
}
