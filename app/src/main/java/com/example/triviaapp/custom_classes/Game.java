package com.example.triviaapp.custom_classes;

import java.util.ArrayList;

public class Game extends Room{

    public Game(boolean is_solo, boolean is_competitive, String subject, int maxPlayers, int questions_number, String room_name, ArrayList<Question> questions, ArrayList<String> players) {
        super(is_solo, is_competitive, subject, maxPlayers, questions_number, room_name, questions, players);

    }

    public Game(boolean is_solo, String subject, int maxPlayers, int questions_number, String name) {
        super(is_solo, subject, maxPlayers, questions_number, name);
    }
}
