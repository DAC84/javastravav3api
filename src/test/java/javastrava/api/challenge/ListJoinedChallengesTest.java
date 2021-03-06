package javastrava.api.challenge;

import javastrava.api.API;
import javastrava.api.APIListTest;
import javastrava.api.callback.APIListCallback;
import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.StravaChallenge;
import javastrava.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests for {@link API#listJoinedChallenges()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListJoinedChallengesTest extends APIListTest<StravaChallenge, Integer> {

    JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();

	@Override
	protected Integer invalidId() {
		return ChallengeDataUtils.CHALLENGE_INVALID_ID;
	}

	@Override
	public void list_invalidParent() throws Exception {
		// Can't run the test if don't have permission to call the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.list_invalidParent();
		}
	}

	@Override
	public void list_private() throws Exception {
		// Can't run the test if don't have permission to call the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.list_private();
		}
	}

	@Override
	public void list_privateBelongsToOtherUser() throws Exception {
		// Can't run the test if don't have permission to call the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.list_privateBelongsToOtherUser();
		}
	}

	@Override
	public void list_privateWithoutViewPrivate() throws Exception {
		// Can't run the test if don't have permission to call the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.list_privateWithoutViewPrivate();
		}
	}

	@Override
	public void list_validBelongsToOtherUser() throws Exception {
		// Can't run the test if don't have permission to call the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.list_validBelongsToOtherUser();
		}
	}

	@Override
	public void list_validParent() throws Exception {
		// Can't run the test if don't have permission to call the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.list_validParent();
		}
	}

	@Override
	public void list_validParentNoChildren() throws Exception {
		// Can't run the test if don't have permission to call the challenges endpoint
        if (javaStravaApplicationConfig.getAllowsChallenges()) {
			super.list_validParentNoChildren();
		}
	}

	@Override
	protected APIListCallback<StravaChallenge, Integer> listCallback() {
		return ((api, id) -> api.listJoinedChallenges());
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
	protected void validateArray(StravaChallenge[] list) throws Exception {
		for (final StravaChallenge challenge : list) {
			validate(challenge);
		}

	}

	@Override
	protected Integer validId() {
		return ChallengeDataUtils.CHALLENGE_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
