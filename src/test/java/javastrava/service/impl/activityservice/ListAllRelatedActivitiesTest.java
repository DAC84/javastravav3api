package javastrava.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaActivity;
import javastrava.service.standardtests.ListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests for list all related activities methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllRelatedActivitiesTest extends ListMethodTest<StravaActivity, Long> {
	@Override
	protected Class<StravaActivity> classUnderTest() {
		return StravaActivity.class;
	}

	@Override
	protected Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_RELATED_ACTIVITIES;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_RELATED_ACTIVITIES;
	}

	@Override
	protected ListCallback<StravaActivity, Long> lister() {
		return ((strava, id) -> strava.listAllRelatedActivities(id));
	}

	/**
	 * <p>
	 * Test that it works for activities belonging to someone other than the authenticated user
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in some unexpected way
	 */
	@Test
	public void testListAllRelatedActivities_otherUserActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = lister().getList(TestUtils.strava(), ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
			assertNotNull(activities);
			for (final StravaActivity activity : activities) {
				ActivityDataUtils.validate(activity);
			}

		});
	}

	@Override
	protected void validate(StravaActivity object) {
		ActivityDataUtils.validate(object);

	}

}
