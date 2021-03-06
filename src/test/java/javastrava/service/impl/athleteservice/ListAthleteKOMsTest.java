package javastrava.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaSegmentEffort;
import javastrava.service.Strava;
import javastrava.service.exception.UnauthorizedException;
import api.issues.strava.Issue175;
import javastrava.service.standardtests.PagingListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.callbacks.PagingListCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.service.standardtests.data.SegmentEffortDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests and configuration for {@link Strava#listAthleteKOMs(Integer, javastrava.util.Paging)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAthleteKOMsTest extends PagingListMethodTest<StravaSegmentEffort, Integer> {
	@Override
	protected Class<StravaSegmentEffort> classUnderTest() {
		return StravaSegmentEffort.class;
	}

	@Override
	protected Integer idInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return AthleteDataUtils.ATHLETE_WITHOUT_KOMS;
	}

	@Override
	protected ListCallback<StravaSegmentEffort, Integer> lister() {
		return ((strava, id) -> strava.listAthleteKOMs(id));
	}

	@Override
	protected PagingListCallback<StravaSegmentEffort, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listAthleteKOMs(id, paging));
	}

	/**
	 * Test listing KOM's for another athlete doesn't return KOM's gained on a private activity
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllAthleteKOMs_otherAthletePrivateActivities() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAthleteKOMs(AthleteDataUtils.ATHLETE_VALID_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * Test listing KOM's for the authenticated athlete doesn't return KOM's gained on a private activity if the token doesn't have view_private scope
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateActivitiesWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAthleteKOMs(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getActivity(effort.getActivity().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private activity!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * Test listing KOM's for the authenticated athlete doesn't return KOM's gained on a private segment if the token doesn't have view_private scope
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAthleteKOMs_authenticatedAthletePrivateSegmentsWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> efforts = TestUtils.strava().listAthleteKOMs(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			for (final StravaSegmentEffort effort : efforts) {
				try {
					TestUtils.strava().getSegment(effort.getSegment().getId());
				} catch (final UnauthorizedException e) {
					fail("Returned KOM's for a private segment!"); //$NON-NLS-1$
				}
			}
		});
	}

	/**
	 * Invalid athlete
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAthleteKOMs_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaSegmentEffort> koms = null;
			koms = TestUtils.strava().listAthleteKOMs(AthleteDataUtils.ATHLETE_INVALID_ID);

			assertNull(koms);
		});
	}

	/**
	 * Valid athlete with some KOM's
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAthleteKOMs_withKOM() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.strava().listAthleteKOMs(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID);
			assertNotNull(koms);
			assertFalse(koms.size() == 0);
			for (final StravaSegmentEffort effort : koms) {
				SegmentEffortDataUtils.validateSegmentEffort(effort);
			}
		});
	}

	/**
	 * Valid athlete with no KOM's
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAthleteKOMs_withoutKOM() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaSegmentEffort> koms = TestUtils.strava().listAthleteKOMs(AthleteDataUtils.ATHLETE_WITHOUT_KOMS);
			assertNotNull(koms);
			assertTrue(koms.size() == 0);
		});
	}

	@Override
	@Test
	public void testPageNumberAndSize() throws Exception {
		assumeFalse(Issue175.issue());

		super.testPageNumberAndSize();
	}

	@Override
	@Test
	public void testPagingIgnoreFirstN() throws Exception {
		assumeFalse(Issue175.issue());

		super.testPagingIgnoreFirstN();
	}

	@Override
	@Test
	public void testPagingIgnoreLastN() throws Exception {
		assumeFalse(Issue175.issue());

		super.testPagingIgnoreLastN();
	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		SegmentEffortDataUtils.validateSegmentEffort(effort);
	}

}
