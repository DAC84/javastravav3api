package javastrava.api.athlete;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import javastrava.api.APIGetTest;
import javastrava.api.callback.APIGetCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for {@link API#getAuthenticatedAthlete()}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAuthenticatedAthleteTest extends APIGetTest<StravaAthlete, Integer> {
	@Override
	protected APIGetCallback<StravaAthlete, Integer> getter() {
		return ((api, id) -> api.getAuthenticatedAthlete());
	}

	/**
	 * @see javastrava.api.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return null;
	}

	/**
	 * @see javastrava.api.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see javastrava.api.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.APITest#validate(Object)
	 */
	@Override
	protected void validate(final StravaAthlete result) throws Exception {
		AthleteDataUtils.validateAthlete(result);

	}

	/**
	 * @see javastrava.api.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see javastrava.api.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
