package com.example.triviaapp.custom_classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    final public boolean is_solo;
    final public boolean is_competitive;
    public String subject;
    final public int maxPlayers;
    final public int questions_number;
    final public String room_name;
    public ArrayList<Question> questions;
    public ArrayList<String> players;

    public Room(boolean is_solo, boolean is_competitive, String subject, int maxPlayers, int questions_number, String room_name, ArrayList<Question> questions, ArrayList<String> players) {
        this.is_solo = is_solo;
        this.is_competitive = is_competitive;
        this.subject = subject;
        this.maxPlayers = maxPlayers;
        this.questions_number = questions_number;
        this.room_name = room_name;
        this.questions = questions;
        this.players = players;
    }

    public Room(boolean is_solo, String subject, int maxPlayers, int questions_number, String name) {
        this.is_solo = is_solo;
        this.subject = subject;
        this.is_competitive = false;
        this.maxPlayers = maxPlayers;
        this.questions_number = questions_number;
        this.room_name = name;
    }


}
