package javastrava.model;

import javastrava.model.StravaSegment;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegment}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentTest extends BeanTest<StravaSegment> {

	@Override
	protected Class<StravaSegment> getClassUnderTest() {
		return StravaSegment.class;
	}
}
