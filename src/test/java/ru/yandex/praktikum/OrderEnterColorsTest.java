package ru.yandex.praktikum;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.Order;
import org.example.OrderClient;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@RunWith(Parameterized.class)
public class OrderEnterColorsTest {

    private Faker faker = new Faker();
    private Random random = new Random();
    private final OrderClient client = new OrderClient();
    private int track;
    private String firstName = faker.name().firstName();
    private String lastName = faker.name().lastName();
    private String address = faker.address().streetAddress();
    private int metro = random.nextInt(12) + 1;
    private String phone = faker.phoneNumber().cellPhone();
    private int rent = random.nextInt(7) + 1;
    private String date = "2024-06-06";
    private String comment = RandomStringUtils.randomAlphabetic(5,15);
    private String[] color;

    public OrderEnterColorsTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] dataForTests() {
        return new Object[][] {
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
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
