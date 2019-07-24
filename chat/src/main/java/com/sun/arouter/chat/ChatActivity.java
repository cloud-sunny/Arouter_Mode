package com.sun.arouter.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sun.com.annotations.BindPath;

@BindPath("chat/chat")
public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}
