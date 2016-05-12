package com.teamlz.cheTajo.object;

/*
 * Created by francesco on 05/05/16.
 */
public class User {
    public static String myEmail;
    public static String myFirstName;
    public static String myLastName;

    private String firstName;
    private String lastName;
    private String email;

    public User(){}

    public User(String email, String firstName, String lastName){
        this.email = email.trim();
        this.firstName = "";
        this.lastName = "";

        String[] parts = firstName.split(" ");
        for (int i = 0; i < parts.length; i++) {
            this.firstName += parts[i].substring(0, 1).toUpperCase();
            this.firstName += parts[i].substring(1).toLowerCase() + " ";
        }
        this.firstName = this.firstName.trim();

        parts = lastName.split(" ");
        for (int i = 0; i < parts.length; i++) {
            this.lastName += parts[i].substring(0, 1).toUpperCase();
            this.lastName += parts[i].substring(1).toLowerCase() + " ";
        }
        this.lastName = this.lastName.trim();
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
