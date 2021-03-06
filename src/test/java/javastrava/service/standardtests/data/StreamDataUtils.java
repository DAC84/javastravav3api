package javastrava.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javastrava.model.StravaMapPoint;
import javastrava.model.StravaStream;
import javastrava.model.reference.StravaStreamResolutionType;
import javastrava.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.model.reference.StravaStreamType;

/**
 * <p>
 * Test data generation and validation utilities for Streams data
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StreamDataUtils {

	/**
	 * Validate the structure and content of a Strava stream
	 *
	 * @param stream
	 *            The stream to be validated
	 */
	@SuppressWarnings("boxing")
	public static void validateStream(final StravaStream stream) {
		assertNotNull(stream);
		assertNotNull(stream.getOriginalSize());
		assertTrue(stream.getOriginalSize() > 0);
	
		if (stream.getResolution() != null) {
			assertFalse(stream.getResolution() == StravaStreamResolutionType.UNKNOWN);
		}
		assertNotNull(stream.getSeriesType());
		assertFalse(stream.getSeriesType() == StravaStreamSeriesDownsamplingType.UNKNOWN);
		assertNotNull(stream.getType());
		assertFalse(stream.getType() == StravaStreamType.UNKNOWN);
	
		if (stream.getType() == StravaStreamType.MOVING) {
			assertNull(stream.getData());
			assertNull(stream.getMapPoints());
			assertNotNull(stream.getMoving());
			if (stream.getResolution() != null) {
				assertTrue(stream.getResolution().getSize() >= stream.getMoving().size());
				assertTrue(stream.getOriginalSize().intValue() >= stream.getMoving().size());
			} else {
				assertEquals(stream.getOriginalSize().intValue(), stream.getMoving().size());
			}
			for (final Boolean moving : stream.getMoving()) {
				assertNotNull(moving);
			}
			return;
		} else if (stream.getType() == StravaStreamType.MAPPOINT) {
			assertNull(stream.getData());
			assertNull(stream.getMoving());
			assertNotNull(stream.getMapPoints());
			if (stream.getResolution() != null) {
				assertTrue(stream.getResolution().getSize() >= stream.getMapPoints().size());
				assertTrue(stream.getOriginalSize().intValue() >= stream.getMapPoints().size());
			} else {
				assertEquals(stream.getOriginalSize().intValue(), stream.getMapPoints().size());
			}
			for (final StravaMapPoint point : stream.getMapPoints()) {
				MapDataUtils.validate(point);
			}
			return;
		} else {
			assertNull(stream.getMapPoints());
			assertNull(stream.getMoving());
			assertNotNull(stream.getData());
			if (stream.getResolution() != null) {
				assertTrue(
						"Resolution restricted to " + stream.getResolution().getSize() + " but returned " //$NON-NLS-1$ //$NON-NLS-2$
								+ stream.getData().size() + " data points", //$NON-NLS-1$
						stream.getResolution().getSize() >= stream.getData().size());
				assertTrue(stream.getOriginalSize().intValue() >= stream.getData().size());
			} else {
				assertEquals(stream.getOriginalSize().intValue(), stream.getData().size());
			}
			for (final Float data : stream.getData()) {
				assertNotNull(data);
			}
		}
	}

}
