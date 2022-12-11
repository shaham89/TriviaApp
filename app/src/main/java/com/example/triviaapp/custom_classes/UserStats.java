package com.example.triviaapp.custom_classes;

public class UserStats extends User {


    private double timeScore;
    private long score;
    private String subject;


    public UserStats(String displayName, double timeScore, long score, String subject) {
        super(displayName);
        this.timeScore = timeScore;
        this.score = score;
        this.subject = subject;
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


}
