package javastrava.api.club.async;

import static org.junit.Assert.assertNotNull;

import javastrava.model.StravaClubAnnouncement;
import javastrava.api.callback.APIListCallback;
import javastrava.api.club.ListClubAnnouncementsTest;
import javastrava.service.standardtests.data.ClubDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAnnouncementsAsyncTest extends ListClubAnnouncementsTest {
	/**
	 * @see javastrava.api.club.ListClubAnnouncementsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaClubAnnouncement, Integer> listCallback() {
		return (api, id) -> api.listClubAnnouncementsAsync(id).get();
	}

	@Override
	public void testListClubAnnouncements_privateClubMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClubAnnouncement[] announcements = api().listClubAnnouncementsAsync(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID).get();
			assertNotNull(announcements);
		});
	}

}
