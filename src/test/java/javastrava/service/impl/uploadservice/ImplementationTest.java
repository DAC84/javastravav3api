package javastrava.service.impl.uploadservice;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.model.StravaUploadResponse;
import javastrava.service.UploadService;
import javastrava.service.exception.UnauthorizedException;
import javastrava.service.impl.UploadServiceImpl;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.service.standardtests.spec.ServiceInstanceTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>
 * UploadService implementation tests
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
			final UploadService service = new UploadServiceImpl(new API(TestUtils.getValidToken()));
			final UploadService service2 = new UploadServiceImpl(new API(TestUtils.getValidTokenWithWriteAccess()));
			assertFalse(service == service2);
		});
	}

	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Token token = TestUtils.getValidToken();
			final UploadService service = new UploadServiceImpl(new API(token));
			final UploadService service2 = new UploadServiceImpl(new API(token));
			assertEquals(service, service2);
		});
	}

	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				final UploadService service = new UploadServiceImpl(new API(TestUtils.INVALID_TOKEN));
				service.checkUploadStatus(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Got a usable implementation from an invalid token"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				final UploadService service = new UploadServiceImpl(new API(TestUtils.getRevokedToken()));
				service.checkUploadStatus(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Got a service implementation with a valid token!"); //$NON-NLS-1$
		});
	}

	/**
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final UploadService service = new UploadServiceImpl(new API(TestUtils.getValidToken()));
			assertNotNull("Didn't get a service implementation using a valid token", service); //$NON-NLS-1$
			final StravaUploadResponse response = service.checkUploadStatus(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			assertNotNull(response);
		});
	}

}
