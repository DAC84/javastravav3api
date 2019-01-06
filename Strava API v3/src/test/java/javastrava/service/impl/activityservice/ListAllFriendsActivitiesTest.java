package javastrava.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaActivity;
import javastrava.service.standardtests.ListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests for listAllFriendsActivities methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllFriendsActivitiesTest extends ListMethodTest<StravaActivity, Integer> {
	@Override
	protected Class<StravaActivity> classUnderTest() {
		return StravaActivity.class;
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
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaActivity, Integer> lister() {
		return ((strava, id) -> strava.listAllFriendsActivities());
	}

	/**
	 * <p>
	 * Test that the list is returned and has a maximum of 200 results as documented
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testListAllFriendsActivities() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaActivity> activities = lister().getList(TestUtils.strava(), AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(activities);
			assertTrue(activities.size() <= 200);
			for (final StravaActivity activity : activities) {
				ActivityDataUtils.validate(activity);
			}
		});
	}

	@Override
	protected void validate(StravaActivity activity) {
		ActivityDataUtils.validate(activity);
	}
}
