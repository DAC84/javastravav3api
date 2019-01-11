package javastrava.service.impl.challengeservice;

import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.StravaChallenge;
import javastrava.service.Strava;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import javastrava.service.standardtests.MethodTest;
import javastrava.service.standardtests.callbacks.GetCallback;
import javastrava.service.standardtests.data.ChallengeDataUtils;
import javastrava.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * <p>
 * Specific tests and config for {@link Strava#joinChallenge(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class LeaveChallengeTest extends MethodTest<StravaChallenge, Integer> {

    JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();


	/**
	 * Callback used to leave the challenge
	 *
	 * @return The callback
	 */
	@Override
	protected GetCallback<StravaChallenge, Integer> getter() {
		return (strava, id) -> {
			strava.leaveChallenge(id);
			return null;
		};
	}

	/**
	 * Test that you can't leave a challenge that doesn't exist
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testLeaveChallenge_invalidChallenge() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			// Try to leave it
			final Integer id = ChallengeDataUtils.CHALLENGE_INVALID_ID;
			try {
				getter().get(TestUtils.stravaWithWriteAccess(), id);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}

			// If it works, it fails
			fail("Succeeded in leaving a non-existent challenge"); //$NON-NLS-1$
		}
	}

	/**
	 * Test that you can successfully leave a valid challenge using a token with write access
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testLeaveChallenge_validChallenge() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {

			// Join the challenge first, to be sure
			final Integer id = ChallengeDataUtils.CHALLENGE_VALID_ID;
			final Strava strava = TestUtils.stravaWithWriteAccess();
			strava.joinChallenge(id);

			// Then leave it
			getter().get(strava, id);

			// If it works, it works.
		}
	}

	/**
	 * Test that you can't successfully join a valid challenge using a token without write access
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testLeaveChallenge_validChallengeNoWriteAccess() throws Exception {
		// Can't run the test unless there's permission at the Strava end to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {

			// Join the challenge first, to be sure
			final Integer id = ChallengeDataUtils.CHALLENGE_VALID_ID;
			TestUtils.stravaWithWriteAccess().joinChallenge(id);

			// Then try to join it
			try {
				getter().get(TestUtils.strava(), id);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// If it works, it fails
			fail("Succeeded in joining a challenge using a token that doesn't have WRITE scope"); //$NON-NLS-1$
		}
	}

	@Override
	protected void validate(StravaChallenge result) {
		ChallengeDataUtils.validate(result);
	}

}
