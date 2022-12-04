package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser m_user;

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
    }

    private class playClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), CreateSoloRoomActivity.class);
            boolean isCompetitive = view.getId() == R.id.playCompetitiveButton;
            intent.putExtra(String.valueOf(R.string.is_competitive_text), isCompetitive);
            startActivity(intent);
            finish();

        }
    }

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

    private void init_views(){
        TextView title = findViewById(R.id.homeActivityTitle);

        String name = m_user.getDisplayName();
        String titleText = "Welcome " + name;
        title.setText(titleText);

        Toast.makeText(getApplicationContext(), "welcome! " + name, Toast.LENGTH_SHORT).show();


        findViewById(R.id.playCompetitiveButton).setOnClickListener(new playClickHandler());
        findViewById(R.id.playPracticeButton).setOnClickListener(new playClickHandler());

        findViewById(R.id.logoutButton).setOnClickListener(new logoutClickHandler());

    }

}