package javastrava.api.activity.async;

import api.issues.strava.Issue72;
import javastrava.api.API;
import javastrava.api.activity.UpdateActivityTest;
import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.StravaActivity;
import javastrava.model.StravaActivityUpdate;
import javastrava.model.reference.StravaActivityType;
import javastrava.model.reference.StravaResourceState;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.StravaUnknownAPIException;
import javastrava.service.exception.UnauthorizedException;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.service.standardtests.data.GearDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import static org.junit.Assert.*;

/**
 * <p>
 * Specific tests for {@link API#updateActivityAsync(Long, StravaActivityUpdate)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class UpdateActivityAsyncTest extends UpdateActivityTest {

    JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();

	/**
	 * @param activity
	 *            The initial activity to create
	 * @param update
	 *            The update to apply
	 * @return The activity as it was created on Strava (although it is ALWAYS deleted again)
	 * @throws Exception
	 *             if not found
	 */
	@Override
	protected StravaActivity createUpdateAndDelete(final StravaActivity activity, final StravaActivityUpdate update) throws Exception {
        if (!javaStravaApplicationConfig.getAllowsActivityDelete()) {
			fail("Can't create test data because Strava will not allow it to be deleted"); //$NON-NLS-1$
		}
		final StravaActivity response = apiWithFullAccess().createManualActivityAsync(activity).get();
		StravaActivity updateResponse = null;
		try {
			updateResponse = apiWithFullAccess().updateActivityAsync(response.getId(), update).get();
		} catch (final Exception e) {
			forceDeleteActivity(response);
			throw e;
		}
		updateResponse = waitForUpdateCompletion(updateResponse);
		forceDeleteActivity(response);
		return updateResponse;

	}

	/**
	 * <p>
	 * Test attempting to update an activity using a token that doesn't have write access
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Override
	public void testUpdateActivity_accessTokenDoesNotHaveWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final TextProducer text = Fairy.create().textProducer();
			final StravaActivity activity = api().getActivityAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null).get();
			activity.setDescription(text.paragraph(1));

			try {
				api().updateActivityAsync(activity.getId(), new StravaActivityUpdate(activity)).get();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Successfully updated an activity despite not having write access"); //$NON-NLS-1$
		});
	}

	@Override
	public void testUpdateActivity_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_invalidActivity"); //$NON-NLS-1$
			activity.setId(ActivityDataUtils.ACTIVITY_INVALID);

			StravaActivity response = null;
			try {
				response = apiWithWriteAccess().updateActivityAsync(activity.getId(), new StravaActivityUpdate(activity)).get();
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			assertNull("Updated an activity which doesn't exist?", response); //$NON-NLS-1$
		});
	}

	@Override
	public void testUpdateActivity_nullUpdate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				apiWithWriteAccess().updateActivityAsync(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null).get();
			} catch (final StravaUnknownAPIException e) {
				// Expected
				return;
			}
			fail("Updated an activity with a null update"); //$NON-NLS-1$
		});
	}

	@Override
	public void testUpdateActivity_tooManyActivityAttributes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_tooManyActivityAttributes"); //$NON-NLS-1$
			final StravaActivity update = new StravaActivity();

			final Float cadence = Float.valueOf(67.2f);
			update.setAverageCadence(cadence);

			// Do all the interaction with the Strava API at once
			final StravaActivity stravaResponse = createUpdateAndDelete(activity, new StravaActivityUpdate(update));

			// Test the results
			assertNull(stravaResponse.getAverageCadence());
			validate(stravaResponse);
		});
	}

	@Override
	public void testUpdateActivity_unauthenticatedAthletesActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = apiWithWriteAccess().getActivityAsync(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, null).get();

			try {
				apiWithWriteAccess().updateActivityAsync(activity.getId(), new StravaActivityUpdate(activity)).get();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Updated an activity which belongs to someone else??"); //$NON-NLS-1$
		});
	}

	@Override
	public void testUpdateActivity_validUpdateAllAtOnce() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateAllAtOnce"); //$NON-NLS-1$

			final TextProducer text = Fairy.create().textProducer();
			final String description = text.sentence();
			final String name = text.sentence();
			final StravaActivityType type = StravaActivityType.RIDE;
			final Boolean privateActivity = Boolean.TRUE;
			final Boolean commute = Boolean.TRUE;
			final Boolean trainer = Boolean.TRUE;
			final String gearId = GearDataUtils.GEAR_VALID_ID;

			final StravaActivityUpdate update = new StravaActivityUpdate();
			update.setDescription(description);
			update.setCommute(commute);
			update.setGearId(gearId);
			update.setName(name);
			update.setPrivateActivity(privateActivity);
			update.setTrainer(trainer);
			update.setType(type);

			// Do all the interaction with the Strava API at once
			final StravaActivity updateResponse = createUpdateAndDelete(activity, update);

			// Validate the results
			validate(updateResponse);
			assertEquals(description, updateResponse.getDescription());

			assertEquals(gearId, updateResponse.getGearId());
			assertEquals(name, updateResponse.getName());
			assertEquals(privateActivity, updateResponse.getPrivateActivity());
			assertEquals(trainer, updateResponse.getTrainer());
			assertEquals(type, updateResponse.getType());

			assertEquals(commute, updateResponse.getCommute());
		});
	}

	@Override
	public void testUpdateActivity_validUpdateCommute() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateCommute"); //$NON-NLS-1$
			StravaActivity updateResponse = null;

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final Boolean commute = Boolean.TRUE;
			update.setCommute(commute);

			// Do all the interaction with the Strava API at once
			updateResponse = createUpdateAndDelete(activity, update);

			// Validate the results
			ActivityDataUtils.validate(updateResponse);
			assertEquals(commute, updateResponse.getCommute());
		});
	}

	@Override
	public void testUpdateActivity_validUpdateDescription() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up test date
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateDescription"); //$NON-NLS-1$

			final TextProducer text = Fairy.create().textProducer();
			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String description = text.sentence();
			update.setDescription(description);

			// Do all the interaction with the Strava API at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Test the response
			ActivityDataUtils.validate(response);
			assertEquals(description, response.getDescription());
		});
	}

	@Override
	public void testUpdateActivity_validUpdateGearId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// set up the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateGearId"); //$NON-NLS-1$
			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String gearId = GearDataUtils.GEAR_VALID_ID;
			update.setGearId(gearId);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			ActivityDataUtils.validate(response);
			assertEquals(gearId, response.getGearId());
		});
	}

	@Override
	public void testUpdateActivity_validUpdateGearIDNone() throws Exception {
		RateLimitedTestRunner.run(() -> {

			// Set up all the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateGearIdNone"); //$NON-NLS-1$

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String gearId = "none"; //$NON-NLS-1$
			update.setGearId(gearId);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			ActivityDataUtils.validate(response);
			assertNull(response.getGearId());
		});
	}

	@Override
	public void testUpdateActivity_validUpdateName() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateName"); //$NON-NLS-1$

			final TextProducer text = Fairy.create().textProducer();

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String sentence = text.sentence();
			update.setName(sentence);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			ActivityDataUtils.validate(response);
			assertEquals(sentence, response.getName());

		});
	}

	@Override
	public void testUpdateActivity_validUpdatePrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// set up the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdatePrivate"); //$NON-NLS-1$

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final Boolean privateFlag = Boolean.TRUE;
			update.setPrivateActivity(privateFlag);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			ActivityDataUtils.validate(response);
			assertEquals(privateFlag, response.getPrivateActivity());
		});
	}

	@Override
	public void testUpdateActivity_validUpdatePrivateNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
            if (!javaStravaApplicationConfig.getAllowsActivityDelete()) {
				fail("Can't create test data because Strava will not allow it to be deleted"); //$NON-NLS-1$
			}

			if (new Issue72().isIssue()) {
				return;
			}

			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdatePrivateNoViewPrivate"); //$NON-NLS-1$
			activity.setPrivateActivity(Boolean.TRUE);

			// Create the activity
			StravaActivity response = apiWithFullAccess().createManualActivityAsync(activity).get();
			assertEquals(Boolean.TRUE, response.getPrivateActivity());

			// Try to update it without view private
			activity.setDescription("Updated description"); //$NON-NLS-1$
			try {
				response = apiWithWriteAccess().updateActivityAsync(response.getId(), new StravaActivityUpdate(activity)).get();
			} catch (final UnauthorizedException e) {
				// expected
				forceDeleteActivity(response);
				return;
			}
			forceDeleteActivity(response);
			fail("Updated private activity without view_private authorisation"); //$NON-NLS-1$

		});
	}

	@Override
	public void testUpdateActivity_validUpdateTrainer() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateTrainer"); //$NON-NLS-1$

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final Boolean trainer = Boolean.TRUE;
			update.setTrainer(trainer);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			ActivityDataUtils.validate(response);
			assertEquals(trainer, response.getTrainer());
		});
	}

	@Override
	public void testUpdateActivity_validUpdateType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateType"); //$NON-NLS-1$
			activity.setType(StravaActivityType.ALPINE_SKI);

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final StravaActivityType type = StravaActivityType.RIDE;
			update.setType(type);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			ActivityDataUtils.validate(response);
			assertEquals(type, response.getType());
		});
	}

	/**
	 * @param updateResponse
	 *            Strava's response to the update API call
	 * @return The updated activity
	 */
	@Override
	protected StravaActivity waitForUpdateCompletion(final StravaActivity updateResponse) throws Exception {
		int i = 0;
		StravaActivity response = null;
		while (i < 600) {
			response = apiWithFullAccess().getActivityAsync(updateResponse.getId(), null).get();
			i++;
			if (response.getResourceState() != StravaResourceState.UPDATING) {
				return response;
			}
		}
		return response;
	}

}
