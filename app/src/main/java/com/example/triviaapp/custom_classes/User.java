package com.example.triviaapp.custom_classes;

public class User {
    protected String m_id;

    public String getId() {
        return m_id;
    }

    public void setId(String m_id) {
        this.m_id = m_id;
    }

    public User(String id){
        m_id = id;
    }


    @Override
    public boolean equals(Object obj){
        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }

        return this.m_id.equals(((User)obj).getId());
    }
}
