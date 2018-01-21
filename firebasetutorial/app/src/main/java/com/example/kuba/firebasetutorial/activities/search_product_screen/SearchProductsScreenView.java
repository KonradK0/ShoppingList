package com.example.kuba.firebasetutorial.activities.search_product_screen;

import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.kuba.firebasetutorial.R;

public class SearchProductsScreenView extends AppCompatActivity {
    LinearLayout found;
    SearchProductScreenController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_list);
        TextView x = findViewById(R.id.listNameTextView);
        x.setText(getIntent().getStringExtra("LISTNAME"));
        controller = new SearchProductScreenController(this);
        found = findViewById(R.id.search_lin_layout);
        getProductsFromList();
        Button addProductBtn = findViewById(R.id.addProductBtnId);
        setNewProductListener(addProductBtn);
        Button addOwnersBtn = findViewById(R.id.addOwnersId);
        setNewOwnerListener(addOwnersBtn);
    }

    private void setNewOwnerListener(Button addOwnersBtn) {
        addOwnersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddOwnerPopup(v.getRootView());
            }
        });
    }

    private void setNewProductListener(Button addProductBtn) {
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddListPopup(v.getRootView());
            }
        });
    }

    public void setFoundProductBox(String productName, int childIndex) {
        LinearLayout productBox = inflateProductBox(productName, childIndex);
        setOnProductBoxOnClick(productBox);
    }

    private LinearLayout inflateProductBox(String productName, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_product_box, found, true);
        LinearLayout productBox = (LinearLayout) found.getChildAt(childIndex);
        CheckBox box = (CheckBox) productBox.getChildAt(0);
        box.setChecked(true);
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
                if (!checkBox.isChecked()) {
                    removeProductFromList(productName);
                    productBox.removeAllViewsInLayout();
                }
            }
        });
    }

    private void removeProductFromList(final String productName) {
        controller.handleRemoveProductFromList(productName);
    }

    private void getProductsFromList() {
        controller.handleGetAllProductsFromList();
    }

    public void onClickShowAllProductsFromDatabase(View view) {
        controller.startAllProductsFromDatabaseActivity();
    }

    public void onClickGoBackToListView(View view) {
        controller.startLoggedInActivity();
    }

    private void showAddOwnerPopup(View parent) {
        final CardView popUpCardView = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_owner_popup_view, null, false);
        final LinearLayout popUpLinLayout = (LinearLayout) popUpCardView.getChildAt(0);
        Button button = (Button) popUpLinLayout.getChildAt(1);
        final PopupWindow popup = new PopupWindow(popUpLinLayout, 600,
                400, true);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                controller.restartActivity();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userLoginText = (EditText) popUpLinLayout.getChildAt(0);
                final String userLogin = userLoginText.getText().toString();
                controller.handleAddNewOwner(userLogin);
                popup.dismiss();
            }
        });
        popup.setContentView(popUpCardView);
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.setOutsideTouchable(true);

        parent.getBackground().setColorFilter(getResources().getColor(R.color.cardview_dark_background), PorterDuff.Mode.DARKEN);
        popup.showAtLocation(parent, Gravity.CENTER_HORIZONTAL, 10, 10);
    }

    private void showAddListPopup(View parent) {
        final CardView popUpCardView = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_product_popup_view, null, false);
        final LinearLayout popUpLinLayout = (LinearLayout) popUpCardView.getChildAt(0);
        Button button = (Button) popUpLinLayout.getChildAt(1);
        final PopupWindow popup = new PopupWindow(popUpLinLayout, 600,
                400, true);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                controller.startAllProductsFromDatabaseActivity();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText listNameText = (EditText) popUpLinLayout.getChildAt(0);
                final String productName = listNameText.getText().toString();
                controller.handleAddOwnProduct(productName);
                popup.dismiss();
            }
        });
        popup.setContentView(popUpCardView);
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.setOutsideTouchable(true);

        parent.getBackground().setColorFilter(getResources().getColor(R.color.cardview_dark_background), PorterDuff.Mode.DARKEN);
        popup.showAtLocation(parent, Gravity.CENTER_HORIZONTAL, 10, 10);
    }

}
