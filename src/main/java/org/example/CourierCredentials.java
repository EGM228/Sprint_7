package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierCredentials() {
    }

    public CourierCredentials(String password) {
        this.password=password;
    }

    public static Object from(Courier courier){
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public static Object fromWithoutOneField(Courier courier) {
        return new CourierCredentials(courier.getPassword());
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
}
