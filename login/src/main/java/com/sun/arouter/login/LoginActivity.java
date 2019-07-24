package com.sun.arouter.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import sun.com.annotations.BindPath;
import sun.com.arouter.ARouter;

@BindPath("login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void jumpChat(View view) {
        ARouter.getInstance().startActivity("chat/chat",null);
    }
}
