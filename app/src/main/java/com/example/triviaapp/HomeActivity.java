package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser m_user;
    public static boolean isSound = true;
    public static boolean isMusic = true;

    private ImageView speakerImageView;
    private ImageView musicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseAuth m_auth = FirebaseAuth.getInstance();
        m_user = m_auth.getCurrentUser();
        assert m_user != null;


        init_views();

        //JUST FOR TESTING!!!!!!
//        Intent intent = new Intent(getApplicationContext(), CreateRoomActivity.class);
//        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
//        //intent.putExtra(String.valueOf(R.string.is_solo), false);
//        startActivity(intent);
//        finish();

        //chatApi.getQuestion(10, "Machine learning");

    }

    //go to create game
    private class playClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), CreateGameActivity.class);
            boolean isCompetitive = view.getId() == R.id.playCompetitiveButton;
            intent.putExtra(String.valueOf(R.string.is_competitive_text), isCompetitive);
            startActivity(intent);
            finish();

        }
    }

    //logout
    private class logoutClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(intent);
        }
    }

    //go to leaderboards
    private class leaderBoardsClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), LeaderBoardActivity.class);

            finish();
            startActivity(intent);
        }
    }

    //set the music and textToSpeech on and off
    private class musicSettings implements View.OnClickListener {
        @Override
        public void onClick(View view){

            if(view.getId() == R.id.speaker_icon){
                isSound = !isSound;
                if(isSound){
                    speakerImageView.setImageResource(R.drawable.speaker);
                } else{
                    speakerImageView.setImageResource(R.drawable.speaker_mute_icon);
                }

            } else if(view.getId() == R.id.music_icon){
                isMusic = !isMusic;

                if(isMusic){
                    musicImageView.setImageResource(R.drawable.music_icon);
                    startService(new Intent(HomeActivity.this, BackgroundMusic.class));
                } else {
                    musicImageView.setImageResource(R.drawable.music_mute_icon);
                    stopService(new Intent(HomeActivity.this, BackgroundMusic.class));
                }

            }

        }
    }

    private void init_views(){
        TextView title = findViewById(R.id.homeActivityTitle);

        String name = m_user.getDisplayName();
        String titleText = "Welcome " + name;
        title.setText(titleText);

        Toast.makeText(getApplicationContext(), "Welcome! " + name, Toast.LENGTH_LONG).show();


        findViewById(R.id.playCompetitiveButton).setOnClickListener(new playClickHandler());
        findViewById(R.id.playPracticeButton).setOnClickListener(new playClickHandler());
        findViewById(R.id.leaderBoardsButton).setOnClickListener(new leaderBoardsClickHandler());
        findViewById(R.id.logoutButton).setOnClickListener(new logoutClickHandler());
        musicImageView = findViewById(R.id.music_icon);
        musicImageView.setOnClickListener(new musicSettings());
        speakerImageView = findViewById(R.id.speaker_icon);
        speakerImageView.setOnClickListener(new musicSettings());

    }

}