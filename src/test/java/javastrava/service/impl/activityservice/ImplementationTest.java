package javastrava.service.impl.activityservice;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.service.ActivityService;
import javastrava.service.exception.InvalidTokenException;
import javastrava.service.exception.UnauthorizedException;
import javastrava.service.impl.ActivityServiceImpl;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.service.standardtests.spec.ServiceInstanceTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>
 * Service implementation tests
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {

	API api = new API(TestUtils.getValidToken());
	@SuppressWarnings("static-method")
	private ActivityService service() {
		return new ActivityServiceImpl(api);
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Token token = TestUtils.getValidToken();
			new ActivityServiceImpl(new API(token));
			final Token token2 = TestUtils.getValidTokenWithWriteAccess();
			new ActivityServiceImpl(new API(token2));
			assertNotEquals("Different tokens returned the same service implementation", token, token2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ActivityServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = new ActivityServiceImpl(api);
			final ActivityService service2 = new ActivityServiceImpl(api);
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = new ActivityServiceImpl(new API(TestUtils.INVALID_TOKEN));
			try {
				service.getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ActivityServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Attempt to get an implementation using the now invalidated token
			final ActivityService activityServices = new ActivityServiceImpl(new API(TestUtils.getRevokedToken()));

			// Check that it can't be used
			try {
				activityServices.getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test we get a {@link ActivityServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 *
	 * @throws UnauthorizedException
	 *             If token is not valid
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ActivityService service = service();
			assertNotNull("Got a NULL service for a valid token", service); //$NON-NLS-1$
		});
	}

}
