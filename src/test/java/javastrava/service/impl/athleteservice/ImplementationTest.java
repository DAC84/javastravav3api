package javastrava.service.impl.athleteservice;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.service.AthleteService;
import javastrava.service.exception.UnauthorizedException;
import javastrava.service.impl.AthleteServiceImpl;
import javastrava.service.standardtests.spec.ServiceInstanceTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>
 * Tests for {@link AthleteServiceImpl} instances
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
			final Token token1 = TestUtils.getValidToken();
            final AthleteService service1 = new AthleteServiceImpl(new API(token1));

			final Token token2 = TestUtils.getValidTokenWithWriteAccess();
			assertFalse(token1.equals(token2));
            final AthleteService service2 = new AthleteServiceImpl(new API(token2));
			assertFalse(service1 == service2);
		});
	}

	@Override
	@Test
	public void testImplementation_implementationIsCached() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Token token = TestUtils.getValidToken();
            final AthleteService service1 = new AthleteServiceImpl(new API(token));
            final AthleteService service2 = new AthleteServiceImpl(new API(token));
			assertTrue(service1 == service2);
		});
	}

	@Override
	@Test
	public void testImplementation_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
            final AthleteService services = new AthleteServiceImpl(new API(TestUtils.getValidToken()));
			try {
				services.getAuthenticatedAthlete();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Got a service object using an invalid token"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_revokedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
            final AthleteService services = new AthleteServiceImpl(new API(TestUtils.getValidToken()));
			try {
				services.getAuthenticatedAthlete();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Got a service object using a revoked token"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testImplementation_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
            final AthleteService services = new AthleteServiceImpl(new API(TestUtils.getValidToken()));
			assertNotNull(services);
		});
	}

}
