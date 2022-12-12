package com.example.triviaapp;

import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import java.text.MessageFormat;

public class Stopwatch {
    private static final String TAG = "stopwatch";
    private final RoundedProgressBar stopWatchBar;
    private final long maxWaitingTimeMs;
    private long currentWaitingTimeMs;
    private boolean isRunning;

    private static final long REFRESH_RATE_MS = 100;
    // Creates a new Handler
    final Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Stopwatch(RoundedProgressBar timeTextView, long maxWaitingTimeMs) {
        this.stopWatchBar = timeTextView;
        this.maxWaitingTimeMs = maxWaitingTimeMs;
        this.currentWaitingTimeMs = maxWaitingTimeMs;
        isRunning = false;
        Log.d(TAG, "time: " + currentWaitingTimeMs);


    }


    private void updateUi(){
        //timeTextView.setText(MessageFormat.format("{0} ms", currentWaitingTimeMs));
        double percentage = 100 * (double)currentWaitingTimeMs / maxWaitingTimeMs;
        //Log.d(TAG, "percent: " + percentage);
        stopWatchBar.setProgressPercentage(percentage, false);
    }

    public void stop(){
        isRunning = false;
    }

    public void reset(){
        this.currentWaitingTimeMs = maxWaitingTimeMs;
        stopWatchBar.setProgressPercentage(95 , false);
        isRunning = true;
        start();
    }

    private void start(){

        Log.d(TAG, "starting");

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(!isRunning){
                    return;
                }

                // Set the text view text.
               updateUi();
                currentWaitingTimeMs -= REFRESH_RATE_MS;

                if(currentWaitingTimeMs <= 0){
                    isRunning = false;
                }
                //Log.d(TAG, "time: " + currentWaitingTimeMs);

                // Post the code again
                handler.postDelayed(this, REFRESH_RATE_MS);
            }
        });

    }

}
