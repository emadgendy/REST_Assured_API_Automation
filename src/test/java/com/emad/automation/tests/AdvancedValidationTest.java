package com.Emad.automation.tests;

import com.Emad.automation.services.UserService;
import com.Emad.automation.utils.SpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator
        .matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class AdvancedValidationTest extends BaseTest {

    private final UserService userService =
            new UserService();

    /**
     * Validate the JSON response structure against a schema.
     */
    @Test(groups = {"regression"})
    public void verifyUserJsonSchema() {

        Response response =
                userService.getUserById(1);

        response.then()
                .spec(SpecBuilder.getOkResponseSpec())
                .body(
                        matchesJsonSchemaInClasspath(
                                "schemas/user-schema.json"
                        )
                );
    }

    /**
     * Validate important HTTP response headers.
     */
    @Test(groups = {"regression"})
    public void verifyResponseHeaders() {

        Response response =
                userService.getUserById(1);

        response.then()
                .statusCode(200)
                .header(
                        "Content-Type",
                        containsString(
                                "application/json"
                        )
                );
    }

    /**
     * Validate API response time.
     *
     * A generous limit is intentionally used because
     * JSONPlaceholder is an external public service.
     */
    @Test(groups = {"regression"})
    public void verifyResponseTime() {

        Response response =
                userService.getUserById(1);

        long responseTime =
                response.getTime();

        Assert.assertTrue(
                responseTime < 10000,
                "API response took longer than 10 seconds. "
                        + "Actual response time: "
                        + responseTime
                        + " ms"
        );
    }

    /**
     * Validate multiple fields and their data types.
     */
    @Test(groups = {"regression"})
    public void verifyUserResponseStructure() {

        Response response =
                userService.getUserById(1);

        response.then()
                .spec(SpecBuilder.getOkResponseSpec())
                .body("id", instanceOf(Integer.class))
                .body("name", instanceOf(String.class))
                .body(
                        "username",
                        instanceOf(String.class)
                )
                .body(
                        "email",
                        instanceOf(String.class)
                )
                .body("address", notNullValue())
                .body("company", notNullValue());
    }
}