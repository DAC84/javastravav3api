package javastrava.model;

import javastrava.model.StravaAPIError;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaAPIError}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaAPIErrorTest extends BeanTest<StravaAPIError> {

	@Override
	protected Class<StravaAPIError> getClassUnderTest() {
		return StravaAPIError.class;
	}

}
