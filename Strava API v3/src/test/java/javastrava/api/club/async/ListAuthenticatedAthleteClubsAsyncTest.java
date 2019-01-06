package javastrava.api.club.async;

import javastrava.api.API;
import javastrava.model.StravaClub;
import javastrava.api.callback.APIListCallback;
import javastrava.api.club.ListAuthenticatedAthleteClubsTest;

/**
 * <p>
 * Specific config and tests for {@link API#listAuthenticatedAthleteClubsAsync()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteClubsAsyncTest extends ListAuthenticatedAthleteClubsTest {
	/**
	 * @see javastrava.api.club.ListAuthenticatedAthleteClubsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaClub, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteClubsAsync().get();
	}
}
