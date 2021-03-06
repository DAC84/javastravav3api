package javastrava.api.activity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;

import org.junit.Test;

import javastrava.api.API;
import javastrava.config.StravaConfig;
import javastrava.model.StravaActivity;
import javastrava.model.StravaAthlete;
import javastrava.api.APIPagingListTest;
import javastrava.api.callback.APIListCallback;
import javastrava.api.util.ArrayCallback;
import api.issues.strava.Issue96;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#listFriendsActivities(Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListFriendsActivitiesTest extends APIPagingListTest<StravaActivity, Integer> {
	/**
	 * @see test.api.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return null;
	}

	@Override
	protected APIListCallback<StravaActivity, Integer> listCallback() {
		return ((api, id) -> api.listFriendsActivities(null, null));
	}

	@Override
	protected ArrayCallback<StravaActivity> pagingCallback() {
		return (paging -> api().listFriendsActivities(paging.getPage(), paging.getPageSize()));
	}

	/**
	 * @see test.api.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * <p>
	 * Check that activities flagged as private by the authenticated user aren't returned by default
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for unexpected reasons
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testListFriendsActivities_checkPrivateFlagAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue96().isIssue()) {
				return;
			}

			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);
			for (final StravaActivity activity : activities) {
				if (activity.getAthlete().getId().equals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID) && activity.getPrivateActivity().booleanValue()) {
					fail("Returned private activities belonging to the authenticated user"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * <p>
	 * Check that activities flagged as private by other users aren't returned by default
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for unexpected reasons
	 */
	@SuppressWarnings({ "boxing", "static-method" })
	@Test
	public void testListFriendsActivities_checkPrivateFlagOtherUsers() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);
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
	 *             if the test fails for unexpected reasons
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testListFriendsActivities_hasFriends() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity[] activities = api().listFriendsActivities(1, StravaConfig.MAX_PAGE_SIZE);

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
				ActivityDataUtils.validate(activity);
			}
		});
	}

	@Override
	protected void validate(final StravaActivity activity) {
		ActivityDataUtils.validate(activity);

	}

	@Override
	protected void validateArray(final StravaActivity[] activities) {
		for (final StravaActivity activity : activities) {
			ActivityDataUtils.validate(activity);
		}
	}

	/**
	 * @see test.api.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
