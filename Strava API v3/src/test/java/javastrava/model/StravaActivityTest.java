package javastrava.model;

import javastrava.model.StravaActivity;
import javastrava.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityTest extends BeanTest<StravaActivity> {

	@Override
	protected Class<StravaActivity> getClassUnderTest() {
		return StravaActivity.class;
	}

}
