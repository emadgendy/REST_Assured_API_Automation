package com.Emad.automation.tests;

import com.Emad.automation.utils.AuthSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationSpecTest
        extends BaseTest {

    @Test(groups = {"regression"})
    public void verifyBearerTokenSpecificationCreated() {

        RequestSpecification specification =
                AuthSpecBuilder
                        .getBearerTokenSpec(
                                "sample-test-token"
                        );

        Assert.assertNotNull(
                specification,
                "Bearer token specification "
                        + "should be created."
        );
    }

    @Test(groups = {"regression"})
    public void verifyApiKeySpecificationCreated() {

        RequestSpecification specification =
                AuthSpecBuilder
                        .getApiKeySpec(
                                "x-api-key",
                                "sample-test-key"
                        );

        Assert.assertNotNull(
                specification,
                "API key specification "
                        + "should be created."
        );
    }
}