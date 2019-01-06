/**
 *
 */
package javastrava.api.upload.async;

import javastrava.model.StravaUploadResponse;
import javastrava.api.callback.APIGetCallback;
import javastrava.api.upload.CheckUploadStatusTest;

/**
 * @author Dan Shannon
 *
 */
public class CheckUploadStatusAsyncTest extends CheckUploadStatusTest {

	@Override
	protected APIGetCallback<StravaUploadResponse, Long> getter() {
		return ((api, id) -> api.checkUploadStatusAsync(id).get());
	}
}
