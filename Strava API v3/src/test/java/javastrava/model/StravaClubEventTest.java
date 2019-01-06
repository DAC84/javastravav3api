package javastrava.model;

import javastrava.model.StravaClubEvent;
import javastrava.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaClubEventTest extends BeanTest<StravaClubEvent> {

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaClubEvent> getClassUnderTest() {
		return StravaClubEvent.class;
	}

}
