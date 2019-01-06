package javastrava.api.club.async;

import javastrava.model.StravaClubEvent;
import javastrava.api.callback.APIListCallback;
import javastrava.api.club.ListClubEventsTest;

/**
 * @author Dan Shannon
 *
 */
public class ListClubEventsAsyncTest extends ListClubEventsTest {
	/**
	 * @see javastrava.api.club.ListClubEventsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaClubEvent, Integer> listCallback() {
		return (api, id) -> api.listClubGroupEventsAsync(id).get();
	}
}
