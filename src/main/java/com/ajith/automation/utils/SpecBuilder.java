package com.Emad.automation.utils;

import com.Emad.automation.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class SpecBuilder {

    private SpecBuilder() {
    }

    public static RequestSpecification getRequestSpec() {

        RequestSpecBuilder builder =
                new RequestSpecBuilder()
                        .setBaseUri(
                                ConfigReader.getBaseUrl()
                        )
                        .setContentType(
                                ContentType.JSON
                        )
                        .setAccept(
                                ContentType.JSON
                        );

        if (ConfigReader
                .isRequestLoggingEnabled()) {

            builder.addFilter(
                    new RequestLoggingFilter()
            );

            builder.addFilter(
                    new ResponseLoggingFilter()
            );
        }

        if (ConfigReader
                .isPerformanceLoggingEnabled()) {

            builder.addFilter(
                    new PerformanceLoggingFilter()
            );
        }

        return builder.build();
    }

    public static ResponseSpecification getOkResponseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(
                        ContentType.JSON
                )
                .build();
    }

    public static ResponseSpecification getCreatedResponseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(
                        ContentType.JSON
                )
                .build();
    }

    public static ResponseSpecification getNotFoundResponseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }
}