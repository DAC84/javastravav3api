package javastrava.api.athlete.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javastrava.model.StravaAthlete;
import javastrava.model.reference.StravaResourceState;
import javastrava.service.Strava;
import javastrava.api.athlete.ListAuthenticatedAthleteFriendsTest;
import javastrava.api.callback.APIListCallback;
import javastrava.api.util.ArrayCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link Strava#listAuthenticatedAthleteFriendsAsync} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteFriendsAsyncTest extends ListAuthenticatedAthleteFriendsTest {
	/**
	 * @see test.api.athlete.ListAuthenticatedAthleteFriendsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteFriendsAsync(null, null).get();
	}

	/**
	 * @see test.api.athlete.ListAuthenticatedAthleteFriendsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteFriendsAsync(paging.getPage(), paging.getPageSize()).get();
	}

	@Override
	public void testListAuthenticatedAthleteFriends_friends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] friends = api().listAuthenticatedAthleteFriendsAsync(null, null).get();
			assertNotNull(friends);
			assertFalse(friends.length == 0);
			for (final StravaAthlete athlete : friends) {
				AthleteDataUtils.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
			}
		});
	}

}
