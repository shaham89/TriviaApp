package com.example.triviaapp.custom_classes;

public class GameResults extends Game {
    private final int[] timeScores;
    private final boolean[] correctAnswers;
    private final Game game;

    public GameResults(int[] timeScores, boolean[] correctAnswers, Game game, Game game1) {
        super(game);
        this.timeScores = timeScores;
        this.correctAnswers = correctAnswers;

        this.game = game1;
    }

    public boolean[] getCorrectAnswers() {
        return correctAnswers;
    }

    public int[] getTimeScores() {
        return timeScores;
    }

    public int getNumberOfCorrectAnswers(){
        int numberOfCorrectAnswers = 0;
        for(boolean isCorrect : this.correctAnswers){
            if(isCorrect){
                numberOfCorrectAnswers += 1;
            }
        }
        return numberOfCorrectAnswers;
    }
}
