package ru.yandex.praktikum;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class UserClient {

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String USER_API = "/api/auth/register";
    public static final String USER_UPDATE_API = "/api/auth/user";
    public static final String USER_LOGIN_API = "/api/auth/login";

    public static void createUser(String name, String email, String password) {
        given().baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\", \"name\":\"" + name + "\"}")
                .when()
                .post(USER_API);
    }

    public static String getAccessToken(String email, String password) {
        Response response = given().baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .when()
                .post(USER_LOGIN_API);
        return response.then().extract().path("accessToken");
    }

    public static void deleteUser(String accessToken) {
        given().baseUri(BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete(USER_UPDATE_API);
    }
}
