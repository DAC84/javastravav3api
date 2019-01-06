package javastrava.api.athlete.async;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import javastrava.api.athlete.ListAthletesBothFollowingTest;
import javastrava.api.callback.APIListCallback;
import javastrava.api.util.ArrayCallback;

/**
 * <p>
 * Specific tests for {@link API#listAthletesBothFollowingAsync(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthletesBothFollowingAsyncTest extends ListAthletesBothFollowingTest {
	/**
	 * @see test.api.athlete.ListAthletesBothFollowingTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAthletesBothFollowingAsync(id, null, null).get();
	}

	/**
	 * @see test.api.athlete.ListAthletesBothFollowingTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAthletesBothFollowingAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

}
