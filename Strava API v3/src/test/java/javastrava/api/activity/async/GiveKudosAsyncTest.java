package javastrava.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaResponse;
import javastrava.api.activity.GiveKudosTest;
import javastrava.api.callback.APICreateCallback;

/**
 * <p>
 * Specific tests for {@link API#giveKudosAsync(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GiveKudosAsyncTest extends GiveKudosTest {
	@Override
	protected APICreateCallback<StravaResponse, Long> creator() {
		return ((api, response, activityId) -> api.giveKudosAsync(activityId).get());
	}

}
