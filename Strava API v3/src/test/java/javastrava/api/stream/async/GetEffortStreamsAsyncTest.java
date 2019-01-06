/**
 *
 */
package javastrava.api.stream.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javastrava.model.StravaStream;
import javastrava.model.reference.StravaStreamResolutionType;
import javastrava.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.model.reference.StravaStreamType;
import javastrava.service.exception.BadRequestException;
import javastrava.service.exception.UnauthorizedException;
import javastrava.api.callback.APIGetCallback;
import javastrava.api.stream.GetEffortStreamsTest;
import api.issues.strava.Issue87;
import api.issues.strava.Issue91;
import javastrava.service.standardtests.data.SegmentEffortDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * @author danshannon
 *
 */
public class GetEffortStreamsAsyncTest extends GetEffortStreamsTest {

	@Override
	protected APIGetCallback<StravaStream[], Long> getter() {
		return ((api, id) -> api.getEffortStreamsAsync(id, StravaStreamType.DISTANCE.toString(), null, null).get());
	}

	/**
	 * All stream types
	 */
	@Override
	public void testGetEffortStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), null, null).get();
			validateArray(streams);
		});
	}

	/**
	 * Downsampled by distance
	 */
	@Override
	public void testGetEffortStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api()
							.getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE).get();
					validateArray(streams);
				}
			}
		});
	}

	/**
	 * Downsampled by time
	 */
	@Override
	public void testGetEffortStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					final StravaStream[] streams = api()
							.getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.TIME).get();
					validateArray(streams);
				}
			}
		});
	}

	/**
	 * Invalid downsample resolution
	 */
	@Override
	public void testGetEffortStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution"); //$NON-NLS-1$
		});
	}

	/**
	 * Invalid downsample type (i.e. not distance or time)
	 */
	@Override
	public void testGetEffortStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type"); //$NON-NLS-1$
		});
	}

	/**
	 * Invalid stream type
	 */
	@Override
	public void testGetEffortStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO Workaround for issue javastravav3api#91
			if (new Issue91().isIssue()) {
				return;
			}
			// End of workaround

			try {
				api().getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamType.UNKNOWN.toString(), null, null).get();
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have got an BadRequestException, but didn't"); //$NON-NLS-1$
		});
	}

	/**
	 * Only one stream type
	 */
	@Override
	public void testGetEffortStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID, StravaStreamType.DISTANCE.toString(), null, null).get();
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			validateArray(streams);
		});
	}

	/**
	 * Valid effort for other user
	 */
	@Override
	public void testGetEffortStreams_privateEffortUnauthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID, getAllStreamTypes(), null, null).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned effort streams for a private effort belonging to another user"); //$NON-NLS-1$
		});
	}

	@Override
	public void testGetEffortStreams_privateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO Workaround for issue javastravav3api#87
			if (new Issue87().isIssue()) {
				return;
			}
			// End of workaround

			final StravaStream[] streams = api().getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID, getAllStreamTypes(), null, null).get();
			assertNotNull(streams);
			assertTrue(streams.length == 0);
		});
	}

	@Override
	public void testGetEffortStreams_privateSegmentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = apiWithViewPrivate().getEffortStreamsAsync(SegmentEffortDataUtils.SEGMENT_EFFORT_PRIVATE_ID, getAllStreamTypes(), null, null).get();
			assertNotNull(streams);
			assertFalse(streams.length == 0);
		});
	}
}
