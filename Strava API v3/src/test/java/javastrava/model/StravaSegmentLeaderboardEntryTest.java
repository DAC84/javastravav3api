package javastrava.model;

import javastrava.model.StravaSegmentLeaderboardEntry;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegmentLeaderboardEntry}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentLeaderboardEntryTest extends BeanTest<StravaSegmentLeaderboardEntry> {

	@Override
	protected Class<StravaSegmentLeaderboardEntry> getClassUnderTest() {
		return StravaSegmentLeaderboardEntry.class;
	}
}
