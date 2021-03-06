package javastrava.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaActivity;
import javastrava.api.activity.GetActivityTest;
import javastrava.api.callback.APIGetCallback;

/**
 * <p>
 * Specific tests for {@link API#getActivityAsync(Long, Boolean)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetActivityAsyncTest extends GetActivityTest {

	@Override
	protected APIGetCallback<StravaActivity, Long> getter() {
		return ((api, id) -> api.getActivityAsync(id, Boolean.FALSE).get());
	}
}
