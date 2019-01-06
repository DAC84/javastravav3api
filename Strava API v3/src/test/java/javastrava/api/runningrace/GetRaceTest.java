package javastrava.api.runningrace;

import javastrava.model.StravaRunningRace;
import javastrava.api.APIGetTest;
import javastrava.api.callback.APIGetCallback;
import javastrava.service.standardtests.data.RunningRaceDataUtils;

/**
 * <p>
 * Specific tests for getRace methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class GetRaceTest extends APIGetTest<StravaRunningRace, Integer> {

	@Override
	protected APIGetCallback<StravaRunningRace, Integer> getter() {
		return ((api, id) -> api.getRace(id));
	}

	@Override
	protected Integer invalidId() {
		return RunningRaceDataUtils.RUNNING_RACE_INVALID_ID;
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaRunningRace result) throws Exception {
		RunningRaceDataUtils.validateRace(result);
	}

	@Override
	protected Integer validId() {
		return RunningRaceDataUtils.RUNNING_RACE_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
