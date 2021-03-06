package javastrava.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaActivity;
import javastrava.api.activity.ListRelatedActivitiesTest;
import javastrava.api.callback.APIListCallback;
import javastrava.api.util.ArrayCallback;

/**
 * <p>
 * Specific tests for {@link API#listRelatedActivitiesAsync(Long, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRelatedActivitiesAsyncTest extends ListRelatedActivitiesTest {
	/**
	 * @see test.api.activity.ListRelatedActivitiesTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaActivity, Long> listCallback() {
		return (api, id) -> api.listRelatedActivitiesAsync(id, null, null).get();
	}

	/**
	 * @see test.api.activity.ListRelatedActivitiesTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return paging -> api().listRelatedActivitiesAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}
}
