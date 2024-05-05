package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CourierLoginTest {
    private final CourierClient client = new CourierClient();
    int courierId;

    @Test
    public void courierCanLogInTest(){
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        client.checkCreatedSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier((CourierCredentials) creds);
        courierId = client.checkLoggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }

    @Test
    public void errorLoginOrPasswordIsWrongTest(){
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        client.checkCreatedSuccessfully(createResponse);

        courier.setPassword("qwe123");
        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier((CourierCredentials) creds);
        int responseCode = client.checkLoggedInWrongLogOrPass(loginResponse);
        assertEquals(404, responseCode);
    }

    @Test
    public void errorIfOneFieldIsNotHere(){
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        client.checkCreatedSuccessfully(createResponse);

        var creds = CourierCredentials.fromWithoutOneField(courier);
        ValidatableResponse loginResponse = client.loginCourier((CourierCredentials) creds);
        int responseCode = client.checkLoggedInWithoutOneField(loginResponse);
        assertEquals(400, responseCode);
    }

    @Test
    public void errorWhenUnauthorizedUser(){
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        client.checkCreatedSuccessfully(createResponse);

        courier.setLogin("fghj");
        courier.setPassword("65473");
        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier((CourierCredentials) creds);
        int responseCode = client.checkLoggedInUnauthorized(loginResponse);
        assertEquals(404, responseCode);
    }



    @After
    public void end(){
        if(courierId != 0) {
            ValidatableResponse deleteResponse = client.deleteCourier(courierId);
            client.deletedSuccessfully(deleteResponse);
        }
    }
}
