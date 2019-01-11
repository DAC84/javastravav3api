package api.apicheck;

import javastrava.api.API;
import javastrava.api.ChallengeAPI;
import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.StravaChallenge;
import javastrava.service.standardtests.data.ChallengeDataUtils;
import javastrava.utils.TestUtils;
import org.junit.Test;
import retrofit.client.Response;

/**
 * <p>
 * Check that the API returns no more data than that which is configured in the model
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ChallengeAPITest {
	private static ChallengeAPI api() {
		return API.instance(ChallengeAPI.class, TestUtils.getValidToken());
	}

	JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();

	/**
	 * <p>
	 * Test the {@link API#getChallenge(Integer)} endpoint
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getChallenge() throws Exception {
		// Can only run the test if the challenges endpoint is enabled
		if (javaStravaApplicationConfig.getAllowsChallenges()) {
			final Response response = api().getChallengeRaw(ChallengeDataUtils.CHALLENGE_VALID_ID);
			ResponseValidator.validate(response, StravaChallenge.class, "getChallenge"); //$NON-NLS-1$
		}
	}

	/**
	 * <p>
	 * Test the {@link API#listJoinedChallenges()} endpoint
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listJoinedChallenges() throws Exception {
		// Can only run the test if the challenges endpoint is enabled
		if (javaStravaApplicationConfig.getAllowsChallenges()) {
			final Response response = api().listJoinedChallengesRaw();
			ResponseValidator.validate(response, StravaChallenge.class, "listJoinedChallenges"); //$NON-NLS-1$
		}
	}
}
