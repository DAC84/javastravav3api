package javastrava.api.runningrace.async;

import javastrava.api.API;
import javastrava.model.StravaRunningRace;
import javastrava.api.callback.APIGetCallback;
import javastrava.api.runningrace.GetRaceTest;

/**
 * <p>
 * Specific tests and config for {@link API#getRaceAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetRaceAsyncTest extends GetRaceTest {
	@Override
	protected APIGetCallback<StravaRunningRace, Integer> getter() {
		return ((api, id) -> api.getRaceAsync(id).get());
	}

}
