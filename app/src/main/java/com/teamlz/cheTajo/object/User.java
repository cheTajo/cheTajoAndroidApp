package com.teamlz.cheTajo.object;

/*
 * Created by francesco on 05/05/16.
 */
public class User {

    private String firstName;
    private String lastName;
    private String email;

    public User(){}

    public User(String email, String firstName, String lastName){
        this.email = email;
        this.firstName = "";
        this.lastName = "";
        String temp;

        String[] pezzi = firstName.split(" ");
        for (int i = 0; i < pezzi.length; i++) {
            temp = pezzi[i].substring(0, 1).toUpperCase();
            firstName += temp + String.copyValueOf(pezzi[i].toCharArray(), 1, pezzi[i].length()-1) + " ";
        }
        firstName.trim();

        pezzi = lastName.split(" ");
        for (int i = 0; i < pezzi.length; i++) {
            temp = pezzi[i].substring(0, 1).toUpperCase();
            lastName += temp + String.copyValueOf(pezzi[i].toCharArray(), 1, pezzi[i].length()-1) + " ";
        }
        firstName.trim();

        String firstL = lastName.substring(0, 1);
        firstL = firstL.toUpperCase();
        this.lastName = firstL + String.copyValueOf(lastName.toCharArray(), 1, lastName.length()-1);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
