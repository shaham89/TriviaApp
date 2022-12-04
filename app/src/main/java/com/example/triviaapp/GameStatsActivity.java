package com.example.triviaapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaapp.custom_classes.Game;
import com.example.triviaapp.custom_classes.GameResults;
import com.example.triviaapp.custom_classes.Question;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.Arrays;

public class GameStatsActivity extends AppCompatActivity {

    private GraphView graphView;
    private TextView dataPointTextView;
    private GameResults m_gameResults;
    private static Question[] questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        Intent intent = getIntent();

        int[] scores = intent.getIntArrayExtra(getString(R.string.scores_text));
        boolean[] correctAnswers = intent.getBooleanArrayExtra(getString(R.string.correct_answers_text));

        Game game = (Game) intent.getSerializableExtra(getString(R.string.game_intent_text));
        m_gameResults = new GameResults(scores, correctAnswers, game);
        questions = m_gameResults.getQuestions();

        initViews();
    }

    private void initViews(){
        graphView = findViewById(R.id.scoresGraph);
        dataPointTextView = findViewById(R.id.dataPointTextView);

        initTextViewsScore();
        initGraph();
        initSeries();
    }

    private void initTextViewsScore(){

        TextView avgView = findViewById(R.id.averageScore);
        avgView.setText(String.format("Average Time Score: %s", m_gameResults.getAverageTimeScore()));

        TextView answerScores = findViewById(R.id.answerScore);

        String correctAnswerRatio = String.format("%s/%s Correct Answers",
                m_gameResults.getNumberOfCorrectQuestions(), m_gameResults.getNumberOfQuestions());

        answerScores.setText(correctAnswerRatio);
    }

    private static String[] generateXAxis(int size){
        String[] data = new String[size];
        for(int i = 1; i <= size; i++){
            data[i - 1] = String.valueOf(i);
        }
        return data;
    }


    private void initSeries(){
        DataPoint[] points = new DataPoint[m_gameResults.getNumberOfQuestions()];

        for(int i = 0; i < m_gameResults.getNumberOfQuestions(); i++){
            points[i] = new DataPoint(i + 1, m_gameResults.getTimeScores()[i]);
        }

        PointsGraphSeries<DataPoint> correctSeries = new PointsGraphSeries<>();
        PointsGraphSeries<DataPoint> inCorrectSeries = new PointsGraphSeries<>();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);


        for(int i = 0; i < m_gameResults.getNumberOfQuestions(); i++){
            if(m_gameResults.getCorrectAnswers()[i]){
                correctSeries.appendData(points[i], false, m_gameResults.getNumberOfQuestions());
            } else{
                inCorrectSeries.appendData(points[i], false, m_gameResults.getNumberOfQuestions());
            }
        }

        correctSeries.setColor(Color.GREEN);
        inCorrectSeries.setColor(Color.RED);


        OnDataPointTapListener showQuestionTap = (OnDataPointTapListener) (series1, dataPoint) -> {
            String showOutput = questions[(int) dataPoint.getX() - 1].questionText + "\n" + (int) dataPoint.getY() + " ms";
            dataPointTextView.setText(showOutput);
        };

        correctSeries.setOnDataPointTapListener(showQuestionTap);
        inCorrectSeries.setOnDataPointTapListener(showQuestionTap);

        correctSeries.setSize(20);
        inCorrectSeries.setSize(20);

        graphView.addSeries(series);
        graphView.addSeries(correctSeries);
        graphView.addSeries(inCorrectSeries);
    }

    private void initGraph(){
        graphView.setTitle("Time scores");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(40);


        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0.9);
        graphView.getViewport().setMaxX(m_gameResults.getNumberOfQuestions() + 0.1);

// Set manual Y bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(5100);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        String[] axis = generateXAxis(m_gameResults.getNumberOfQuestions());
        Log.w("Stats activity", Arrays.toString(axis));
        staticLabelsFormatter.setHorizontalLabels(axis);

        staticLabelsFormatter.setVerticalLabels(new String[] {"0,", "1000", "2000", "3000","4000", "5000"});
        //staticLabelsFormatter.setVerticalLabels(new String[] {"1000", "2000"});

        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        graphView.setBackgroundColor(Color.BLACK);

    }


}