package com.example.triviaapp.custom_classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    public boolean is_solo;
    public boolean is_competitive;
    public boolean is_active;
    public String subject;
    public int maxPlayers;
    public int questions_number;
    public String name;
    public ArrayList<String> players;


    public Room(boolean is_solo, boolean is_competitive, String subject, int maxPlayers, int questions_number, String name, ArrayList<String> players) {
        this.is_solo = is_solo;
        this.is_competitive = is_competitive;
        this.subject = subject;
        this.maxPlayers = maxPlayers;
        this.questions_number = questions_number;
        this.name = name;
        this.players = players;
    }

    public Room(boolean is_solo, String subject, int maxPlayers, int questions_number, String name) {
        this.is_solo = is_solo;
        this.subject = subject;
        this.maxPlayers = maxPlayers;
        this.questions_number = questions_number;
        this.name = name;
    }

    public Room() {
        this.is_solo = is_solo;
        this.subject = subject;
        this.maxPlayers = maxPlayers;
        this.questions_number = questions_number;
        this.name = name;
    }

}
