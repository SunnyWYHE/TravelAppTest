package com.hsbc.gbl.eep.robotpa.travelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class HomeActivity extends AppCompatActivity {

    private TextView mWelcomeUser; //  User Name TextView Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent it = getIntent();
        String username = it.getStringExtra("username");
        if(username != null) {
            mWelcomeUser = (TextView)findViewById(R.id.welcome_user);
            mWelcomeUser.setText(username);
        }


    }
}
