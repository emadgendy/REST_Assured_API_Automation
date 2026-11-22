package com.Emad.automation.tests;

import com.Emad.automation.data.TestDataProvider;
import com.Emad.automation.models.User;
import com.Emad.automation.services.UserService;
import com.Emad.automation.utils.SpecBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DataDrivenUserTest extends BaseTest {

    private final UserService userService =
            new UserService();

    /**
     * Executes the same GET test against multiple user IDs.
     */
    @Test(
            dataProvider = "validUserIds",
            dataProviderClass = TestDataProvider.class,
            groups = {"regression"}
    )
    public void verifyMultipleUsersById(int userId) {

        Response response =
                userService.getUserById(userId);

        response.then()
                .spec(SpecBuilder.getOkResponseSpec())
                .body("id", equalTo(userId))
                .body("name", notNullValue())
                .body("email", notNullValue());
    }

    /**
     * Executes POST requests with multiple test-data combinations.
     */
    @Test(
            dataProvider = "userCreationData",
            dataProviderClass = TestDataProvider.class,
            groups = {"regression"}
    )
    public void verifyCreateMultipleUsers(
            String name,
            String username,
            String email) {

        User user =
                new User(
                        name,
                        username,
                        email
                );

        Response response =
                userService.createUser(user);

        response.then()
                .spec(
                        SpecBuilder
                                .getCreatedResponseSpec()
                )
                .body("name", equalTo(name))
                .body(
                        "username",
                        equalTo(username)
                )
                .body("email", equalTo(email))
                .body("id", notNullValue());
    }
}