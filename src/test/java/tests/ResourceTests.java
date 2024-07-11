package tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.Specs;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static specs.Specs.reqresRequest;

public class ResourceTests {
    @Test
    @Owner("osam")
    @DisplayName("Проверка наличия цвета по названию")
    void getNameSingleResourceWithGroovy() {
        step("Send request", () ->
                given(reqresRequest)
                        .when()
                        .get("/unknown")
                        .then()
                        .log().body()
                        .spec(Specs.response)
                        .body("data.findAll{it.name =~/./}.name.flatten()",
                                hasItem("blue turquoise")));
    }

    @Test
    @Owner("osam")
    @DisplayName("Проверка несуществующего ресурса")
    void singleResourceNotFound() {
        step("Send request", () ->
                given(reqresRequest)
                        .when()
                        .get("/unknown/100")
                        .then()
                        .log().body()
                        .spec(Specs.responseNotFound));
    }

}
