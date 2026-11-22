package com.Emad.automation.services;

import com.Emad.automation.constants.ApiEndpoints;
import com.Emad.automation.models.User;
import com.Emad.automation.utils.SpecBuilder;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserService {

    /**
     * GET - Retrieve all users.
     */
    public Response getAllUsers() {

        return given()
                .spec(SpecBuilder.getRequestSpec())
                .when()
                .get(ApiEndpoints.USERS);
    }

    /**
     * GET - Retrieve a user by ID.
     */
    public Response getUserById(int userId) {

        return given()
                .spec(SpecBuilder.getRequestSpec())
                .pathParam("id", userId)
                .when()
                .get(ApiEndpoints.USER_BY_ID);
    }

    /**
     * POST - Create a new user.
     *
     * REST Assured/Jackson serializes the User POJO
     * into a JSON request body.
     */
    public Response createUser(User user) {

        return given()
                .spec(SpecBuilder.getRequestSpec())
                .body(user)
                .when()
                .post(ApiEndpoints.USERS);
    }

    /**
     * PUT - Completely update an existing user.
     */
    public Response updateUser(int userId, User user) {

        return given()
                .spec(SpecBuilder.getRequestSpec())
                .pathParam("id", userId)
                .body(user)
                .when()
                .put(ApiEndpoints.USER_BY_ID);
    }

    /**
     * PATCH - Partially update an existing user.
     */
    public Response partiallyUpdateUser(
            int userId,
            Map<String, Object> updates) {

        return given()
                .spec(SpecBuilder.getRequestSpec())
                .pathParam("id", userId)
                .body(updates)
                .when()
                .patch(ApiEndpoints.USER_BY_ID);
    }

    /**
     * DELETE - Delete a user.
     */
    public Response deleteUser(int userId) {

        return given()
                .spec(SpecBuilder.getRequestSpec())
                .pathParam("id", userId)
                .when()
                .delete(ApiEndpoints.USER_BY_ID);
    }
}