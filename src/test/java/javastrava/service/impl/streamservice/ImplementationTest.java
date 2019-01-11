package javastrava.service.impl.streamservice;

import javastrava.api.API;
import javastrava.model.StravaStream;
import javastrava.service.StreamService;
import javastrava.service.exception.InvalidTokenException;
import javastrava.service.impl.StreamServiceImpl;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.service.standardtests.spec.ServiceInstanceTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * <p>
 * StreamService implementation tests
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {

	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = new StreamServiceImpl(new API(TestUtils.getValidToken()));
			final StreamService service2 = new StreamServiceImpl(new API(TestUtils.getValidTokenWithWriteAccess()));
			assertFalse(service == service2);
		});
	}

	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = new StreamServiceImpl(new API(TestUtils.getValidToken()));
			final StreamService service2 = new StreamServiceImpl(new API(TestUtils.getValidToken()));
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = new StreamServiceImpl(new API(TestUtils.INVALID_TOKEN));
			try {
				service.getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = new StreamServiceImpl(new API(TestUtils.getRevokedToken()));
			try {
				service.getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StreamService service = new StreamServiceImpl(new API(TestUtils.getValidToken()));
			assertNotNull("Didn't get a service implementation using a valid token", service); //$NON-NLS-1$
			final List<StravaStream> streams = service.getActivityStreams(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(streams);
		});
	}

}
