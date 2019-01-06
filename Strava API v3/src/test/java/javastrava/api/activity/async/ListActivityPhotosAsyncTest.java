package javastrava.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaPhoto;
import javastrava.api.activity.ListActivityPhotosTest;
import javastrava.api.callback.APIListCallback;

/**
 * <p>
 * Specific tests for {@link API#listActivityPhotosAsync(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityPhotosAsyncTest extends ListActivityPhotosTest {
	/**
	 * @see test.api.activity.ListActivityPhotosTest#listCallback()
	 */
	@Override
	protected APIListCallback<StravaPhoto, Long> listCallback() {
		return (api, id) -> api.listActivityPhotosAsync(id).get();
	}
}
