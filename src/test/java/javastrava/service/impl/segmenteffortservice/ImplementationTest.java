package javastrava.service.impl.segmenteffortservice;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.service.SegmentEffortService;
import javastrava.service.exception.InvalidTokenException;
import javastrava.service.impl.SegmentEffortServiceImpl;
import javastrava.service.standardtests.data.SegmentEffortDataUtils;
import javastrava.service.standardtests.spec.ServiceInstanceTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Implementation tests for SegmentEffortService
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {

	private static Token getRevokedToken() {
		return TestUtils.getRevokedToken();
	}

	private static SegmentEffortService getService() {
        return new SegmentEffortServiceImpl(new API(TestUtils.getValidToken()));
	}

	private static SegmentEffortService getServiceWithoutWriteAccess() {
        return new SegmentEffortServiceImpl(new API(TestUtils.getValidTokenWithWriteAccess()));
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentEffortService service = getService();
			final SegmentEffortService service2 = getServiceWithoutWriteAccess();
			assertFalse(service == service2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentEffortServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
            final SegmentEffortService service = new SegmentEffortServiceImpl(new API(TestUtils.getValidToken()));
            final SegmentEffortService service2 = new SegmentEffortServiceImpl(new API(TestUtils.getValidToken()));
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			SegmentEffortService service = null;
            service = new SegmentEffortServiceImpl(new API(TestUtils.INVALID_TOKEN));
			try {
				service.getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used an invalid token, but still got access!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentEffortServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
            final SegmentEffortService service = new SegmentEffortServiceImpl(new API(getRevokedToken()));
			try {
				service.getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID);
			} catch (final InvalidTokenException e) {
				// Expected
				return;
			}
			fail("Used an invalid token, still got access to Strava data!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test we get a {@link SegmentEffortServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
            final SegmentEffortService service = new SegmentEffortServiceImpl(new API(TestUtils.getValidToken()));
			assertNotNull("Got a NULL service for a valid token", service); //$NON-NLS-1$
		});
	}
}
