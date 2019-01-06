/**
 *
 */
package javastrava.model;

import javastrava.model.StravaVideo;
import javastrava.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaVideo}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaVideoTest extends BeanTest<StravaVideo> {

	@Override
	protected Class<StravaVideo> getClassUnderTest() {
		return StravaVideo.class;
	}

}
