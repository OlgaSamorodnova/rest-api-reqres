package tests;

import io.qameta.allure.Owner;
import models.CreateBodyModel;
import models.CreateResponseModel;
import models.GetUserDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.Specs;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.Specs.reqresRequest;

public class UserTests {
    @Test
    @Owner("osam")
    @DisplayName("Проверка имени юзера по id")
    void checkUserNameLombok() {
        GetUserDataModel data = step("Send request", () ->
                given(reqresRequest)
                        .when()
                        .get("/users/6")
                        .then()
                        .log().body()
                        .spec(Specs.response)
                        .extract().as(GetUserDataModel.class));

        step("Check response", () ->
                assertThat(data.getUser().getFirstName()).isEqualTo("Tracey"));
    }

    @Test
    @Owner("osam")
    @DisplayName("Проверка данных пользователя")
    void checkSingleNameLombok() {
        GetUserDataModel data = step("Send request", () ->
                given(reqresRequest)
                        .when()
                        .get("/users/6")
                        .then()
                        .spec(Specs.response)
                        .log().body()
                        .extract().as(GetUserDataModel.class));

        step("Check first name", () -> assertThat(data.getUser().getFirstName()).isEqualTo("Tracey"));
        step("Verify last name", () -> assertThat(data.getUser().getLastName()).isEqualTo("Ramos"));
    }

    @Test
    @Owner("osam")
    @DisplayName("Создание нового пользователя")
    void createUserWithPojo() {
        CreateBodyModel createBody = new CreateBodyModel();
        createBody.setName("Olga");
        createBody.setJob("QA");
        CreateResponseModel createResponse = step("Send request", () ->
                given(reqresRequest)
                        .body(createBody)
                        .when()
                        .post("/users")
                        .then()
                        .log().body()
                        .spec(Specs.responseCreate)
                        .extract().as(CreateResponseModel.class));

        step("Verify name", () -> assertThat(createResponse.getName()).isEqualTo("Olga"));
        step("Verify job", () -> assertThat(createResponse.getJob()).isEqualTo("QA"));
    }


    @Test
    @Owner("osam")
    @DisplayName("Удаление юзера")
    void deleteUser() {
        step("Send request", () ->
                given(reqresRequest)
                        .when()
                        .delete("/users/6")
                        .then()
                        .log().body()
                        .spec(Specs.responseDelete));
    }
}
