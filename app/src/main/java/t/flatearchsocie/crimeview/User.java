package t.flatearchsocie.crimeview;

import java.io.Serializable;

class User implements Serializable {

    private int userID;
    private String username,password, name , surname;


    public User(int userID, String username, String password, String name, String surname) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
