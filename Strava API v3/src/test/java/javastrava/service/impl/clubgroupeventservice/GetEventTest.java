package javastrava.service.impl.clubgroupeventservice;

import javastrava.model.StravaClubEvent;
import javastrava.service.Strava;
import javastrava.service.standardtests.GetMethodTest;
import javastrava.service.standardtests.callbacks.GetCallback;
import javastrava.service.standardtests.data.ClubGroupEventDataUtils;

/**
 * <p>
 * Specific tests and configuration for {@link Strava#getEvent(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetEventTest extends GetMethodTest<StravaClubEvent, Integer> {

	@Override
	protected Integer getIdInvalid() {
		return ClubGroupEventDataUtils.CLUB_EVENT_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer getIdValid() {
		return ClubGroupEventDataUtils.CLUB_EVENT_VALID_ID;
	}

	@Override
	protected GetCallback<StravaClubEvent, Integer> getter() {
		return ClubGroupEventDataUtils.getter();
	}

	@Override
	protected void validate(StravaClubEvent event) {
		ClubGroupEventDataUtils.validateEvent(event);
	}
}
