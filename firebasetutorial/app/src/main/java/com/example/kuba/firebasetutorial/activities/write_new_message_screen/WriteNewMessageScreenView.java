package com.example.kuba.firebasetutorial.activities.write_new_message_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.kuba.firebasetutorial.R;


public class WriteNewMessageScreenView extends AppCompatActivity {
    EditText recipientEditText;
    EditText messageEditText;
    WriteNewMessageScreenController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_new_message_screen);
        recipientEditText = findViewById(R.id.message_to);
        recipientEditText.setText(getIntent().getStringExtra("RECIPENTNAME"));
        messageEditText = findViewById(R.id.new_message_edit_text);
        controller = new WriteNewMessageScreenController(this);
    }

    public void sendMessageOnClick(View view) {
        controller.handleSendMessage(recipientEditText.getText().toString(), messageEditText.getText().toString());
    }
}
