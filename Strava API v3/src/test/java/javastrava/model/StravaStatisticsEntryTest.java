package javastrava.model;

import javastrava.model.StravaStatisticsEntry;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaStatisticsEntry}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaStatisticsEntryTest extends BeanTest<StravaStatisticsEntry> {

	@Override
	protected Class<StravaStatisticsEntry> getClassUnderTest() {
		return StravaStatisticsEntry.class;
	}

}
