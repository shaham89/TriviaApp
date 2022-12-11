package com.example.triviaapp.custom_classes;

public class UserStats extends User {

    private int totalGamesNumber;
    private int totalRightAnswers;
    private int bestAverageTime;
    private String subject;

    public UserStats(String id, int totalGamesNumber, int totalRightAnswers, int bestAverageTime, String subject) {
        super(id);
        this.totalGamesNumber = totalGamesNumber;
        this.totalRightAnswers = totalRightAnswers;
        this.bestAverageTime = bestAverageTime;
        this.subject = subject;
    }

    public int getTotalGamesNumber() {
        return totalGamesNumber;
    }

    public void setTotalGamesNumber(int totalGamesNumber) {
        this.totalGamesNumber = totalGamesNumber;
    }

    public int getTotalRightAnswers() {
        return totalRightAnswers;
    }

    public void setTotalRightAnswers(int totalRightAnswers) {
        this.totalRightAnswers = totalRightAnswers;
    }

    public int getBestAverageTime() {
        return bestAverageTime;
    }

    public void setBestAverageTime(int bestAverageTime) {
        this.bestAverageTime = bestAverageTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
