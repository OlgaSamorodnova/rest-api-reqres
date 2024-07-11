package tests;

import io.qameta.allure.Owner;
import models.LoginBodyModel;
import models.RegisterResponseErrorModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.Specs.reqresRequest;
import static specs.Specs.responseFailed;

public class RegisterTest {
    @Test
    @Owner("osam")
    @DisplayName("Проверка пустого пароля при регистрации")
    void loginUnsuccessfulWithPojo() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("olga@gmail");
        RegisterResponseErrorModel responseBody = step("Send request", () ->
                given(reqresRequest)
                        .body(loginBody)
                        .when()
                        .post("/register")
                        .then()
                        .log().body()
                        .spec(responseFailed)
                        .extract().as(RegisterResponseErrorModel.class));

        step("Check response", () ->
                assertThat(responseBody.getError()).isEqualTo("Missing password"));
    }
}

