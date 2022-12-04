package com.example.triviaapp.custom_classes;

public class GameResults extends Game {
    private final int[] timeScores;
    private final boolean[] correctAnswers;

    private float averageTimeScore;
    private int numberOfCorrectQuestions;

    public GameResults(int[] timeScores, boolean[] correctAnswers, Game game) {
        super(game);
        this.timeScores = timeScores;
        this.correctAnswers = correctAnswers;

        setNumberOfCorrectAnswers();
        setAverageTimeScore();
    }

    public boolean[] getCorrectAnswers() {
        return correctAnswers;
    }

    public int[] getTimeScores() {
        return timeScores;
    }

    public int getNumberOfCorrectQuestions() {
        return numberOfCorrectQuestions;
    }

    public float getAverageTimeScore() {
        return averageTimeScore;
    }

    private void setNumberOfCorrectAnswers(){
        int numberOfCorrectAnswers = 0;
        for(boolean isCorrect : this.correctAnswers){
            if(isCorrect){
                numberOfCorrectAnswers += 1;
            }
        }
        this.numberOfCorrectQuestions = numberOfCorrectAnswers;
    }

    public void setAverageTimeScore(){
        float avg = 0;
        for (long score : timeScores) {
            avg += score;
        }
        avg /= timeScores.length;
        averageTimeScore = avg;
    }

}
