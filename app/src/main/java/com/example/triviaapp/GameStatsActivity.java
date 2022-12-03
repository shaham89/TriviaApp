package com.example.triviaapp;

import static com.example.triviaapp.R.string.questions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.triviaapp.custom_classes.Question;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class GameStatsActivity extends AppCompatActivity {

    private GraphView graphView;
    private long[] scores;
    private String[] questions;
    private boolean[] correctAnswers;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        Intent intent = getIntent();

        scores = intent.getLongArrayExtra(getString(R.string.scores_text));
        questions = intent.getStringArrayExtra(getString(R.string.questions));
        correctAnswers = intent.getBooleanArrayExtra(getString(R.string.correct_answers_text));

        initViews();
    }

    private void initViews(){
        graphView = findViewById(R.id.scoresGraph);

        initGraph();

    }

    private void initGraph(){
        graphView.setTitle("Time scores");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(18);

        DataPoint[] points = new DataPoint[scores.length];
        for(int i = 0; i < scores.length; i++){
            points[i] = new DataPoint(i, scores[i]);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        graphView.addSeries(series);

    }
}