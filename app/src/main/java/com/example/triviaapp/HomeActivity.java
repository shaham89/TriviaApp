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

    private FirebaseAuth m_auth;

    private TextView title;
    private Button createGameButton;
    private FirebaseUser m_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        m_auth = FirebaseAuth.getInstance();
        m_user = m_auth.getCurrentUser();
        assert m_user != null;


        title = findViewById(R.id.homeActivityTitle);
        createGameButton = findViewById(R.id.createGameButton);
        final Button logoutButton = findViewById(R.id.logoutButton);

        String name = m_user.getDisplayName();
        String titleText = "Welcome " + name;
        title.setText(titleText);

        Toast.makeText(this, "welcome! " + name, Toast.LENGTH_SHORT).show();

        createGameButton.setOnClickListener(new createRoomClickHandler());
        logoutButton.setOnClickListener(new logoutClickHandler());

        //JUST FOR TESTING!!!!!!
//        Intent intent = new Intent(getApplicationContext(), CreateRoomActivity.class);
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        //intent.putExtra(String.valueOf(R.string.is_solo), false);
        startActivity(intent);
        finish();
    }

    private class createRoomClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), CreateRoomActivity.class);
            //intent.putExtra(String.valueOf(R.string.is_solo), false);
            startActivity(intent);
            finish();

        }
    }

    private class logoutClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(intent);
        }
    }

}