package javastrava.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaPhotoUrls;
import javastrava.service.standardtests.data.PhotoDataUtils;

/**
 * Test data factory for mean bean tests
 *
 * @author Dan Shannon
 *
 */
public class StravaPhotoUrlsFactory implements Factory<StravaPhotoUrls> {

	@Override
	public StravaPhotoUrls create() {
		return PhotoDataUtils.testPhotoUrls();
	}

}
