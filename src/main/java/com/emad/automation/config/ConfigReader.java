package com.Emad.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES =
            new Properties();

    private ConfigReader() {
    }

    static {

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream(
                                     "config.properties"
                             )) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "config.properties file not found."
                );
            }

            PROPERTIES.load(inputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load config.properties file.",
                    e
            );
        }
    }

    public static String get(String key) {

        String systemValue =
                System.getProperty(key);

        if (systemValue != null
                && !systemValue.isBlank()) {

            return systemValue.trim();
        }

        String value =
                PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {

            throw new RuntimeException(
                    "Configuration key not found: "
                            + key
            );
        }

        return value.trim();
    }

    public static String getEnvironment() {

        return get("environment");
    }

    public static String getBaseUrl() {

        String environment =
                System.getProperty(
                        "environment",
                        getEnvironment()
                );

        return get(
                environment + ".baseUrl"
        );
    }

    public static boolean isRequestLoggingEnabled() {

        return Boolean.parseBoolean(
                get("request.logging")
        );
    }

    public static boolean isPerformanceLoggingEnabled() {

        return Boolean.parseBoolean(
                get("performance.logging")
        );
    }
}