package com.example.triviaapp.customClasses;
import android.graphics.Color;

import java.io.Serializable;

public class Subject implements Serializable {

    public static final String subjectNameCapitals = "capitals";
    public static final String displayNameCapitals = "Capitals";

    public static final String subjectNameAstronomy = "astronomy";
    public static final String displayNameAstronomy = "Astronomy";

    public static final String subjectNameHistory = "history";
    public static final String displayNameHistory = "History";

    public static final String subjectNameHarryPotter = "harry_potter";
    public static final String displayNameHarryPotter = "Harry Potter";

    public static final String subjectNameGeneralKnowledge = "general_knowledge";
    public static final String displayNameGeneralKnowledge = "General Knowledge";

    public static final String subjectNameWorldCup = "world_cup";
    public static final String displayNameWorldCup = "World Cup";

    public Subject(String subjectName, int subjectImageId, String subjectDisplayName){
        this.subjectName = subjectName;
        this.subjectImageId = subjectImageId;
        this.subjectDisplayName = subjectDisplayName;
    }

    public Subject(){
        this.subjectName = "";
        this.subjectImageId = 0;
        this.subjectDisplayName = "";
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectImageId() {
        return subjectImageId;
    }

    public void setSubjectImageId(int subjectImageId) {
        this.subjectImageId = subjectImageId;
    }

    public String getSubjectDisplayName() {
        return subjectDisplayName;
    }

    public void setSubjectDisplayName(String subjectDisplayName) {
        this.subjectDisplayName = subjectDisplayName;
    }

    private String subjectName;
    private int subjectImageId;
    private String subjectDisplayName;
}
