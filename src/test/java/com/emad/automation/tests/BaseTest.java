package com.Emad.automation.tests;

import com.Emad.automation.reporting.PassFailReportListener;
import io.restassured.RestAssured;
import org.testng.annotations.Listeners;
import org.testng.annotations.BeforeSuite;

@Listeners(PassFailReportListener.class)
public class BaseTest {

    @BeforeSuite
    public void setUp() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}