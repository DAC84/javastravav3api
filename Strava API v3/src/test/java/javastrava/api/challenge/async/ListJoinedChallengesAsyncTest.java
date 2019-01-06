package javastrava.api.challenge.async;

import javastrava.api.API;
import javastrava.model.StravaChallenge;
import javastrava.api.callback.APIListCallback;
import javastrava.api.challenge.ListJoinedChallengesTest;

/**
 * <p>
 * Specific tests and configuration for {@link API#listJoinedChallengesAsync()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListJoinedChallengesAsyncTest extends ListJoinedChallengesTest {

	@Override
	protected APIListCallback<StravaChallenge, Integer> listCallback() {
		return ((api, id) -> api.listJoinedChallengesAsync().get());
	}

}
