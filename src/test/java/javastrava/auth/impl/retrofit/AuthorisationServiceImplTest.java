package javastrava.auth.impl.retrofit;

import javastrava.api.API;
import javastrava.auth.AuthorisationService;
import javastrava.auth.impl.AuthorisationServiceImpl;
import javastrava.auth.model.Token;
import javastrava.auth.ref.AuthorisationScope;
import javastrava.model.StravaActivity;
import javastrava.service.Strava;
import javastrava.service.exception.BadRequestException;
import javastrava.service.exception.InvalidTokenException;
import javastrava.service.exception.StravaInternalServerErrorException;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestHttpUtils;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>
 * Tests for the authorisation service
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class AuthorisationServiceImplTest {
	/**
	 * <p>
	 * Test getting a token but with an invalid application identifier
	 * </p>
	 *
	 * <p>
	 * Should fail to get a token at all
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testTokenExchange_invalidClientId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a service implementation
			final AuthorisationService service = new AuthorisationServiceImpl();

			// Authorise
			final String code = TestHttpUtils.approveApplication();

			// Perform the token exchange
			Token tokenResponse;
			try {
				tokenResponse = service.tokenExchange(0, TestUtils.STRAVA_CLIENT_SECRET, code);
			} catch (final BadRequestException e) {
				// Expected behaviour
				return;
			}
			assertNull("Token unexpectedly returned by Strava", tokenResponse); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test getting a token but with an invalid client secret
	 * </p>
	 *
	 * <p>
	 * Should fail to get a token at all
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTokenExchange_invalidClientSecret() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a service implementation
			final AuthorisationService service = new AuthorisationServiceImpl();

			// Authorise
			final String code = TestHttpUtils.approveApplication();

			// Perform the token exchange
			Token tokenResponse;
			try {
				tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, "", code); //$NON-NLS-1$
			} catch (final InvalidTokenException e) {
				// Expected behaviour
				return;
			}
			assertNull("Token unexpectedly returned by Strava", tokenResponse); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange with the wrong code (which is returned by Strava when access is granted)
	 * </p>
	 *
	 * <p>
	 * Should fail to get a token at all
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTokenExchange_invalidCode() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a service implementation
			final AuthorisationService service = new AuthorisationServiceImpl();

			TestHttpUtils.approveApplication();

			// Perform the token exchange
			Token tokenResponse = null;
			try {
				tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, "oops wrong code!"); //$NON-NLS-1$
			} catch (final BadRequestException e) {
				// Expected behaviour
				return;
			}
			assertNull("Token unexpectedly returned by Strava", tokenResponse); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange which includes request for an invalid scope
	 * </p>
	 *
	 * <p>
	 * Should not return a token successfully
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTokenExchange_invalidScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Try to get a token
			try {
				TestHttpUtils.getStravaAccessToken(TestUtils.USERNAME, TestUtils.PASSWORD, AuthorisationScope.UNKNOWN);
			} catch (final BadRequestException e1) {
				// Expected
				return;
			} catch (final StravaInternalServerErrorException e2) { // Workaround for issue #56
				return;
			}
			fail("Got a token with an invalid scope"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test getting a token with all valid settings and no scope
	 * </p>
	 *
	 * <p>
	 * Should return a token successfully which can be used to get athlete public data
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTokenExchange_noScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a service implementation
			final AuthorisationService service = new AuthorisationServiceImpl();

			// Authorise
			final String code = TestHttpUtils.approveApplication();

			// Perform the token exchange
			final Token tokenResponse = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, code);
			assertNotNull("Token not successfully returned by Strava", tokenResponse); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange which includes request for view_private scope
	 * </p>
	 *
	 * <p>
	 * Should return a token successfully, this should grant access to private data for the authenticated athlete
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTokenExchange_viewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Perform the token exchange
			final Token tokenResponse = TestHttpUtils.getStravaAccessToken(TestUtils.USERNAME, TestUtils.PASSWORD, AuthorisationScope.VIEW_PRIVATE);
			assertNotNull("Token not successfully returned by Strava", tokenResponse); //$NON-NLS-1$

		});
	}

	/**
	 * <p>
	 * Test performing a token exchange which includes request for both view_private and write access
	 * </p>
	 *
	 * <p>
	 * Should return a token successfully, this token should grant write access to the authenticated user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTokenExchange_writeAndViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a service implementation
			final Token token = TestHttpUtils.getStravaAccessToken(TestUtils.USERNAME, TestUtils.PASSWORD, AuthorisationScope.VIEW_PRIVATE, AuthorisationScope.WRITE);
			assertNotNull("Token not successfully returned by Strava", token); //$NON-NLS-1$
			// Validate token has write access
			assertTrue(token.getScopes().contains(AuthorisationScope.WRITE));
			assertTrue(token.getScopes().contains(AuthorisationScope.VIEW_PRIVATE));
		});
	}

	/**
	 * <p>
	 * Test performing a token exchange which includes request for write access
	 * </p>
	 *
	 * <p>
	 * Should return a token successfully, this token should grant write access to the authenticated user's data
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTokenExchange_writeScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a token
			final Token token = TestHttpUtils.getStravaAccessToken(TestUtils.USERNAME, TestUtils.PASSWORD, AuthorisationScope.WRITE);
			assertNotNull("Token not successfully returned by Strava", token); //$NON-NLS-1$

			// Validate token has write access
			assertTrue(token.getScopes().contains(AuthorisationScope.WRITE_ACTIVITY));

			// test case to prove we've got write access
			final Strava strava = new Strava(new API(token));
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("AuthorisationServiceImplTest.testTokenExchange_writeScope"); //$NON-NLS-1$
			final StravaActivity response = strava.createManualActivity(activity);
			strava.deleteActivity(response.getId());
		});
	}

}
