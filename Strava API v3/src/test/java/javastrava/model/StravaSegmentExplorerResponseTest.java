package javastrava.model;

import javastrava.model.StravaSegmentExplorerResponse;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegmentExplorerResponse}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentExplorerResponseTest extends BeanTest<StravaSegmentExplorerResponse> {

	@Override
	protected Class<StravaSegmentExplorerResponse> getClassUnderTest() {
		return StravaSegmentExplorerResponse.class;
	}
}
