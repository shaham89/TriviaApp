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

    protected class signUpClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {

            //is the typed c
            if(!isCredentialsValid()){
                return;
            }

            signUp(emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    displayNameEditText.getText().toString());
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        signIn();
    }

    //Signs in an already signed user, for example if the user already signedUp or logged in
    private void signIn(){

        //check if the user is signed(non-null)
        FirebaseUser currentUser = m_auth.getCurrentUser();
        if(currentUser != null){
            //user is already logged in
            homeActivity();
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

                        homeActivity();
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
                });
    }


    private void homeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        finish();
        startActivity(intent);
    }

    protected class switchToLogin implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    //checks if all the credentials are valid, and Toasts appropriate message accordingly
    private boolean isCredentialsValid(){
        if(isPasswordValid()){
            String passwordRules = "Password Length should be at least 6 letters";
            Toast.makeText(SignUpActivity.this, passwordRules, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isEmailAddressValid()){
            String emailRules = "Email not valid";
            Toast.makeText(SignUpActivity.this, emailRules, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isDisplayNameValid()){
            String displayNameRules = "Display name should be 2-10 letters";
            Toast.makeText(SignUpActivity.this, displayNameRules, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //checks for length
    private boolean isDisplayNameValid(){
        final short MIN_CHARACTER_SIZE = 2;
        final short MAX_CHARACTER_SIZE = 10;
        String username = displayNameEditText.getText().toString();
        return username.length() >= MIN_CHARACTER_SIZE && username.length() <= MAX_CHARACTER_SIZE;
    }

    //checks for regex pattern and length
    private boolean isEmailAddressValid(){
        final short MIN_CHARACTER_SIZE = 2;
        final short MAX_CHARACTER_SIZE = 40;

        String email = emailEditText.getText().toString();
        return email.length() >= MIN_CHARACTER_SIZE &&
                email.length() <= MAX_CHARACTER_SIZE &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //checks for length
    private boolean isPasswordValid(){
        final short MIN_CHARACTER_SIZE = 4;
        final short MAX_CHARACTER_SIZE = 20;
        String password = passwordEditText.getText().toString();
        return password.length() >= MIN_CHARACTER_SIZE && password.length() <= MAX_CHARACTER_SIZE;
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