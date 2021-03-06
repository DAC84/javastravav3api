package javastrava.service.impl.clubservice;

import javastrava.model.StravaClubAnnouncement;
import javastrava.service.standardtests.ListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.data.ClubDataUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAnnouncementsTest extends ListMethodTest<StravaClubAnnouncement, Integer> {
	@Override
	protected Class<StravaClubAnnouncement> classUnderTest() {
		return StravaClubAnnouncement.class;
	}

	@Override
	public Integer idInvalid() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	public Integer idPrivateBelongsToOtherUser() {
		return ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID;
	}

	@Override
	public Integer idValidWithEntries() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	public Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaClubAnnouncement, Integer> lister() {
		return ((strava, id) -> strava.listClubAnnouncements(id));
	}

	@Override
	protected void validate(StravaClubAnnouncement object) {
		ClubDataUtils.validateClubAnnouncement(object);
	}

}
