package javastrava.api.segment;

import javastrava.api.API;
import javastrava.model.StravaSegment;
import javastrava.api.APIGetTest;
import javastrava.api.callback.APIGetCallback;
import api.issues.strava.Issue70;
import javastrava.service.standardtests.data.SegmentDataUtils;

/**
 * <p>
 * Tests for {@link API#getSegment(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetSegmentTest extends APIGetTest<StravaSegment, Integer> {
	// 4. Private segment belonging to the authenticated user
	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		// TODO This is a workaround for issue javastravav3api#70
		if (new Issue70().isIssue()) {
			return;
		}
		// End of workaround

		super.get_privateWithoutViewPrivate();
	}

	@Override
	protected APIGetCallback<StravaSegment, Integer> getter() {
		return ((api, id) -> api.getSegment(id));
	}

	/**
	 * @see javastrava.api.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

	/**
	 * @see javastrava.api.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	/**
	 * @see javastrava.api.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	/**
	 * @see test.api.APITest#validate(Object)
	 */
	@Override
	protected void validate(final StravaSegment result) throws Exception {
		SegmentDataUtils.validateSegment(result);

	}

	/**
	 * @see javastrava.api.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

	/**
	 * @see javastrava.api.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
