package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.*;


import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class OrdersListTest {
    private final CourierClient courierClient = new CourierClient();
    private final OrderClient orderClient = new OrderClient();
    int track;
    int idOrder;
    int courierId;
    private String[] color = {"Grey"};

    @DisplayName("Check that the response body returns a list of orders")
    @Test
    public void checkBodyHasListOfOrders(){
        var courier = Courier.random();
        ValidatableResponse createCourierResponse = courierClient.createCourier(courier);
        courierClient.checkCreatedSuccessfully(createCourierResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = CourierClient.loginCourier((CourierCredentials) creds);
        courierId = CourierClient.checkLoggedInSuccessfully(loginResponse);

        var order = new Order(RandomStringUtils.randomAlphabetic(5,15), "sizif", "Moscow", 2, "+789762345672", 4, "2024-06-06", "egsd", List.of(color));
        ValidatableResponse createOrder1Response = orderClient.createOrder(order);
        track = orderClient.checkCreatedSuccessfully(createOrder1Response);

        idOrder=orderClient.getIdOrderByTRack(track);

        orderClient.courierTakeOrder(idOrder, courierId);

        orderClient.checkCourierOrders(courierId);
    }

    @After
    public void end(){
        if(courierId != 0) {
            ValidatableResponse deleteResponse = courierClient.deleteCourier(courierId);
            courierClient.deletedSuccessfully(deleteResponse);
        }
    }

}
