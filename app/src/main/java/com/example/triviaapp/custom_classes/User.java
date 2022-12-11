package com.example.triviaapp.custom_classes;

public class User {
    protected String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public User(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }

        return this.displayName.equals(((User)obj).displayName);
    }
}
