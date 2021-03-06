/**
 *
 */
package javastrava.api.segment.async;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaSegment;
import javastrava.api.callback.APIListCallback;
import javastrava.api.segment.ListAuthenticatedAthleteStarredSegmentsTest;
import javastrava.api.util.ArrayCallback;
import api.issues.strava.Issue71;
import javastrava.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#listAuthenticatedAthleteStarredSegmentsAsync(Integer, Integer)}
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteStarredSegmentsAsyncTest extends ListAuthenticatedAthleteStarredSegmentsTest {
	@SuppressWarnings("boxing")
	@Override
	@Test
	public void list_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment[] segments = this.listCallback().list(apiWithViewPrivate(), null);
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getPrivateSegment()) {
					pass = true;
				}
			}
			assertTrue(pass);
		});
	}

	@Override
	public void list_privateWithoutViewPrivate() throws Exception {
		// TODO This is a workaround for issue javastravav3api#71
		final Issue71 issue71 = new Issue71();
		if (issue71.isIssue()) {
			return;
		}
		// End of workaround

		final StravaSegment[] segments = this.listCallback().list(api(), null);
		for (final StravaSegment segment : segments) {
			if ((segment.getPrivateSegment() != null) && segment.getPrivateSegment().equals(Boolean.TRUE)) {
				fail("Returned at least one private starred segment"); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected APIListCallback<StravaSegment, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteStarredSegmentsAsync(null, null).get();
	}

	@Override
	protected ArrayCallback<StravaSegment> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteStarredSegmentsAsync(paging.getPage(), paging.getPageSize()).get();
	}

}
