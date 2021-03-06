package javastrava.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaPhotoSizes;
import javastrava.service.standardtests.data.PhotoDataUtils;

/**
 * MeanBean factory for Strava photo sizes objects
 *
 * @author Dan Shannon
 *
 */
public class StravaPhotoSizesFactory implements Factory<StravaPhotoSizes> {

	@Override
	public StravaPhotoSizes create() {
		return PhotoDataUtils.testPhotoSizes();
	}

}
