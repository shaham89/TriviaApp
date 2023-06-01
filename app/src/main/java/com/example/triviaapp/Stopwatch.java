package com.example.triviaapp;

import android.os.Build;
import android.os.Handler;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.mackhartley.roundedprogressbar.RoundedProgressBar;


public class Stopwatch {
    private static final String TAG = "stopwatch";
    private final RoundedProgressBar stopWatchBar;
    private final long maxWaitingTimeMs;
    private long currentWaitingTimeMs;
    private boolean isRunning;

    private static final long REFRESH_RATE_MS = 100;
    private static final long SOME_DELAY_MS = 5;

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


        handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(!isRunning){
                    return;
                }
                // Set the text view text.
                updateUi();
                currentWaitingTimeMs = currentWaitingTimeMs - REFRESH_RATE_MS - SOME_DELAY_MS;

                if(currentWaitingTimeMs <= 0){
                    isRunning = false;
                }
                // Post the code again
                handler.postDelayed(this, REFRESH_RATE_MS);
            }
        });

    }

}
