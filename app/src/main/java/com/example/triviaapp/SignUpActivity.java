package com.example.triviaapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth m_auth;
    private String Tag = "SignInActivity";

    private CollectionReference usersRef;
    private static final String usersRefTitle = "users";

    private EditText emailEditText;
    private EditText passwordEditText;

    private static final String usernameField = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = findViewById(R.id.signUpEmailEditText);
        passwordEditText = findViewById(R.id.signUpPasswordEditText);


        m_auth = FirebaseAuth.getInstance();

        final Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new signUpClickHandler());

        final TextView switchTextView = findViewById(R.id.switchToLoginText);
        switchTextView.setOnClickListener(new switchToLogin());
    }

    protected class signUpClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {
            signIn(emailEditText.getText().toString(), passwordEditText.getText().toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //check if the user is signed(non-null)
        FirebaseUser currentUser = m_auth.getCurrentUser();
        if(currentUser != null){
            //user is already logged in
            HomeActivity();
        }

    }

    private void signIn(String email, String password){
        m_auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //if sign in is a success
                            FirebaseUser user = m_auth.getCurrentUser();
                            assert user != null;
                            Log.d(Tag, "create user with email success: " + user.getEmail());
                            HomeActivity();
                        } else{
                            //if sign is failed
                            Log.w(Tag, "create user failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "auth failed", Toast.LENGTH_SHORT).show();
//shaharmar1@kramim.ort.org.il
                        }
                    }
                });

    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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

    private boolean isValidUsername(){
        final short MIN_SIZE = 2;
        final short MAX_SIZE = 10;
        String username = emailEditText.getText().toString();
        return username.length() >= MIN_SIZE && username.length() <= MAX_SIZE;
    }

    private boolean isValidPassword(){
        final short MIN_SIZE = 4;
        String password = passwordEditText.getText().toString();
        return password.length() >= MIN_SIZE;
    }

    private User getCurrentUser(){
        return new User(emailEditText.getText().toString(),
                passwordEditText.getText().toString());
    }

    private void userAlreadyExist(){
        String msg = "User already exists, try login";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    private void addUser(User user){
        usersRef
                .add(user)
                .addOnSuccessListener(documentReference -> Log.d(Tag, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(Tag, "Error adding document", e));

    }



}