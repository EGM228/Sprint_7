package org.example;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class CourierClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("courier loging")
    public static ValidatableResponse loginCourier(CourierCredentials creds){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(creds)
                .when()
                .post(COURIER_PATH+"/login")
                .then().log().all();
    }

    @Step("creating courier")
    public static ValidatableResponse createCourier(Courier courier){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("check of successfully creation of courier")
    public void checkCreatedSuccessfully(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("check of successfully log of courier")
    public static int checkLoggedInSuccessfully(ValidatableResponse loginResponse){
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
        return id;
    }

    @Step("deleting courier")
    public ValidatableResponse deleteCourier(int id) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(Map.of("id",id))
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then().log().all();
    }

    @Step("check of successfully deleted of courier")
    public void deletedSuccessfully(ValidatableResponse deleteResponse) {
        deleteResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("ok");
    }

    @Step("check of the inability to create the same courier")
    public void checkNotCreatedSameCourier(ValidatableResponse createSecondCourierResponse) {
        int created = createSecondCourierResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .extract()
                .path("code");
        assertEquals(409, created);
    }

    @Step("check of the inability to create courier without one important field")
    public void checkNotCreatedWithoutOneField(ValidatableResponse createWithoutOneFieldResponse) {
        int created = createWithoutOneFieldResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .extract()
                .path("code");
        assertEquals(400, created);
    }

    @Step("check of the inability to log in with wrong password or login")
    public int checkLoggedInWrongLogOrPass(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .extract()
                .path("code");
    }

    @Step("check of the inability to log in without password or login")
    public int checkLoggedInWithoutOneField(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .extract()
                .path("code");
    }

    @Step("check of the inability to log in when unauthorized")
    public int checkLoggedInUnauthorized(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .extract()
                .path("code");
    }
}
