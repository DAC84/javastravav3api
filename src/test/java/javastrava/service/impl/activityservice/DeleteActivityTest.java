package javastrava.service.impl.activityservice;

import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.StravaActivity;
import javastrava.service.exception.UnauthorizedException;
import javastrava.service.standardtests.DeleteMethodTest;
import javastrava.service.standardtests.callbacks.CreateCallback;
import javastrava.service.standardtests.callbacks.DeleteCallback;
import javastrava.service.standardtests.callbacks.GetCallback;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * <p>
 * Tests related to deleting activities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class DeleteActivityTest extends DeleteMethodTest<StravaActivity, Long> {

    JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();

	@Override
	protected CreateCallback<StravaActivity> creator() {
		return ActivityDataUtils.creator();
	}

	@Override
	protected DeleteCallback<StravaActivity> deleter() {
		return ActivityDataUtils.deleter();
	}

	@Override
	protected StravaActivity generateInvalidObject() {
		return ActivityDataUtils.generateInvalidObject();
	}

	@Override
	protected StravaActivity generateValidObject() {
		return ActivityDataUtils.generateValidObject();
	}

	@Override
	protected GetCallback<StravaActivity, Long> getter() {
		return ActivityDataUtils.getter();
	}

	@Override
	@Test
	public void testDeleteNonExistentParent() throws Exception {
		// Activities don't have parents so this isn't relevant
		return;
	}

	@Override
	public void testDeleteNoWriteAccess() throws Exception {
		// Can't run this test if we don't have permission to delete activities from Strava
        if (javaStravaApplicationConfig.getAllowsActivityDelete()) {
			super.testDeleteNoWriteAccess();
		}
	}

	@Override
	public void testDeleteValidObject() throws Exception {
		// Can't run this test if we don't have permission to delete activities from Strava
        if (javaStravaApplicationConfig.getAllowsActivityDelete()) {
			super.testDeleteValidObject();
		}

	}

	@Override
	@Test
	public void testInvalidId() throws Exception {
		// Can't run this test if we don't have permission to delete activities from Strava
        if (javaStravaApplicationConfig.getAllowsActivityDelete()) {

			RateLimitedTestRunner.run(() -> {
				// Fake activity
				final StravaActivity activity = generateValidObject();
				activity.setId(ActivityDataUtils.ACTIVITY_INVALID);

				// Attempt to delete
				final StravaActivity deletedActivity = deleter().delete(TestUtils.stravaWithFullAccess(), activity);

				// Failure
				assertNull("Successfully deleted an activity " + ActivityDataUtils.ACTIVITY_INVALID + " that does not exist!", deletedActivity); //$NON-NLS-1$ //$NON-NLS-2$
			});
		}

	}

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can't run this test if we don't have permission to delete activities from Strava
        if (javaStravaApplicationConfig.getAllowsActivityDelete()) {

			RateLimitedTestRunner.run(() -> {
				// Fake activity
				final StravaActivity activity = generateValidObject();
				activity.setId(ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER);

				// Attempt to delete
				final StravaActivity deletedActivity;
				try {
					deletedActivity = deleter().delete(TestUtils.stravaWithFullAccess(), activity);
				} catch (final UnauthorizedException e) {
					// Expected
					return;
				}

				// Failure
				fail("Successfully deleted an activity " + deletedActivity.getId() + " that is private and belongs to another user!"); //$NON-NLS-1$ //$NON-NLS-2$
			});

		}
	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can't run this test if we don't have permission to delete activities from Strava
        if (javaStravaApplicationConfig.getAllowsActivityDelete()) {
			RateLimitedTestRunner.run(() -> {
				// Generate test data
				final StravaActivity activity = generateValidObject();
				activity.setPrivateActivity(Boolean.TRUE);
				final StravaActivity createdActivity = creator().create(TestUtils.stravaWithFullAccess(), activity);

				// Now try to delete it
				try {
					deleter().delete(TestUtils.stravaWithWriteAccess(), createdActivity);
				} catch (final UnauthorizedException e) {
					// Expected
					deleter().delete(TestUtils.stravaWithFullAccess(), createdActivity);
					return;
				}

				// Fail
				fail("Successfully deleted a private activity, but don't have view_private scope in token!"); //$NON-NLS-1$

				// If that worked, it's all good
				return;
			});
		}
	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Can't run this test if we don't have permission to delete activities from Strava
        if (javaStravaApplicationConfig.getAllowsActivityDelete()) {

			RateLimitedTestRunner.run(() -> {
				// Generate test data
				final StravaActivity activity = generateValidObject();
				activity.setPrivateActivity(Boolean.TRUE);
				final StravaActivity createdActivity = creator().create(TestUtils.stravaWithFullAccess(), activity);

				// Now delete it
				deleter().delete(TestUtils.stravaWithFullAccess(), createdActivity);

				// If that worked, it's all good
				return;
			});
		}
	}

	@Override
	protected void validate(StravaActivity object) {
		ActivityDataUtils.validate(object);
	}

}
