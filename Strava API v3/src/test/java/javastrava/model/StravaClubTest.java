package javastrava.model;

import javastrava.model.StravaClub;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Data tests for {@link StravaClub}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaClubTest extends BeanTest<StravaClub> {

	@Override
	protected Class<StravaClub> getClassUnderTest() {
		return StravaClub.class;
	}
}
