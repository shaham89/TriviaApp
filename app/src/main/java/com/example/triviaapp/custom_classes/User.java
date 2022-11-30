package com.example.triviaapp.custom_classes;

public class User {
    private String m_username;
    private String m_password;

    public User(String username, String password){
        m_username = username;
        m_password = password;
    }

    public String getPassword() {
        return m_password;
    }

    public String getUsername() {
        return m_username;
    }

    public void setPassword(String m_password) {
        this.m_password = m_password;
    }

    public void setUsername(String m_username) {
        this.m_username = m_username;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }

        return this.m_password.equals(((User) obj).getPassword()) &&
                this.m_username.equals(((User)obj).getUsername());
    }
}
