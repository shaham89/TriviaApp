package com.example.triviaapp;

public class UserStats {


    private double timeScore;
    private long score;
    private String subject;
    private String displayName;

    public UserStats(String displayName, double timeScore, long score, String subject) {
        this.timeScore = timeScore;
        this.score = score;
        this.subject = subject;
        this.displayName = displayName;
    }

    public double getTimeScore() {
        return timeScore;
    }

    public void setTimeScore(double timeScore) {
        this.timeScore = timeScore;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
