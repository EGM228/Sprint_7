package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Order;
import org.example.OrderClient;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class OrderEnterColorsTest {

    private int track;
    private final OrderClient client = new OrderClient();
    private String firstName;
    private String lastName;
    private String address;
    private int metro;
    private String phone;
    private int rent;
    private String date;
    private String comment;
    private String[] color;

    public OrderEnterColorsTest(String firstName, String lastName, String address, int metro, String phone, int rent, String date, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.rent = rent;
        this.date = date;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] dataForTests() {
        return new Object[][] {
                { "Kenpachi", "Zaraki", "Gotei", 11, "+78005553535", 3, "2024-06-06", "Strongest", new String[]{"GREY"}},
                { "Mayuri", "Kurotsuchi", "Gotei", 12, "+79345673425", 4, "2024-06-06", "Smartest", new String[]{"BLACK"}},
                { "Yachiru", "Unohana", "Gotei", 4, "+79341373426", 1, "2024-06-06", "First", new String[]{"BLACK", "GREY"}},
                { "Byakuya", "Kuchiki", "Gotei", 6, "+79345653765", 2, "2024-06-06", "Calmest", new String[]{}}
        };
    }

    @DisplayName("different colors in order test")
    @Test
    public void blackOrGreyTest(){
        List<String> list = new ArrayList<>(Arrays.asList(color));
        var order = new Order(firstName, lastName, address, metro, phone, rent, date, comment, list);
        ValidatableResponse createResponse = client.createOrder(order);
        track = client.checkCreatedSuccessfully(createResponse);
    }

    @After
    public void end(){
        ValidatableResponse deleteResponse = client.deleteOrder(track);
        client.checkDeleteSuccessfully(deleteResponse);
    }
}
