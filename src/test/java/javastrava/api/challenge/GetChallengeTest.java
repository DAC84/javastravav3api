package javastrava.api.challenge;

import javastrava.api.API;
import javastrava.api.APIGetTest;
import javastrava.api.callback.APIGetCallback;
import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.StravaChallenge;
import javastrava.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests for {@link API} get challenge methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetChallengeTest extends APIGetTest<StravaChallenge, Integer> {

    JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();


	@Override
	public void get_invalid() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.get_invalid();
		}
	}

	@Override
	public void get_private() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.get_private();
		}
	}

	@Override
	public void get_privateBelongsToOtherUser() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.get_privateBelongsToOtherUser();
		}
	}

	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.get_privateWithoutViewPrivate();
		}
	}

	@Override
	public void get_valid() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.get_valid();
		}
	}

	@Override
	public void get_validBelongsToOtherUser() throws Exception {
		// Can't run the test if we don't have permission to use the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.get_validBelongsToOtherUser();
		}
	}

	@Override
	protected APIGetCallback<StravaChallenge, Integer> getter() {
		return ChallengeDataUtils.apiGetter();
	}

	@Override
	protected Integer invalidId() {
		return ChallengeDataUtils.CHALLENGE_INVALID_ID;
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaChallenge result) throws Exception {
		ChallengeDataUtils.validate(result);

	}

	@Override
	protected Integer validId() {
		return ChallengeDataUtils.CHALLENGE_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
