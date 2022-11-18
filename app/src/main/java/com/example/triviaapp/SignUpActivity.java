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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private CollectionReference usersRef;
    private static final String usersRefTitle = "users";

    private EditText usernameEditText;
    private EditText passwordEditText;

    private static final String usernameField = "username";
    ImageView googleSignInImg;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usersRef = db.collection(usersRefTitle);
        googleSignInImg = findViewById(R.id.google_signin_button);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build();

        gsc = GoogleSignIn.getClient(this, gso);

        googleSignInImg.setOnClickListener(view -> SignIn());

//        signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                .setSupported(true)
//                .setS)

//        final Button signUpButton = findViewById(R.id.signUpButton);
//        signUpButton.setOnClickListener(new signUpClickHandler());
//
    }

    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(resultCode, resultCode, data);

        if (requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        startActivity(intent);
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

    private void userAlreadyExist(){
        String msg = "User already exists, try login";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    private void addUser(User user){
        usersRef
                .add(user)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

    }



}