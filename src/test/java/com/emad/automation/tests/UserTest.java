package com.Emad.automation.tests;

import com.Emad.automation.models.User;
import com.Emad.automation.services.UserService;
import com.Emad.automation.utils.SpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class UserTest extends BaseTest {

    private final UserService userService =
            new UserService();

    /**
     * GET ALL USERS
     */
    @Test(groups = {"smoke", "regression"})
    public void verifyGetAllUsers() {

        Response response =
                userService.getAllUsers();

        response.then()
                .spec(SpecBuilder.getOkResponseSpec())
                .body("size()", greaterThan(0));

        Assert.assertFalse(
                response.jsonPath()
                        .getList("$")
                        .isEmpty(),
                "Users list should not be empty."
        );
    }

    /**
     * GET USER BY ID
     */
    @Test(groups = {"smoke", "regression"})
    public void verifyGetUserById() {

        int userId = 1;

        Response response =
                userService.getUserById(userId);

        response.then()
                .spec(SpecBuilder.getOkResponseSpec())
                .body("id", equalTo(userId))
                .body("name", not(emptyOrNullString()))
                .body("username", not(emptyOrNullString()))
                .body("email", not(emptyOrNullString()));
    }

    /**
     * POST - CREATE USER
     *
     * Demonstrates POJO serialization.
     */
    @Test(groups = {"regression"})
    public void verifyCreateUser() {

        User requestUser =
                new User(
                        "Emad R",
                        "Emadautomation",
                        "Emad@example.com"
                );

        Response response =
                userService.createUser(requestUser);

        response.then()
                .statusCode(201)
                .body(
                        "name",
                        equalTo(requestUser.getName())
                )
                .body(
                        "username",
                        equalTo(requestUser.getUsername())
                )
                .body(
                        "email",
                        equalTo(requestUser.getEmail())
                )
                .body(
                        "id",
                        notNullValue()
                );
    }

    /**
     * PUT - FULL UPDATE
     */
    @Test(groups = {"regression"})
    public void verifyUpdateUser() {

        int userId = 1;

        User updatedUser =
                new User(
                        "Emad Updated",
                        "Emadupdated",
                        "Emad.updated@example.com"
                );

        Response response =
                userService.updateUser(
                        userId,
                        updatedUser
                );

        response.then()
                .statusCode(200)
                .body(
                        "id",
                        equalTo(userId)
                )
                .body(
                        "name",
                        equalTo(updatedUser.getName())
                )
                .body(
                        "username",
                        equalTo(updatedUser.getUsername())
                )
                .body(
                        "email",
                        equalTo(updatedUser.getEmail())
                );
    }

    /**
     * PATCH - PARTIAL UPDATE
     */
    @Test(groups = {"regression"})
    public void verifyPartialUpdateUser() {

        int userId = 1;

        Map<String, Object> updates =
                new HashMap<>();

        updates.put(
                "email",
                "newemail@example.com"
        );

        Response response =
                userService.partiallyUpdateUser(
                        userId,
                        updates
                );

        response.then()
                .statusCode(200)
                .body(
                        "id",
                        equalTo(userId)
                )
                .body(
                        "email",
                        equalTo(
                                "newemail@example.com"
                        )
                );
    }

    /**
     * DELETE USER
     */
    @Test(groups = {"regression"})
    public void verifyDeleteUser() {

        int userId = 1;

        Response response =
                userService.deleteUser(userId);

        response.then()
                .statusCode(200);
    }

    /**
     * DESERIALIZATION
     *
     * Converts JSON response into User POJO.
     */
    @Test(groups = {"regression"})
    public void verifyUserResponseDeserialization() {

        int userId = 1;

        Response response =
                userService.getUserById(userId);

        response.then()
                .spec(
                        SpecBuilder.getOkResponseSpec()
                );

        User user =
                response.as(User.class);

        Assert.assertEquals(
                user.getId(),
                Integer.valueOf(userId),
                "User ID is incorrect."
        );

        Assert.assertNotNull(
                user.getName(),
                "User name should not be null."
        );

        Assert.assertNotNull(
                user.getEmail(),
                "User email should not be null."
        );

        Assert.assertFalse(
                user.getName().isBlank(),
                "User name should not be empty."
        );
    }
}