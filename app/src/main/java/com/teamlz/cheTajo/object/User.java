package com.teamlz.cheTajo.object;

/**
 * Created by francesco on 05/05/16.
 */
public class User {

    private String firstName;
    private String lastName;
    private String email;

    public User(){}

    public User(String email, String firstName, String lastName){
        this.email = email;
        String firstF = firstName.substring(0, 1);
        firstF = firstF.toUpperCase();
        this.firstName = firstF + String.copyValueOf(firstName.toCharArray(), 1, firstName.length()-1);

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
