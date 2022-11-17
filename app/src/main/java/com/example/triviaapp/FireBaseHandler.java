package com.example.triviaapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FireBaseHandler {

    private static final String TAG = "FireBaseHandler";
    private FirebaseFirestore db;
    private static final String usersRefTitle = "users";
    private CollectionReference usersRef;


    public FireBaseHandler(){
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection(usersRefTitle);
    }
    
    CollectionReference getCollection(String collectionName){
        return db.collection(usersRefTitle);
    }

    public QuerySnapshot getDocuments(Query query) {
        QuerySnapshot[] result = new QuerySnapshot[1];

        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int x = 6;
                            result[0] = task.getResult();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
            });

        return result[0];
    }

}
