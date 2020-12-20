package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InterviewAppConfiguration {
    private Properties properties;

    private static final String PROPERTIES_FILE_NAME = "config.properties";

    // Keys
    private static final String TWITTER_BEARER_TOKEN_KEY = "twitter.bearerToken";
    private static final String DISPLAY_INTERVAL_KEY = "display.intervalSeconds";
    private static final String DISPLAY_INITIAL_DELAY_KEY = "display.initialDelaySeconds";

    // Default values
    private static final String DISPLAY_INTERVAL_DEFAULT = "10";
    private static final String DISPLAY_INITIAL_DELAY_DEFAULT = "10";

    public InterviewAppConfiguration() {
        properties = new Properties();
    }
    
    public void load() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
        if(input == null) {
            throw new RuntimeException(
                String.format("Could not find config resource named [%s]. App cannot proceed.", PROPERTIES_FILE_NAME));
        }

        properties.load(input);
        input.close();
        
        if(properties.getProperty(TWITTER_BEARER_TOKEN_KEY) == null) {
            throw new RuntimeException(
                String.format("Required configuration [%s] is missing. App cannot proceed.", TWITTER_BEARER_TOKEN_KEY));
        }
    }
    
    public String getTwitterBearerToken() {
        return properties.getProperty(TWITTER_BEARER_TOKEN_KEY);
    }

    public Long getDisplayIntervalSeconds() {
        return Long.valueOf(
            properties.getProperty(DISPLAY_INTERVAL_KEY, DISPLAY_INTERVAL_DEFAULT));
    }

    public Long getDisplayInitialDelay() {
        return Long.valueOf(
            properties.getProperty(DISPLAY_INITIAL_DELAY_KEY, DISPLAY_INITIAL_DELAY_DEFAULT));
    }
}
