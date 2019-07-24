package com.sun.mode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import sun.com.arouter.ARouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpLogin(View view) {
        ARouter.getInstance().startActivity("login/login",null);
    }
}
