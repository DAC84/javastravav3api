package javastrava.model;

import javastrava.model.StravaSegmentLeaderboard;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegmentLeaderboard}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentLeaderboardTest extends BeanTest<StravaSegmentLeaderboard> {

	@Override
	protected Class<StravaSegmentLeaderboard> getClassUnderTest() {
		return StravaSegmentLeaderboard.class;
	}
}
