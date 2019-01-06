package javastrava.model;

import javastrava.model.StravaChallenge;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaChallenge}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaChallengeTest extends BeanTest<StravaChallenge> {
	@Override
	protected Class<StravaChallenge> getClassUnderTest() {
		return StravaChallenge.class;
	}

}
