package com.example.triviaapp.custom_classes;

import android.content.res.Resources;

import com.example.triviaapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    public boolean is_solo;
    public boolean is_competitive;
    public String subject;
    public int maxPlayers;
    public int questions_number;
    public String room_name;
    public ArrayList<Question> questions;
    public ArrayList<String> players;


    public static final String DEFAULT_SUBJECT = Resources.getSystem().getString(R.string.capitals);
    public static final int DEFAULT_QUESTION_NUMBER = 20;

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

    public Room(String roomName){
        this.is_solo = true;
        this.subject = DEFAULT_SUBJECT;
        this.is_competitive = false;
        this.maxPlayers = 1;
        this.questions_number = DEFAULT_QUESTION_NUMBER;
        this.room_name = roomName;
    }

}
