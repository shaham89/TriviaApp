package com.example.triviaapp;

public class Room {
    public boolean is_solo;
    public String subject;
    public int maxPlayers;
    public int questions_number;


    public Room(boolean is_solo, String subject, int maxPlayers, int questions_number) {
        this.is_solo = is_solo;
        this.subject = subject;
        this.maxPlayers = maxPlayers;
        this.questions_number = questions_number;
    }
}
