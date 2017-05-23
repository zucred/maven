package com.vascomouta.VMLogger_example;

/**
 * Created by Asma on 23/05/17.
 */

public class User {

     private String name;
     private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Name : " + name + " , Password : " + password;
    }
}
