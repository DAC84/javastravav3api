package javastrava.service.impl.challengeservice;

import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.StravaChallenge;
import javastrava.service.Strava;
import javastrava.service.standardtests.GetMethodTest;
import javastrava.service.standardtests.callbacks.GetCallback;
import javastrava.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests for {@link Strava#getChallenge(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetChallengeTest extends GetMethodTest<StravaChallenge, Integer> {
    private JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();

	@Override
	protected Integer getIdInvalid() {
		return ChallengeDataUtils.CHALLENGE_INVALID_ID;
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
		return ChallengeDataUtils.CHALLENGE_VALID_ID;
	}

	@Override
	protected GetCallback<StravaChallenge, Integer> getter() {
		return ChallengeDataUtils.getter();
	}

	@Override
	public void testGetInvalidId() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.testGetInvalidId();
		}
	}

	@Override
	public void testGetNullId() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.testGetNullId();
		}
	}

	@Override
	public void testGetValidId() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.testGetValidId();
		}
	}

	@Override
	public void testInvalidId() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.testInvalidId();
		}
	}

	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.testPrivateBelongsToOtherUser();
		}
	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.testPrivateWithNoViewPrivateScope();
		}
	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.testPrivateWithViewPrivateScope();
		}
	}

	@Override
	protected void validate(StravaChallenge object) {
		ChallengeDataUtils.validate(object);

	}

}
