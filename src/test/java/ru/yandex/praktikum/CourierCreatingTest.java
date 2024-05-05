package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.*;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;


public class CourierCreatingTest {

    private final CourierClient client = new CourierClient();
    int courierId;
    int courierId2;

    @DisplayName("successfully creating courier test")
    @Test
    public void canCreateCourierTest() {

        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        client.checkCreatedSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier((CourierCredentials) creds);
        courierId = client.checkLoggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }

    @DisplayName("can not create 2 same couriers test")
    @Test
    public void createTwoSameCouriersTest(){
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        client.checkCreatedSuccessfully(createResponse);

        var courier2 = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstname());
        ValidatableResponse createSecondCourierResponse = client.createCourier(courier2);
        client.checkNotCreatedSameCourier(createSecondCourierResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier((CourierCredentials) creds);
        courierId = client.checkLoggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }

    @DisplayName("One important field in courier creation throws error test")
    @Test
    public void oneFieldInRequestNotCompleteTest(){
        var courier = new Courier(RandomStringUtils.randomAlphabetic(5,15), "1243");
        ValidatableResponse createWithoutOneFieldResponse = client.createCourier(courier);
        client.checkNotCreatedWithoutOneField(createWithoutOneFieldResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier((CourierCredentials) creds);
        client.checkNotCreatedWithoutOneField(loginResponse);
    }

    @After
    public void end(){
        if(courierId != 0) {
            ValidatableResponse deleteResponse = client.deleteCourier(courierId);
            client.deletedSuccessfully(deleteResponse);
        }
    }
}