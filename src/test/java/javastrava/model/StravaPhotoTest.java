package javastrava.model;

import javastrava.model.StravaPhoto;
import javastrava.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaPhotoTest extends BeanTest<StravaPhoto> {

	@Override
	protected Class<StravaPhoto> getClassUnderTest() {
		return StravaPhoto.class;
	}
}
