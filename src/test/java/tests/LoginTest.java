package tests;

import io.qameta.allure.Owner;
import models.LoginBodyModel;
import models.LoginResponseErrorModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.Specs.reqresRequest;
import static specs.Specs.responseFailed;

public class LoginTest {
    @Test
    @Owner("osam")
    @DisplayName("Проверка пустого пароля при авторизации")
    void loginUnsuccessful() {
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("olga@gmail");

        LoginResponseErrorModel responseBody = step("Send request", () ->
                given(reqresRequest)
                        .body(data)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseFailed)
                        .extract().as(LoginResponseErrorModel.class));

        step("Check response", () -> {
            assertThat(responseBody.getError()).isEqualTo("Missing password");
        });
    }

}
