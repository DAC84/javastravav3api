package javastrava.model;

import javastrava.model.StravaStream;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaStream}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaStreamTest extends BeanTest<StravaStream> {

	@Override
	protected Class<StravaStream> getClassUnderTest() {
		return StravaStream.class;
	}

}
