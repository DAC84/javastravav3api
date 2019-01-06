package javastrava.model;

import javastrava.model.StravaSplit;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSplit}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSplitTest extends BeanTest<StravaSplit> {

	@Override
	protected Class<StravaSplit> getClassUnderTest() {
		return StravaSplit.class;
	}
}
