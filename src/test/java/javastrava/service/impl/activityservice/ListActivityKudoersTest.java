package javastrava.service.impl.activityservice;

import javastrava.model.StravaAthlete;
import javastrava.service.standardtests.PagingListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.callbacks.PagingListCallback;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for methods dealing with listing kudos on activities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityKudoersTest extends PagingListMethodTest<StravaAthlete, Long> {
	@Override
	protected Class<StravaAthlete> classUnderTest() {
		return StravaAthlete.class;
	}

	@Override
	protected Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_KUDOS;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_KUDOS;
	}

	@Override
	protected ListCallback<StravaAthlete, Long> lister() {
		return ((strava, id) -> strava.listActivityKudoers(id));
	}

	@Override
	protected PagingListCallback<StravaAthlete, Long> pagingLister() {
		return ((strava, paging, id) -> strava.listActivityKudoers(id, paging));
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete, athlete.getId(), athlete.getResourceState());
	}
}
