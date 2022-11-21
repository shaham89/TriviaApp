package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth m_auth;

    private TextView title;
    private Button soloButton;
    private FirebaseUser m_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        m_auth = FirebaseAuth.getInstance();
        m_user = m_auth.getCurrentUser();
        assert m_user != null;
        String userId = m_user.getUid();


        title = findViewById(R.id.homeActivityTitle);
        soloButton = findViewById(R.id.soloPlayButton);
        final Button logoutButton = findViewById(R.id.logoutButton);

        String name = m_user.getDisplayName();
        String titleText ="Welcome " + name;
        title.setText(titleText);

        Toast.makeText(this, "welcome! " + name, Toast.LENGTH_SHORT).show();

        soloButton.setOnClickListener(new soloClickHandler());
        logoutButton.setOnClickListener(new logoutClickHandler());
    }

    private class soloClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
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