package javastrava.utils;

import javastrava.api.API;
import javastrava.auth.TokenManager;
import javastrava.auth.TokenService;
import javastrava.auth.impl.TokenServiceImpl;
import javastrava.auth.model.Token;
import javastrava.auth.model.TokenResponse;
import javastrava.auth.ref.AuthorisationScope;
import javastrava.model.StravaAthlete;
import javastrava.service.Strava;
import javastrava.service.exception.BadRequestException;
import javastrava.service.exception.UnauthorizedException;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Dan Shannon
 */
public abstract class TestUtils {
    /**
     * Username to use to authenticate with Strava OAuth process
     */
    public static String USERNAME;

    /**
     * Password to use to authenticate with Strava OAuth process
     */
    public static String PASSWORD;

    /**
     * Strava application id (see https://www.strava.com/settings/api - client id)
     */
    public static Integer STRAVA_APPLICATION_ID;

    /**
     * Strava client secret (see https://www.strava.com/settings/api - Client Secret)
     */
    public static String STRAVA_CLIENT_SECRET;

    /**
     * An invalid token
     */
    public static Token INVALID_TOKEN;

    private static final String PROPERTIES_FILE = "test-config.properties"; //$NON-NLS-1$

    private static Properties properties;

    static {
        try {
            properties = loadPropertiesFile(PROPERTIES_FILE);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        USERNAME = properties.getProperty("username"); //$NON-NLS-1$
        PASSWORD = properties.getProperty("password"); //$NON-NLS-1$
        STRAVA_APPLICATION_ID = Integer.valueOf(properties.getProperty("strava_application_id")); //$NON-NLS-1$
        STRAVA_CLIENT_SECRET = properties.getProperty("client_secret"); //$NON-NLS-1$

        INVALID_TOKEN = createToken(properties.getProperty("test.invalidToken"), USERNAME); //$NON-NLS-1$

    }

    /**
     * Create token from scratch
     *
     * @param accessToken Access token that you've already been given by Strava
     * @param username    User name
     * @return Token created
     */
    private static Token createToken(final String accessToken, final String username) {
        final TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        final StravaAthlete athlete = new StravaAthlete();
        athlete.setEmail(username);
        tokenResponse.setAthlete(athlete);
        tokenResponse.setTokenType("Bearer"); //$NON-NLS-1$
        final Token token = new Token(tokenResponse);
        return token;
    }

    /**
     * Get a revoked token
     *
     * @return A Token whose authorization has been revoked
     * @throws UnauthorizedException if don't have access to deauthorise the token
     */
    public static Token getRevokedToken() throws UnauthorizedException {
        final Token token = getValidToken();
        final TokenService service = new TokenServiceImpl(new API(token));
        service.deauthorise(token);
        return token;
    }

    /**
     * @return A valid token with no additional scopes
     */
    public static Token getValidToken() {
        return tokenWithExactScope();
    }

    /**
     * @return A valid token with write and view_private scopes
     */
    public static Token getValidTokenWithFullAccess() {
        return tokenWithExactScope(AuthorisationScope.WRITE, AuthorisationScope.VIEW_PRIVATE);
    }

    /**
     * @return A valid token with view_private scope
     */
    public static Token getValidTokenWithViewPrivate() {
        return tokenWithExactScope(AuthorisationScope.VIEW_PRIVATE);
    }

    /**
     * @return A valid token with write scope
     */
    public static Token getValidTokenWithWriteAccess() {
        return tokenWithExactScope(AuthorisationScope.WRITE);
    }

    /**
     * @param key Name in the properties file
     * @return The value of the named property
     */
    public static Integer integerProperty(final String key) {
        try {
            return Integer.valueOf(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * @param propertiesFile Name of the properties file
     * @return Resource bundle associated with the file
     * @throws IOException File can't be read
     */
    private static Properties loadPropertiesFile(final String propertiesFile) throws IOException {
        Properties properties = new Properties();
        properties.load(TestUtils.class.getClassLoader().getResourceAsStream(propertiesFile));
        return properties;
    }

    /**
     * @param key Name in the properties file
     * @return The value of the named property
     */
    public static Long longProperty(final String key) {
        return Long.valueOf(properties.getProperty(key));
    }

    /**
     * @return A Strava instance with no additional scopes
     */
    public static Strava strava() {
        return new Strava(new API(getValidToken()));
    }

    /**
     * @return A Strava instance with write and view_private scopes
     */
    public static Strava stravaWithFullAccess() {
        return new Strava(new API(getValidTokenWithFullAccess()));
    }

    /**
     * @return A Strava instance with view_private scope
     */
    public static Strava stravaWithViewPrivate() {
        return new Strava(new API(getValidTokenWithViewPrivate()));
    }

    /**
     * @return A Strava instance with write scope
     */
    public static Strava stravaWithWriteAccess() {
        return new Strava(new API(getValidTokenWithWriteAccess()));
    }

    /**
     * @param key Name in the properties file
     * @return The value of the named property
     */
    public static String stringProperty(final String key) {
        return new String(properties.getProperty(key));
    }

    private static Token tokenWithExactScope(final AuthorisationScope... scopes) {
        Token token = TokenManager.instance().retrieveTokenWithExactScope(USERNAME, scopes);
        if (token == null) {
            try {
                token = TestHttpUtils.getStravaAccessToken(USERNAME, PASSWORD, scopes);
                TokenManager.instance().storeToken(token);
            } catch (BadRequestException | UnauthorizedException e) {
                return null;
            }
        }
        return token;

    }

}
