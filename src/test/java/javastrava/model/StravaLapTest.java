package javastrava.model;

import javastrava.model.StravaLap;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for StravaLap
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaLapTest extends BeanTest<StravaLap> {

	@Override
	protected Class<StravaLap> getClassUnderTest() {
		return StravaLap.class;
	}
}
