package javastrava.service.impl.runningraceservice;

import javastrava.model.StravaRunningRace;
import javastrava.service.Strava;
import javastrava.service.standardtests.ListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.data.RunningRaceDataUtils;

/**
 * <p>
 * Specific tests and configuration for {@link Strava#listRaces(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRacesTest extends ListMethodTest<StravaRunningRace, Integer> {

	@Override
	protected Class<StravaRunningRace> classUnderTest() {
		return StravaRunningRace.class;
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
		return RunningRaceDataUtils.RUNNING_RACE_VALID_YEAR;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaRunningRace, Integer> lister() {
		return (strava, id) -> strava.listRaces(null);
	}

	@Override
	protected void validate(StravaRunningRace object) {
		RunningRaceDataUtils.validateRace(object);
	}

}
