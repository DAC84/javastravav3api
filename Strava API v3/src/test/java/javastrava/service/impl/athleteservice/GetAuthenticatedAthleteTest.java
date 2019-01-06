package javastrava.service.impl.athleteservice;

import org.junit.Test;

import javastrava.model.StravaAthlete;
import javastrava.model.reference.StravaResourceState;
import javastrava.service.Strava;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests and configuration for {@link Strava#getAuthenticatedAthlete()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAuthenticatedAthleteTest {
	/**
	 * Check operation of the method to get the authenticated athlete
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetAuthenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete athlete = TestUtils.strava().getAuthenticatedAthlete();
			AthleteDataUtils.validateAthlete(athlete, AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, StravaResourceState.DETAILED);
		});
	}

}
