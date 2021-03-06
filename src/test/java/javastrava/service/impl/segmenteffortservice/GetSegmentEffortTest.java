package javastrava.service.impl.segmenteffortservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.StravaSegmentEffort;
import javastrava.model.reference.StravaResourceState;
import javastrava.service.standardtests.GetMethodTest;
import javastrava.service.standardtests.callbacks.GetCallback;
import javastrava.service.standardtests.data.SegmentEffortDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests for getSegmentEffort methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetSegmentEffortTest extends GetMethodTest<StravaSegmentEffort, Long> {
	@Override
	protected Long getIdInvalid() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_INVALID_ID;
	}

	@Override
	protected Long getIdPrivate() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID;
	}

	@Override
	protected Long getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Long getIdValid() {
		return SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID;
	}

	@Override
	protected GetCallback<StravaSegmentEffort, Long> getter() {
		return ((strava, id) -> strava.getSegmentEffort(id));
	}

	/**
	 * Check that an effort on a private activity is not returned
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentEffort_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = TestUtils.strava().getSegmentEffort(new Long(5735858255L));
			assertNotNull(effort);
			assertEquals(StravaResourceState.PRIVATE, effort.getResourceState());
		});
	}

	/**
	 * Check that an effort on a private segment is not returned
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentEffort_privateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = TestUtils.strava().getSegmentEffort(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID);
			assertNotNull(effort);
			assertEquals(StravaResourceState.PRIVATE, effort.getResourceState());
		});
	}

	@Override
	protected void validate(StravaSegmentEffort object) {
		SegmentEffortDataUtils.validateSegmentEffort(object);
	}

}
