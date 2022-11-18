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

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private TextView title;
    private Button soloButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);
        title = findViewById(R.id.homeActivityTitle);
        soloButton = findViewById(R.id.soloPlayButton);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null){
            Toast.makeText(this, "account error! ", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = account.getDisplayName();
        String titleText ="Welcome" + name;
        title.setText(titleText);

        Toast.makeText(this, "welcome! " + name, Toast.LENGTH_SHORT).show();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        soloButton.setOnClickListener(new soloClickHandler());
    }

    private class soloClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
            finish();
        }
    }
}