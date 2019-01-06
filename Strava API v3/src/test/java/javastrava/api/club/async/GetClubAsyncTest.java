package javastrava.api.club.async;

import javastrava.api.API;
import javastrava.api.callback.APIGetCallback;
import javastrava.api.club.GetClubTest;
import javastrava.model.StravaClub;
import javastrava.service.standardtests.data.ClubDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>
 * Specific tests and config for {@link API#getClubAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetClubAsyncTest extends GetClubTest {
	@Override
	protected APIGetCallback<StravaClub, Integer> getter() {
		return ((api, id) -> api.getClubAsync(id).get());
	}

	@Override
	@Test
	public void testGetClub_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = api().getClub(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(club);
			ClubDataUtils.validate(club, ClubDataUtils.CLUB_PRIVATE_MEMBER_ID, club.getResourceState());
		});
	}

	@Override
	@Test
	public void testGetClub_privateClubIsNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = api().getClubAsync(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID).get();
			assertNotNull(club);
		});
	}

}
