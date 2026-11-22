package com.Emad.automation.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public final class AuthSpecBuilder {

    private AuthSpecBuilder() {
    }

    /**
     * Bearer-token authentication specification.
     */
    public static RequestSpecification
    getBearerTokenSpec(String token) {

        return new RequestSpecBuilder()
                .addHeader(
                        "Authorization",
                        "Bearer " + token
                )
                .build();
    }

    /**
     * API-key authentication specification.
     */
    public static RequestSpecification
    getApiKeySpec(
            String headerName,
            String apiKey) {

        return new RequestSpecBuilder()
                .addHeader(
                        headerName,
                        apiKey
                )
                .build();
    }
}