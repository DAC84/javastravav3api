package javastrava.api.challenge.async;

import javastrava.api.API;
import javastrava.model.StravaChallenge;
import javastrava.api.callback.APIGetCallback;
import javastrava.api.challenge.GetChallengeTest;
import javastrava.service.standardtests.data.ChallengeDataUtils;

/**
 * <p>
 * Specific tests for {@link API#getChallengeAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetChallengeAsyncTest extends GetChallengeTest {

	@Override
	protected APIGetCallback<StravaChallenge, Integer> getter() {
		return ChallengeDataUtils.apiGetterAsync();
	}

}
