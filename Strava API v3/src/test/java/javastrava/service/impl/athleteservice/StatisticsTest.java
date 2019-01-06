package javastrava.service.impl.athleteservice;

import org.junit.Test;

import javastrava.model.StravaStatistics;
import javastrava.service.standardtests.GetMethodTest;
import javastrava.service.standardtests.callbacks.GetCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests for statistics methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StatisticsTest extends GetMethodTest<StravaStatistics, Integer> {
	@Override
	protected Integer getIdInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer getIdValid() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected GetCallback<StravaStatistics, Integer> getter() {
		return ((strava, id) -> strava.statistics(id));
	}

	/**
	 * <p>
	 * Test that we can get statistics for the authenticated athlete
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testStatistics_authenticatedAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStatistics stats = TestUtils.strava().statistics(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			AthleteDataUtils.validate(stats);
		});
	}

	@Override
	protected void validate(StravaStatistics object) {
		AthleteDataUtils.validate(object);
	}

}
