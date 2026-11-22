package com.Emad.automation.data;

import org.testng.annotations.DataProvider;

public final class TestDataProvider {

    private TestDataProvider() {
    }

    /**
     * Provides valid user IDs for data-driven API testing.
     */
    @DataProvider(name = "validUserIds")
    public static Object[][] validUserIds() {

        return new Object[][]{
                {1},
                {2},
                {3},
                {4},
                {5}
        };
    }

    /**
     * Provides multiple user payloads for POST testing.
     */
    @DataProvider(name = "userCreationData")
    public static Object[][] userCreationData() {

        return new Object[][]{
                {
                        "Emad Automation",
                        "Emadqa",
                        "Emad.qa@example.com"
                },
                {
                        "API Tester",
                        "apitester",
                        "api.tester@example.com"
                },
                {
                        "SDET Engineer",
                        "sdetengineer",
                        "sdet@example.com"
                }
        };
    }
}