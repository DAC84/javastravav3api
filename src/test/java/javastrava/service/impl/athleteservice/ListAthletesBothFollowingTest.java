package javastrava.service.impl.athleteservice;

import javastrava.model.StravaAthlete;
import javastrava.service.standardtests.PagingListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.callbacks.PagingListCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for list athletes both following methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthletesBothFollowingTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected Class<StravaAthlete> classUnderTest() {
		return StravaAthlete.class;

	}

	@Override
	protected Integer idInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return AthleteDataUtils.ATHLETE_WITHOUT_KOMS;
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAthletesBothFollowing(id));
	}

	@Override
	protected PagingListCallback<StravaAthlete, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAthletesBothFollowing(id, paging));
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete);
	}

}
