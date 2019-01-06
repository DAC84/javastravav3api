package javastrava.model;

import javastrava.model.StravaStatistics;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaStatistics}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaStatisticsTest extends BeanTest<StravaStatistics> {

	@Override
	protected Class<StravaStatistics> getClassUnderTest() {
		return StravaStatistics.class;
	}

}
