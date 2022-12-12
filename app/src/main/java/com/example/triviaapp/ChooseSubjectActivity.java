package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.triviaapp.customClasses.Question;
import com.example.triviaapp.customClasses.Game;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChooseSubjectActivity extends AppCompatActivity {

    private static final String TAG = "ChooseSubjectActivity";
    private boolean isSolo;
    private Game m_game;
    private String subject;
    private ArrayList<Question> questions;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);

//        db = FirebaseFirestore.getInstance();
//
//        Intent intent = getIntent();
//        m_room = (Room) intent.getExtras().get(String.valueOf(R.string.room));


        //questions = new ArrayList<Question>();

        findViewById(R.id.capitals_image).setOnClickListener(new imageClickHandler());

    }

    protected class imageClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.capitals_image){
                subject = getString(R.string.capitals);
            }
            //m_room.subject = subject;
            returnSubject();
        }

    }


    private void returnSubject(){
        Intent intent = new Intent();
        //intent.putExtra(String.valueOf(R.string.room), m_room);
        intent.putExtra(String.valueOf(R.string.subject), subject);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }



}