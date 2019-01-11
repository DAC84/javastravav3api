package javastrava.config;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>
 * Permissions given by the nice people at Strava to individual applications.
 * </p>
 *
 * @author Dan Shannon
 */
public class JavaStravaApplicationConfig {
    /**
     * Resource bundle containing configuration properties
     */
    private final Properties properties; //$NON-NLS-1$

    public boolean getAllowsActivityDelete() {
        return Boolean.parseBoolean(properties.getProperty("strava.permission.deleteActivity"));
    }

    public boolean getAllowsComment() {
        return Boolean.parseBoolean(properties.getProperty("strava.permission.writeComment"));
    }

    public boolean getAllowsKudo() {
        return Boolean.parseBoolean(properties.getProperty("strava.permission.giveKudos"));
    }

    public boolean getAllowsWebhooks() {
        return Boolean.parseBoolean(properties.getProperty("strava.permission.webhooksEndpoint"));
    }

    public boolean getAllowsChallenges() {
        return Boolean.parseBoolean(properties.getProperty("strava.permission.challengesEndpoint"));
    }

    public boolean getAllowsBeacon() {
        return Boolean.parseBoolean(properties.getProperty("strava.permission.beaconEndpoint"));
    }

    public JavaStravaApplicationConfig() {
        properties = new Properties();
        try {
            properties.load(JavaStravaApplicationConfig.class.getClassLoader().getResourceAsStream("javastrava-config-test.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
