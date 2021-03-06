/**
 *
 */
package javastrava.api.segment.async;

import javastrava.model.StravaSegment;
import javastrava.api.callback.APIListCallback;
import javastrava.api.segment.ListStarredSegmentsTest;
import javastrava.api.util.ArrayCallback;

/**
 * @author Dan Shannon
 *
 */
public class ListStarredSegmentsAsyncTest extends ListStarredSegmentsTest {
	/**
	 * @see test.api.segment.ListStarredSegmentsTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaSegment, Integer> listCallback() {
		return (api, id) -> api.listStarredSegmentsAsync(id, null, null).get();
	}

	/**
	 * @see test.api.segment.ListStarredSegmentsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegment> pagingCallback() {
		return paging -> api().listStarredSegmentsAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}
}
