package javastrava.service.impl.segmentservice;

import javastrava.api.API;
import javastrava.service.SegmentService;
import javastrava.service.exception.InvalidTokenException;
import javastrava.service.impl.SegmentServiceImpl;
import javastrava.service.standardtests.data.SegmentDataUtils;
import javastrava.service.standardtests.spec.ServiceInstanceTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>
 * SegmentService implementation tests
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = new SegmentServiceImpl(new API(TestUtils.getValidToken()));
			final SegmentService service2 = new SegmentServiceImpl(new API(TestUtils.getValidTokenWithWriteAccess()));
			assertFalse(service == service2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link SegmentServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = new SegmentServiceImpl(new API(TestUtils.getValidToken()));
			final SegmentService service2 = new SegmentServiceImpl(new API(TestUtils.getValidToken()));
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = new SegmentServiceImpl(new API(TestUtils.INVALID_TOKEN));
			try {
				service.getSegment(SegmentDataUtils.SEGMENT_VALID_ID);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link SegmentServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = new SegmentServiceImpl(new API(TestUtils.getRevokedToken()));
			try {
				service.getSegment(SegmentDataUtils.SEGMENT_VALID_ID);
			} catch (final InvalidTokenException e) {
				// expected
				return;
			}
			fail("Used a revoked token but still got access to Strava!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test we get a {@link SegmentServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final SegmentService service = new SegmentServiceImpl(new API(TestUtils.getValidToken()));
			assertNotNull("Got a NULL service for a valid token", service); //$NON-NLS-1$
		});
	}

}
