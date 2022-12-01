package com.example.triviaapp;

import android.content.Context;
import android.widget.Toast;

public class CredentialsValidator {


    //checks if all the credentials are valid, and Toasts appropriate message accordingly
    public static boolean isLoginCredentialsValid(Context activityContext,
                                                   String emailAddress,
                                                   String password){

        if(!isPasswordValid(password)){
            //password invalid
            String passwordRules = "Password Length should be at least 6 letters";
            Toast.makeText(activityContext, passwordRules, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!isEmailAddressValid(emailAddress)){
            //email address invalid
            String emailRules = "Email not valid";
            Toast.makeText(activityContext, emailRules, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //checks if all the credentials are valid, and Toasts appropriate message accordingly
    public static boolean isSignUpCredentialsValid(Context activityContext,
                                                   String emailAddress,
                                                   String password,
                                                   String displayName){
        if(!isLoginCredentialsValid(activityContext, emailAddress, password)){
            return false;
        }

        if(!isDisplayNameValid(displayName)){
            String displayNameRules = "Display name should be 2-10 letters";
            Toast.makeText(activityContext, displayNameRules, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //checks for length
    static boolean isDisplayNameValid(String username){
        final short MIN_CHARACTER_SIZE = 2;
        final short MAX_CHARACTER_SIZE = 10;

        return username.length() >= MIN_CHARACTER_SIZE &&
                username.length() <= MAX_CHARACTER_SIZE;
    }

    //checks for regex pattern and length
    static boolean isEmailAddressValid(String email){
        final short MIN_CHARACTER_SIZE = 2;
        final short MAX_CHARACTER_SIZE = 40;

        return email.length() >= MIN_CHARACTER_SIZE &&
                email.length() <= MAX_CHARACTER_SIZE &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //checks for length
    static boolean isPasswordValid(String password){
        final short MIN_CHARACTER_SIZE = 4;
        final short MAX_CHARACTER_SIZE = 20;

        return password.length() >= MIN_CHARACTER_SIZE &&
                password.length() <= MAX_CHARACTER_SIZE;
    }


}
