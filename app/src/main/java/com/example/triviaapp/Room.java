package com.example.triviaapp;

import java.io.Serializable;

public class Room implements Serializable {
    public boolean is_solo;
    public String subject;
    public int maxPlayers;
    public int questions_number;
    public String name;

    public Room(boolean is_solo, String subject, int maxPlayers, int questions_number, String name) {
        this.is_solo = is_solo;
        this.subject = subject;
        this.maxPlayers = maxPlayers;
        this.questions_number = questions_number;
        this.name = name;
    }
}
