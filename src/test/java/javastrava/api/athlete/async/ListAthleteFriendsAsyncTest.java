package javastrava.api.athlete.async;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import javastrava.api.athlete.ListAthleteFriendsTest;
import javastrava.api.callback.APIListCallback;
import javastrava.api.util.ArrayCallback;

/**
 * <p>
 * Specific tests for {@link API#listAthleteFriendsAsync(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthleteFriendsAsyncTest extends ListAthleteFriendsTest {
	/**
	 * @see test.api.athlete.ListAthleteFriendsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listAthleteFriendsAsync(id, null, null).get();
	}

	/**
	 * @see test.api.athlete.ListAthleteFriendsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listAthleteFriendsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}
}
