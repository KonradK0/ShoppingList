package com.example.kuba.firebasetutorial.activities.all_products_from_database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuba.firebasetutorial.R;

/**
 * Created by Kuba on 20/01/2018.
 */

public class AllProductsFromDatabaseView extends AppCompatActivity {

    LinearLayout found;
    AllProductsFromDatabaseController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_products);
        found = findViewById(R.id.search_lin_layout);
        controller = new AllProductsFromDatabaseController(this);
        controller.handleAllGetProducts();
    }

    public void setFoundProductBox(String productName, int childIndex) {
        LinearLayout productBox = inflateProductBox(productName, childIndex);
        setOnProductBoxOnClick(productBox);
    }

    private LinearLayout inflateProductBox(String productName, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_product_box, found, true);
        LinearLayout productBox = (LinearLayout) found.getChildAt(childIndex);
        CheckBox box = (CheckBox) productBox.getChildAt(0);
        box.setChecked(false);
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
                if (checkBox.isChecked()) {
                    addProductToList(productName);
                }
            }
        });
    }

    public void addProductToList(String productName){
        controller.handleAddProductToList(productName);
    }

    public void onClickUpdateAndReturnToList(View view) {
        controller.updateAndRestartActivity();
    }
}
