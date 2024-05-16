package org.example;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class Courier {
    private String login;
    private String password;
    private String firstname;

    public Courier(String login, String password, String firstname) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
    }

    public Courier(String password, String firstname){
        this.firstname = firstname;
        this.password = password;
    }

    public Courier() {
    }

    public static Courier random(){
        return new Courier(RandomStringUtils.randomAlphabetic(5,15), "1234", "Sparrow");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
