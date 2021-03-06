package javastrava.service.impl.segmentservice;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaSegment;
import javastrava.service.Strava;
import javastrava.service.standardtests.PagingListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.callbacks.PagingListCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.service.standardtests.data.SegmentDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#listAuthenticatedAthleteStarredSegments()} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteStarredSegmentsTest extends PagingListMethodTest<StravaSegment, Integer> {
	@Override
	protected Class<StravaSegment> classUnderTest() {
		return StravaSegment.class;
	}

	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaSegment, Integer> lister() {
		return ((strava, id) -> strava.listAuthenticatedAthleteStarredSegments());
	}

	@Override
	protected PagingListCallback<StravaSegment, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAuthenticatedAthleteStarredSegments(paging));
	}

	@Test
	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = lister().getList(TestUtils.strava(), null);
			for (final StravaSegment segment : segments) {
				if ((segment.getPrivateSegment() != null) && segment.getPrivateSegment().equals(Boolean.TRUE)) {
					fail("Returned at least one private starred segment"); //$NON-NLS-1$
				}
			}
		});
	}

	@Test
	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegment> segments = lister().getList(TestUtils.stravaWithViewPrivate(), null);
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getPrivateSegment() == Boolean.TRUE) {
					pass = true;
				}
			}
			assertTrue(pass);
		});
	}

	@Override
	protected void validate(final StravaSegment segment) {
		SegmentDataUtils.validateSegment(segment);

	}

}
