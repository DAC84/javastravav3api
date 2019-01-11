package javastrava.service.impl.gearservice;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.model.StravaGear;
import javastrava.model.reference.StravaResourceState;
import javastrava.service.GearService;
import javastrava.service.impl.GearServiceImpl;
import javastrava.service.standardtests.data.GearDataUtils;
import javastrava.service.standardtests.spec.ServiceInstanceTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Implementation tests for GearService
 *
 * @author Dan Shannon
 *
 */
public class ImplementationTest implements ServiceInstanceTests {
	private static GearService getGearService() {
		return new GearServiceImpl(new API(TestUtils.getValidToken()));
	}

	private static GearService getGearServiceWithoutWriteAccess() {
		return new GearServiceImpl(new API(TestUtils.getValidTokenWithWriteAccess()));
	}

	private static Token getRevokedToken() {
		return TestUtils.getRevokedToken();
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link GearServiceImpl service implementation} for a second, valid, different token, we get a DIFFERENT implementation
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_differentImplementationIsNotCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final GearService service = getGearService();
			final GearService service2 = getGearServiceWithoutWriteAccess();
			assertFalse(service == service2);
		});
	}

	/**
	 * <p>
	 * Test that when we ask for a {@link GearServiceImpl service implementation} for a second time, we get the SAME ONE as the first time (i.e. the caching strategy is working)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final GearService service = new GearServiceImpl(new API(TestUtils.getValidToken()));
			final GearService service2 = new GearServiceImpl(new API(TestUtils.getValidToken()));
			assertEquals("Retrieved multiple service instances for the same token - should only be one", service, service2); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link GearServiceImpl service implementation} if the token isn't valid
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			GearService service = null;
			service = new GearServiceImpl(new API(TestUtils.INVALID_TOKEN));
			final StravaGear gear = service.getGear(GearDataUtils.GEAR_VALID_ID);
			assertEquals(StravaResourceState.PRIVATE, gear.getResourceState());
		});
	}

	/**
	 * <p>
	 * Test that we don't get a {@link GearServiceImpl service implementation} if the token has been revoked by the user
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Token token = getRevokedToken();
			final GearService service = new GearServiceImpl(new API(token));

			final StravaGear gear = service.getGear(GearDataUtils.GEAR_VALID_ID);
			assertEquals(StravaResourceState.PRIVATE, gear.getResourceState());
		});
	}

	/**
	 * <p>
	 * Test we get a {@link GearServiceImpl service implementation} successfully with a valid token
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final GearService service = new GearServiceImpl(new API(TestUtils.getValidToken()));
			assertNotNull("Got a NULL service for a valid token", service); //$NON-NLS-1$
		});
	}

}
