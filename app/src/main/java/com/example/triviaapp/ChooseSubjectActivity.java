package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    private int subjectImageID;
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
        findViewById(R.id.astronomy_image).setOnClickListener(new imageClickHandler());
    }

    protected class imageClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int m_id = view.getId();
            switch (m_id){
                case R.id.capitals_image:
                    subject = getString(R.string.capitals);
                    subjectImageID = R.drawable.earth;
                    break;
                case R.id.astronomy_image:
                    subject = getString(R.string.astronomy);
                    subjectImageID = R.drawable.astronomy;
                    break;
                default:
                    subject = getString(R.string.capitals);
                    subjectImageID = R.drawable.earth;

            }

            returnSubject();
        }

    }


    private void returnSubject(){
        Intent intent = new Intent();
        //intent.putExtra(String.valueOf(R.string.room), m_room);
        intent.putExtra(String.valueOf(R.string.subject), subject);
        intent.putExtra(String.valueOf(R.string.image_id), subjectImageID);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }



}