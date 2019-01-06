package javastrava.model;

import javastrava.model.StravaMapPoint;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaMapPoint}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaMapPointTest extends BeanTest<StravaMapPoint> {

	@Override
	protected Class<StravaMapPoint> getClassUnderTest() {
		return StravaMapPoint.class;
	}

}
