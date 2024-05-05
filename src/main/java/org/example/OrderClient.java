package org.example;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/api/v1/orders";

    @Step("creating order")
    public ValidatableResponse createOrder(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("check for successfully created order")
    public int checkCreatedSuccessfully(ValidatableResponse createResponse) {
        int track = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("track");
        return track;
    }

    @Step("deleting order")
    public ValidatableResponse deleteOrder(int track) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(Map.of("track",track))
                .when()
                .delete(ORDER_PATH + "/cancel")
                .then().log().all();
    }

    @Step("check for successfully deleted order")
    public void checkDeleteSuccessfully(ValidatableResponse deleteResponse) {
        deleteResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .extract()
                .path("ok");
    }

    @Step("getting order id by track number")
    public int getIdOrderByTRack(int track) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDER_PATH + "/track?t="+ track)
                .then().log().all()
                .extract()
                .path("order.id");
    }


    @Step("courier taking order")
    public void courierTakeOrder(int idOrder, int courierId) {
        given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .put(ORDER_PATH + "/accept/" + idOrder + "?courierId=" + courierId);
    }

    @Step("check of courier orders")
    public Object checkCourierOrders(int courierId) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDER_PATH + "?courierId=" + courierId)
                .body().as(Object.class);
    }
}
