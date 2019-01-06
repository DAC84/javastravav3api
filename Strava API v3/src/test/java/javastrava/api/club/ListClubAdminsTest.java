package javastrava.api.club;

import javastrava.model.StravaAthlete;
import javastrava.api.APIPagingListTest;
import javastrava.api.callback.APIListCallback;
import javastrava.api.util.ArrayCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.service.standardtests.data.ClubDataUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAdminsTest extends APIPagingListTest<StravaAthlete, Integer> {

	@Override
	protected Integer invalidId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listClubAdmins(id, null, null);
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listClubAdmins(ClubDataUtils.CLUB_VALID_ID, paging.getPage(), paging.getPageSize());
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaAthlete result) throws Exception {
		AthleteDataUtils.validateAthlete(result);

	}

	@Override
	protected void validateArray(StravaAthlete[] list) throws Exception {
		for (final StravaAthlete athlete : list) {
			AthleteDataUtils.validateAthlete(athlete);
		}

	}

	@Override
	protected Integer validId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
