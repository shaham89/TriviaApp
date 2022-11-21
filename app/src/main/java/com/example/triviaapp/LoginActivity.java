package com.example.triviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth m_auth;
    private String Tag = "loginActivity";

    private CollectionReference usersRef;
    private static final String usersRefTitle = "users";

    private EditText emailEditText;
    private EditText passwordEditText;

    private static final String usernameField = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.loginEmailEditText);
        passwordEditText = findViewById(R.id.loginPasswordEditText);


        m_auth = FirebaseAuth.getInstance();

        final Button signUpButton = findViewById(R.id.loginButton);
        signUpButton.setOnClickListener(new loginClickHandler());

        final TextView switchTextView = findViewById(R.id.switchToSignUpTextView);
        switchTextView.setOnClickListener(new switchToSignUp());
    }

    protected class loginClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {
            login(emailEditText.getText().toString(), passwordEditText.getText().toString());
        }

    }

    private void login(String email, String password){
        m_auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        //if sign in is a success
                        FirebaseUser user = m_auth.getCurrentUser();
                        assert user != null;
                        Log.d(Tag, "logged user with email success: " + user.getEmail());
                        HomeActivity();
                    } else{
                        //if sign is failed
                        Log.w(Tag, "login user failure", task.getException());
                        Toast.makeText(LoginActivity.this, "auth failed", Toast.LENGTH_SHORT).show();
//shaharmar1@kramim.ort.org.il
                    }
                });
    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    protected class switchToSignUp implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        }
    }

}