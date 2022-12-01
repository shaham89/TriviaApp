package com.example.triviaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth m_auth;
    private final String Tag = "loginActivity";

    private CollectionReference usersRef;
    private static final String usersRefTitle = "users";

    private EditText emailEditText;
    private EditText passwordEditText;

    private static final String usernameField = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        m_auth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.loginEmailEditText);
        passwordEditText = findViewById(R.id.loginPasswordEditText);

        final Button signUpButton = findViewById(R.id.loginButton);
        signUpButton.setOnClickListener(new loginClickHandler());

        final TextView switchTextView = findViewById(R.id.switchToSignUpTextView);
        switchTextView.setOnClickListener(new switchToSignUp());
    }

    protected class loginClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String emailAddress = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //is the typed credentials are not valid, don't send them
            if(!CredentialsValidator.isLoginCredentialsValid(getApplicationContext(),
                    emailAddress,
                    password)){
                return;
            }

            login(emailAddress, password);
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
                    } else {
                        //if sign is failed
                        Log.w(Tag, "login user failure", task.getException());
                        Toast.makeText(LoginActivity.this, "auth failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    private class switchToSignUp implements View.OnClickListener {
        //switch to signUpScreen
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        }
    }

}