package api.issues.strava;

import javastrava.api.API;
import javastrava.model.StravaSegment;
import javastrava.model.StravaSegmentEffort;
import javastrava.model.StravaSegmentLeaderboard;
import javastrava.model.StravaSegmentLeaderboardEntry;
import javastrava.service.impl.SegmentServiceImpl;
import javastrava.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class Issue32 extends IssueTest {
	@SuppressWarnings("boxing")
	private static boolean isKom(final StravaSegment segment, final Integer athleteId) {
        final StravaSegmentLeaderboard leaderboard = new SegmentServiceImpl(new API(TestUtils.getValidToken())).getSegmentLeaderboard(segment.getId());
		boolean isKom = false;
		for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
			if (entry.getAthleteId().equals(athleteId) && entry.getRank().equals(1)) {
				isKom = true;
			}
		}
		return isKom;
	}

	/**
	 */
	@SuppressWarnings("boxing")
	@Override
	public boolean isIssue() throws Exception {
		final StravaSegmentEffort[] efforts = this.api.listAthleteKOMs(200384, 1, 200);
		for (final StravaSegmentEffort effort : efforts) {
			if (!isKom(effort.getSegment(), 200384)) {
				return true;
			}
		}
		return false;

	}

	/**
	 */
	@Override
	public int issueNumber() {
		return 32;
	}
}
