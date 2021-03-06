package javastrava.api.activity.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;

import org.junit.Test;

import javastrava.api.API;
import javastrava.config.StravaConfig;
import javastrava.model.StravaActivity;
import javastrava.model.StravaAthlete;
import javastrava.api.activity.ListFriendsActivitiesTest;
import javastrava.api.callback.APIListCallback;
import javastrava.api.util.ArrayCallback;
import api.issues.strava.Issue96;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#listFriendsActivitiesAsync(Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListFriendsActivitiesAsyncTest extends ListFriendsActivitiesTest {
	/**
	 * @see test.api.activity.ListFriendsActivitiesTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaActivity, Integer> listCallback() {
		return (api, id) -> api.listFriendsActivitiesAsync(null, null).get();
	}

	/**
	 * @see test.api.activity.ListFriendsActivitiesTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return paging -> api().listFriendsActivitiesAsync(paging.getPage(), paging.getPageSize()).get();
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testListFriendsActivities_checkPrivateFlagAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue96().isIssue()) {
				return;
			}
			final StravaActivity[] activities = api().listFriendsActivitiesAsync(1, StravaConfig.MAX_PAGE_SIZE).get();
			for (final StravaActivity activity : activities) {
				if (activity.getAthlete().getId().equals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID) && activity.getPrivateActivity().booleanValue()) {
					fail("Returned private activities belonging to the authenticated user"); //$NON-NLS-1$
				}
			}
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testListFriendsActivities_checkPrivateFlagOtherUsers() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivitiesAsync(1, StravaConfig.MAX_PAGE_SIZE).get();
			for (final StravaActivity activity : activities) {
				if (!(activity.getAthlete().getId().equals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID)) && activity.getPrivateActivity().booleanValue()) {
					fail("Returned private activities belonging to other users!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * <p>
	 * List latest {@link StravaActivity activities} for {@link StravaAthlete athletes} the currently authorised user is following
	 * </p>
	 *
	 * <p>
	 * Should return a list of rides in descending order of start date
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testListFriendsActivities_hasFriends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivitiesAsync(1, StravaConfig.MAX_PAGE_SIZE).get();

			assertNotNull("Returned null array for latest friends' activities", activities); //$NON-NLS-1$

			// Check that the activities are returned in descending order of
			// start date
			ZonedDateTime lastStartDate = null;
			for (final StravaActivity activity : activities) {
				if (lastStartDate == null) {
					lastStartDate = activity.getStartDate();
				} else {
					if (activity.getStartDate().isAfter(lastStartDate)) {
						fail("Activities not returned in descending start date order"); //$NON-NLS-1$
					}
				}
			}
		});
	}
}
