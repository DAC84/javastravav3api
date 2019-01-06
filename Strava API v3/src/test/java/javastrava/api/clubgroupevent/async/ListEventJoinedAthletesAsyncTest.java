package javastrava.api.clubgroupevent.async;

import javastrava.api.API;
import javastrava.model.StravaAthlete;
import javastrava.api.callback.APIListCallback;
import javastrava.api.clubgroupevent.ListEventJoinedAthletesTest;
import javastrava.api.util.ArrayCallback;
import javastrava.service.standardtests.data.ClubGroupEventDataUtils;

/**
 * Test and configuration for {@link API#listEventJoinedAthletesAsync(Integer, Integer, Integer)}
 *
 * @author Dan Shannon
 *
 */
public class ListEventJoinedAthletesAsyncTest extends ListEventJoinedAthletesTest {

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listEventJoinedAthletesAsync(id, null, null).get();
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listEventJoinedAthletesAsync(ClubGroupEventDataUtils.CLUB_EVENT_VALID_ID, paging.getPage(), paging.getPageSize()).get();
	}

}
