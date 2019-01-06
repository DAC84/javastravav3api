package javastrava.model;

import javastrava.model.StravaRoute;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaRoute}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaRouteTest extends BeanTest<StravaRoute> {

	@Override
	protected Class<StravaRoute> getClassUnderTest() {
		return StravaRoute.class;
	}

}
