package com.Emad.automation.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public final class PerformanceLoggingFilter implements Filter {

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext context) {

        Response response =
                context.next(
                        requestSpec,
                        responseSpec
                );

        System.out.printf(
                "PERF method=%s uri=%s status=%d timeMs=%d responseBytes=%d%n",
                requestSpec.getMethod(),
                requestSpec.getURI(),
                response.getStatusCode(),
                response.getTime(),
                response.getBody().asByteArray().length
        );

        return response;
    }
}
