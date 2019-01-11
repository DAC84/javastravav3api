package javastrava.service.impl.clubservice;

import javastrava.api.API;
import javastrava.service.ClubService;
import javastrava.service.exception.InvalidTokenException;
import javastrava.service.impl.ClubServiceImpl;
import javastrava.service.standardtests.data.ClubDataUtils;
import javastrava.service.standardtests.spec.ServiceInstanceTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Service implementation tests for {@link ClubService}
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {
	private static ClubService getRevokedTokenService() {
        return new ClubServiceImpl(new API(TestUtils.getValidToken()));
	}

	private static ClubService service() {
        return new ClubServiceImpl(new API(TestUtils.getValidToken()));
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ClubServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = service();
            final ClubService service2 = new ClubServiceImpl(new API(TestUtils.getValidToken()));
			assertFalse(service == service2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link ClubServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = service();
			final ClubService service2 = service();
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ClubServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
            final ClubService service = new ClubServiceImpl(new API(TestUtils.getValidToken()));
			try {
				service.getClub(ClubDataUtils.CLUB_VALID_ID);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link ClubServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = getRevokedTokenService();
			try {
				service.getClub(ClubDataUtils.CLUB_VALID_ID);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test we get a {@link ClubServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final ClubService service = service();
			assertNotNull("Got a NULL service for a valid token", service); //$NON-NLS-1$
		});
	}

}
