package com.Emad.automation.tests;

import com.Emad.automation.services.UserService;
import com.Emad.automation.utils.SpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class NegativeApiTest extends BaseTest {

    private final UserService userService =
            new UserService();

    /**
     * Verify that requesting a non-existing user
     * returns HTTP 404.
     */
    @Test(groups = {"regression"})
    public void verifyNonExistingUserReturns404() {

        int invalidUserId = 99999;

        Response response =
                userService.getUserById(
                        invalidUserId
                );

        response.then()
                .spec(
                        SpecBuilder
                                .getNotFoundResponseSpec()
                );
    }

    /**
     * Verify that a non-existing user returns
     * an empty JSON response body.
     */
    @Test(groups = {"regression"})
    public void verifyNonExistingUserResponseBody() {

        int invalidUserId = 99999;

        Response response =
                userService.getUserById(
                        invalidUserId
                );

        response.then()
                .statusCode(404);

        Map<String, Object> responseBody =
                response.jsonPath()
                        .getMap("$");

        Assert.assertTrue(
                responseBody.isEmpty(),
                "Response body should be empty for a non-existing user."
        );
    }
}