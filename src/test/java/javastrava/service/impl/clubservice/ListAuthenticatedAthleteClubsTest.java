package javastrava.service.impl.clubservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaClub;
import javastrava.service.standardtests.ListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.service.standardtests.data.ClubDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests for listAuthenticatedAthleteClubs methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteClubsTest extends ListMethodTest<StravaClub, Integer> {
	@Override
	protected Class<StravaClub> classUnderTest() {
		return StravaClub.class;
	}

	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaClub, Integer> lister() {
		return ((strava, id) -> strava.listAuthenticatedAthleteClubs());
	}

	/**
	 * <p>
	 * Authenticated StravaAthlete has clubs
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAuthenticatedAthleteClubs() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaClub> clubs = TestUtils.strava().listAuthenticatedAthleteClubs();
			assertNotNull(clubs);
			assertFalse(clubs.size() == 0);
			for (final StravaClub club : clubs) {
				ClubDataUtils.validate(club);
			}
		});
	}

	@Override
	protected void validate(StravaClub object) {
		ClubDataUtils.validate(object);
	}

}
