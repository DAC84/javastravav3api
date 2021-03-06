package api.apicheck;

import org.junit.Test;

import javastrava.api.API;
import javastrava.api.AthleteAPI;
import javastrava.model.StravaAthlete;
import javastrava.model.StravaSegmentEffort;
import javastrava.model.StravaStatistics;
import retrofit.client.Response;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class AthleteAPITest {
	private static AthleteAPI api() {
		return API.instance(AthleteAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test for {@link AthleteAPI#getAthleteRaw(Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getAthlete() throws Exception {
		final Response response = api().getAthleteRaw(AthleteDataUtils.ATHLETE_VALID_ID);
		ResponseValidator.validate(response, StravaAthlete.class, "getAthlete"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link AthleteAPI#getAuthenticatedAthleteRaw()}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getAuthenticatedAthlete() throws Exception {
		final Response response = api().getAuthenticatedAthleteRaw();
		ResponseValidator.validate(response, StravaAthlete.class, "getAuthenticatedAthlete"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link AthleteAPI#getStatisticsRaw(Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getStatistics() throws Exception {
		final Response response = api().getStatisticsRaw(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
		ResponseValidator.validate(response, StravaStatistics.class, "getStatistics"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link AthleteAPI#listAthleteFriendsRaw(Integer, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listAthleteFriends() throws Exception {
		final Response response = api().listAthleteFriendsRaw(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listAthleteFriends"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link AthleteAPI#listAthleteKOMsRaw(Integer, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listAthleteKOMs() throws Exception {
		final Response response = api().listAthleteKOMsRaw(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID, null, null);
		ResponseValidator.validate(response, StravaSegmentEffort.class, "listAthleteKOMs"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link AthleteAPI#listAthletesBothFollowingRaw(Integer, Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listAthletesBothFollowing() throws Exception {
		final Response response = api().listAthletesBothFollowingRaw(AthleteDataUtils.ATHLETE_VALID_ID, null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listAthletesNothFollowing"); //$NON-NLS-1$
	}

	/**
	 * Test for {@link AthleteAPI#listAuthenticatedAthleteFriendsRaw(Integer, Integer)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_listAuthenticatedAthleteFriends() throws Exception {
		final Response response = api().listAuthenticatedAthleteFriendsRaw(null, null);
		ResponseValidator.validate(response, StravaAthlete.class, "listAuthenticatedAthleteFriends"); //$NON-NLS-1$
	}

}
