package com.konrad.shoppinglist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konrad.shoppinglist.database.AppDatabase;
import com.konrad.shoppinglist.database.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchProductsScreen extends AppCompatActivity {

    EditText searchText;
    LinearLayout found;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_screen);
        searchText = findViewById(R.id.search_text);
        found = findViewById(R.id.search_lin_layout);
        db = AppDatabase.getInstance(getApplicationContext());
        setSearchOnTextChanged();
    }

    public void setFoundProductBox(String productName, int childIndex){
        LinearLayout productBox = inflateProductBox(productName, childIndex);
        setOnProductBoxOnClick(productBox);
    }

    private LinearLayout inflateProductBox(String productName, int childIndex){
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_product_box, found, true);
        LinearLayout productBox = (LinearLayout) found.getChildAt(childIndex);
        CheckBox box = (CheckBox) productBox.getChildAt(0);
        box.setChecked(false);
        TextView view = (TextView) productBox.getChildAt(1);
        view.setText(productName);
        return productBox;
    }

    private void setOnProductBoxOnClick(LinearLayout productBox){
        productBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO dodanie do listy zakupów i wyszukiwanego produktu do bazy
            }
        });
    }

    private class getProductsAsyncTask extends AsyncTask<String, Void, List<Product>>{

        @Override
        protected List<Product> doInBackground(String... strings) {
            List<Product> products = new ArrayList<>();
            products.add(new Product(0,0, strings[0]));
            products.addAll(Arrays.asList(db.productDao().findProductsLike(strings[0])));
            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            int i = 0;
            for (Product product : products) {
                setFoundProductBox(product.getName(), i++);
            }
        }
    }

    public void setSearchOnTextChanged(){
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                found.removeAllViews();
                new getProductsAsyncTask().execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
