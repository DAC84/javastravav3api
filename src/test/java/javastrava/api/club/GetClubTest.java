package javastrava.api.club;

import static javastrava.api.APITest.api;
import static org.junit.Assert.assertNotNull;

import javastrava.api.APIGetTest;
import javastrava.api.callback.APIGetCallback;
import javastrava.service.standardtests.data.ClubDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaClub;

/**
 * <p>
 * Specific tests for {@link API#getClub(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetClubTest extends APIGetTest<StravaClub, Integer> {
	@Override
	protected APIGetCallback<StravaClub, Integer> getter() {
		return ((api, id) -> api.getClub(id));
	}

	/**
	 * @see javastrava.api.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return ClubDataUtils.CLUB_INVALID_ID;
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
	 * Private club of which current authenticated athlete is a member
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetClub_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = api().getClub(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(club);
			ClubDataUtils.validate(club, ClubDataUtils.CLUB_PRIVATE_MEMBER_ID, club.getResourceState());
		});
	}

	/**
	 * Private club of which current authenticated athlete is NOT a member
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetClub_privateClubIsNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = api().getClub(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			assertNotNull(club);
		});
	}

	/**
	 * @see javastrava.api.APIGetTest#validate(Object)
	 */
	@Override
	protected void validate(final StravaClub result) throws Exception {
		ClubDataUtils.validate(result);

	}

	/**
	 * @see javastrava.api.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	/**
	 * @see javastrava.api.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
