package com.example.triviaapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviaapp.helperFunctions.CredentialsValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth m_auth;
    private static final String Tag = "SignInActivity";

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText displayNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        m_auth = FirebaseAuth.getInstance();

        init_views();
    }

    private class signUpClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String emailAddress = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String displayName = displayNameEditText.getText().toString();

            //is the typed credentials are not valid, don't send them
            if(CredentialsValidator.areSignUpCredentialsInvalid(getApplicationContext(),
                    emailAddress,
                    password,
                    displayName)){
                return;
            }

            signUp(emailAddress, password, displayName);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        signIn(); //check if the user has already signedIn
    }

    //Signs in an already signed user, for example if the user has already signedUp or logged in
    private void signIn(){

        //check if the user is signed(non-null)
        FirebaseUser currentUser = m_auth.getCurrentUser();
        if(currentUser != null){
            //user is already logged in
            startHomeActivity();
        }
    }

    //SignUp a --NEW-- user, with the typed credentials
    //Creates a new FirebaseUser in firestore auth
    private void signUp(String email, String password, String displayName) {
        m_auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        //if signUp is a success
                        updateUserDisplayName(displayName);

                    } else {
                        //if signUp failed
                        Log.w(Tag, "create user failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //change the display name of the user to the display name typed
    private void updateUserDisplayName(String displayName){
        FirebaseUser user = m_auth.getCurrentUser();
        assert user != null;

        //update request
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName).build();

        //call update
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        Log.d(Tag, "Updated user display name successfully: " + user.getDisplayName());
                    }
                    startHomeActivity();
                });
    }


    protected void startHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        finish();
        startActivity(intent);
    }

    private class switchToLogin implements View.OnClickListener {
        //switch to login screen
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }




    private void init_views(){
        emailEditText = findViewById(R.id.signUpEmailEditText);
        passwordEditText = findViewById(R.id.signUpPasswordEditText);
        displayNameEditText = findViewById(R.id.displayNameEditText);

        final Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new signUpClickHandler());

        final TextView switchTextView = findViewById(R.id.switchToLoginText);
        switchTextView.setOnClickListener(new switchToLogin());

    }

}