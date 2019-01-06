package javastrava.api.club.async;

import javastrava.model.StravaAthlete;
import javastrava.api.callback.APIListCallback;
import javastrava.api.club.ListClubAdminsTest;
import javastrava.api.util.ArrayCallback;
import javastrava.service.standardtests.data.ClubDataUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAdminsAsyncTest extends ListClubAdminsTest {

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listClubAdminsAsync(id, null, null).get();
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listClubAdminsAsync(ClubDataUtils.CLUB_VALID_ID, paging.getPage(), paging.getPageSize()).get();
	}

}
