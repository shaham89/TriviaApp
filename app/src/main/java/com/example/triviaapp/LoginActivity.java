package com.example.triviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText usernameEditText;
    private EditText passwordEditText;

    private CollectionReference usersRef;
    private static final String usernameField = "username";
    private static final String passwordField = "password";
    private static final String usersRefTitle = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usersRef = db.collection(usersRefTitle);

        usernameEditText = findViewById(R.id.loginUsernameEditText);
        passwordEditText = findViewById(R.id.loginPasswordEditText);

        final Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new loginClickHandler());

    }

    private boolean isValidUsername(){
        final short MIN_SIZE = 2;
        final short MAX_SIZE = 10;
        String username = usernameEditText.getText().toString();
        return username.length() >= MIN_SIZE && username.length() <= MAX_SIZE;
    }

    private boolean isValidPassword(){
        final short MIN_SIZE = 4;
        String password = passwordEditText.getText().toString();
        return password.length() >= MIN_SIZE;
    }

    private User getCurrentUser(){
        return new User(usernameEditText.getText().toString(),
                passwordEditText.getText().toString());
    }

    private boolean isUserExist(@NonNull Task<QuerySnapshot> task){

        for (QueryDocumentSnapshot document : task.getResult()) {

            if (document.exists()){
                return true;
            }
            Log.d(TAG, document.getId() + " => " + document.getData());
        }
        return false;

    }

    protected class loginClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {
            if(isValidUsername() && isValidPassword()){
                User m_user = getCurrentUser();

                usersRef.whereEqualTo(usernameField, m_user.getUsername()).whereEqualTo(passwordField, m_user.getPassword())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                                if(isUserExist(task)){
                                    Toast.makeText(getApplicationContext(), "Successfully logged", Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(getApplicationContext(), "Username or password are not valid", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Enter username and password", Toast.LENGTH_LONG).show();
            }
        }
    }


}