package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseSubjectActivity extends AppCompatActivity {

    private boolean isSolo;
    private String subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);

        Intent intent = getIntent();
        isSolo = intent.getExtras().getBoolean(String.valueOf(R.string.is_solo));

        findViewById(R.id.capitals_image).setOnClickListener(new imageClickHandler());

    }

    protected class imageClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.capitals_image){
                subject = String.valueOf(R.string.capitals);
            }
        }

    }

}