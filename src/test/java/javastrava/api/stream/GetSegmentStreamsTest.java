package javastrava.api.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaStream;
import javastrava.model.reference.StravaStreamResolutionType;
import javastrava.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.model.reference.StravaStreamType;
import javastrava.service.exception.BadRequestException;
import javastrava.api.APIGetTest;
import javastrava.api.callback.APIGetCallback;
import api.issues.strava.Issue87;
import api.issues.strava.Issue89;
import api.issues.strava.Issue90;
import javastrava.service.standardtests.data.SegmentDataUtils;
import javastrava.service.standardtests.data.StreamDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#getSegmentStreams(Integer, String, StravaStreamResolutionType, StravaStreamSeriesDownsamplingType)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetSegmentStreamsTest extends APIGetTest<StravaStream[], Integer> {
	/**
	 * @return List of all valid stream types that can be requested
	 */
	protected static String getAllStreamTypes() {
		final StravaStreamType[] types = StravaStreamType.values();
		String list = ""; //$NON-NLS-1$

		for (final StravaStreamType type : types) {
			if (type != StravaStreamType.UNKNOWN) {
				list = list + type.getValue() + ","; //$NON-NLS-1$
			}
		}
		return list;
	}

	/**
	 * Validate an array of streams
	 * 
	 * @param streams
	 *            The streams to be validated
	 */
	protected static void validateArray(final StravaStream[] streams) {
		for (final StravaStream stream : streams) {
			StreamDataUtils.validateStream(stream);
		}
	}

	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		if (new Issue87().isIssue()) {
			return;
		}

		super.get_privateWithoutViewPrivate();
	}

	@Override
	protected APIGetCallback<StravaStream[], Integer> getter() {
		return ((api, id) -> api.getSegmentStreams(id, StravaStreamType.DISTANCE.toString(), null, null));
	}

	@Override
	protected Integer invalidId() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

	@Override
	protected Integer privateId() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	/**
	 * All stream types
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentStreams_allStreamTypes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), null, null);
			validateArray(streams);
		});
	}

	/**
	 * Downsampled by distance
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentStreams_downsampledByDistance() throws Exception {
		RateLimitedTestRunner.run(() -> {
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if ((resolutionType != StravaStreamResolutionType.UNKNOWN) && (resolutionType != null)) {
					final StravaStream[] streams = api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.DISTANCE);
					validateArray(streams);
				}
			}
		});
	}

	/**
	 * Downsampled by time - can't be done for segment streams as there's no time element
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentStreams_downsampledByTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue89().isIssue()) {
				return;
			}
			for (final StravaStreamResolutionType resolutionType : StravaStreamResolutionType.values()) {
				if (resolutionType != StravaStreamResolutionType.UNKNOWN) {
					try {
						api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), resolutionType, StravaStreamSeriesDownsamplingType.TIME);
					} catch (final BadRequestException e) {
						// expected
						return;
					}
					fail("Can't return a segment stream which is downsampled by TIME!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * Invalid downsample resolution
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentStreams_invalidDownsampleResolution() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.UNKNOWN, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample resolution"); //$NON-NLS-1$
		});
	}

	/**
	 * Invalid downsample type (i.e. not distance or time)
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentStreams_invalidDownsampleType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, getAllStreamTypes(), StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.UNKNOWN);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Didn't throw an exception when asking for an invalid downsample type"); //$NON-NLS-1$
		});
	}

	/**
	 * Invalid stream type
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentStreams_invalidStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue90().isIssue()) {
				return;
			}
			try {
				api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, StravaStreamType.UNKNOWN.toString(), null, null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}
			fail("Should have got a BadRequestException, but didn't"); //$NON-NLS-1$
		});
	}

	/**
	 * Only one stream type
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSegmentStreams_oneStreamType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaStream[] streams = api().getSegmentStreams(SegmentDataUtils.SEGMENT_VALID_ID, StravaStreamType.DISTANCE.toString(), null, null);
			assertNotNull(streams);
			assertEquals(1, streams.length);
			assertEquals(StravaStreamType.DISTANCE, streams[0].getType());
			validateArray(streams);
		});
	}

	@Override
	protected void validate(final StravaStream[] result) throws Exception {
		for (final StravaStream stream : result) {
			StreamDataUtils.validateStream(stream);
		}

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
