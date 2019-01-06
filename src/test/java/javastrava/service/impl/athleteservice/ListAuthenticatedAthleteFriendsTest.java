package javastrava.service.impl.athleteservice;

import javastrava.model.StravaAthlete;
import javastrava.service.standardtests.PagingListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.callbacks.PagingListCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for list authenticated athlete friends methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteFriendsTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected Class<StravaAthlete> classUnderTest() {
		return StravaAthlete.class;

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
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAuthenticatedAthleteFriends());
	}

	@Override
	protected PagingListCallback<StravaAthlete, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAuthenticatedAthleteFriends(paging));
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete);
	}
}
