package com.example.partehobe;

public class User {
    public String name , pass ,  email ;

    public User(){

    }
    public User(String name, String email, String pass) {
        this.name = name;
        this.pass = pass;

        this.email = email;
    }


    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }



    public String getEmail() {
        return email;
    }
}
