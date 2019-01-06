package javastrava.model;

import javastrava.model.StravaMap;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaMap}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaMapTest extends BeanTest<StravaMap> {

	@Override
	protected Class<StravaMap> getClassUnderTest() {
		return StravaMap.class;
	}
}
