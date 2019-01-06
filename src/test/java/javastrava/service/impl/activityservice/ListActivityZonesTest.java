package javastrava.service.impl.activityservice;

import javastrava.model.StravaActivityZone;
import javastrava.service.standardtests.ListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Specific tests for the methods that list Strava activity zones
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityZonesTest extends ListMethodTest<StravaActivityZone, Long> {
	@Override
	protected Class<StravaActivityZone> classUnderTest() {
		return StravaActivityZone.class;
	}

	@Override
	public Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	public Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	public Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_ZONES;
	}

	@Override
	public Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_ZONES;
	}

	@Override
	protected ListCallback<StravaActivityZone, Long> lister() {
		return ((strava, id) -> strava.listActivityZones(id));
	}

	@Override
	protected void validate(StravaActivityZone zone) {
		ActivityDataUtils.validateActivityZone(zone);
	}

}
