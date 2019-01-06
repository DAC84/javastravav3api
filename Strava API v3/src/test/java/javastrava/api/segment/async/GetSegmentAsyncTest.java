package javastrava.api.segment.async;

import javastrava.model.StravaSegment;
import javastrava.api.callback.APIGetCallback;
import javastrava.api.segment.GetSegmentTest;

/**
 * @author Dan Shannon
 *
 */
public class GetSegmentAsyncTest extends GetSegmentTest {

	@Override
	protected APIGetCallback<StravaSegment, Integer> getter() {
		return ((api, id) -> api.getSegmentAsync(id).get());
	}
}
