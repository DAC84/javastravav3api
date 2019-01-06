package javastrava.api.athlete.async;

import static org.junit.Assert.fail;

import javastrava.api.API;
import javastrava.model.StravaStatistics;
import javastrava.service.exception.UnauthorizedException;
import javastrava.api.APIGetTest;
import javastrava.api.callback.APIGetCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests for {@link API#statisticsAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StatisticsAsyncTest extends APIGetTest<StravaStatistics, Integer> {

	@Override
	public void get_validBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().statisticsAsync(validIdBelongsToOtherUser()).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned statistics for another athlete"); //$NON-NLS-1$
		});
	}

	@Override
	protected APIGetCallback<StravaStatistics, Integer> getter() {
		return ((api, id) -> api.statistics(id));
	}

	/**
	 * @see javastrava.api.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
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
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	/**
	 * @see test.api.APITest#validate(Object)
	 */
	@Override
	protected void validate(final StravaStatistics result) throws Exception {
		AthleteDataUtils.validate(result);

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
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}
}
