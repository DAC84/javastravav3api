package javastrava.model;

import static org.junit.Assert.assertNotNull;

import javastrava.model.StravaActivityPhotos;
import javastrava.service.standardtests.data.PhotoDataUtils;
import javastrava.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaActivityPhotosTest extends BeanTest<StravaActivityPhotos> {
	/**
	 * Validate that an activity contains the documented structure and data
	 * 
	 * @param photos
	 *            Photos object to validate
	 */
	public static void validate(final StravaActivityPhotos photos) {
		assertNotNull(photos.getCount());
		if (photos.getPrimary() != null) {
			PhotoDataUtils.validatePhoto(photos.getPrimary(), photos.getPrimary().getId(), photos.getPrimary().getResourceState());
		}
	}

	/**
	 * @see test.utils.BeanTest#getClassUnderTest()
	 */
	@Override
	protected Class<StravaActivityPhotos> getClassUnderTest() {
		return StravaActivityPhotos.class;
	}

}
