package com.example.triviaapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class GameStatsActivity extends AppCompatActivity {

    private GraphView graphView;
    private long[] scores;
    private boolean[] correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        Intent intent = getIntent();

        scores = intent.getLongArrayExtra(getString(R.string.scores_text));
        correctAnswers = intent.getBooleanArrayExtra(getString(R.string.correct_answers_text));

        initViews();
    }

    private void initViews(){
        graphView = findViewById(R.id.scoresGraph);
        TextView avgView = findViewById(R.id.averageScore);
        float avg = 0;
        for (long score : scores) {
            avg += score;
        }
        avg /= scores.length;

        avgView.setText(String.format("Average Time Score: %s", avg));

        TextView answerScores = findViewById(R.id.answerScore);

        int numberOfCorrectAnswer = 0;
        for(Boolean isCorrect: correctAnswers){
            if(isCorrect){
                numberOfCorrectAnswer += 1;
            }
        }

        answerScores.setText(String.format("%s/%sCorrect Answers", numberOfCorrectAnswer, correctAnswers.length));
        initGraph();

    }

    private static String[] generateXAxis(int size){
        String[] data = new String[size];
        for(int i = 1; i <= size; i++){
            data[i - 1] = String.valueOf(i);
        }
        return data;
    }

    private void initGraph(){
        graphView.setTitle("Time scores");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(40);

        graphView.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graphView.getViewport().setScrollable(true);  // activate horizontal scrolling
        graphView.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graphView.getViewport().setScrollableY(true);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        String[] axis = generateXAxis(scores.length);
        staticLabelsFormatter.setHorizontalLabels(axis);
        staticLabelsFormatter.setVerticalLabels(new String[] {"1000", "2000", "3000","4000", "5000"});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);



        DataPoint[] points = new DataPoint[scores.length];

        for(int i = 0; i < scores.length; i++){
            points[i] = new DataPoint(i, scores[i]);
        }

        PointsGraphSeries<DataPoint> correctSeries = new PointsGraphSeries<>();
        PointsGraphSeries<DataPoint> inCorrectSeries = new PointsGraphSeries<>();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);


        for(int i = 0; i < scores.length; i++){
            if(correctAnswers[i]){
                correctSeries.appendData(points[i], false, scores.length);
            } else{
                inCorrectSeries.appendData(points[i], false, scores.length);
            }
        }

        correctSeries.setColor(Color.GREEN);
        inCorrectSeries.setColor(Color.RED);
        correctSeries.setSize(20);
        inCorrectSeries.setSize(20);

        graphView.addSeries(series);
        graphView.addSeries(correctSeries);
        graphView.addSeries(inCorrectSeries);


    }
}