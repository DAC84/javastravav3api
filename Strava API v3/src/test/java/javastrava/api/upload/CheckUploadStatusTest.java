package javastrava.api.upload;

import javastrava.api.API;
import javastrava.model.StravaUploadResponse;
import javastrava.api.APIGetTest;
import javastrava.api.callback.APIGetCallback;
import javastrava.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Specific config and tests for {@link API#checkUploadStatus(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CheckUploadStatusTest extends APIGetTest<StravaUploadResponse, Long> {
	@Override
	protected APIGetCallback<StravaUploadResponse, Long> getter() {
		return ((api, id) -> api.checkUploadStatus(id));
	}

	/**
	 * @see javastrava.api.APIGetTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return new Long(0L);
	}

	/**
	 * @see javastrava.api.APIGetTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return null;
	}

	/**
	 * @see javastrava.api.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.APITest#validate(Object)
	 */
	@Override
	protected void validate(final StravaUploadResponse result) throws Exception {
		ActivityDataUtils.validateUploadResponse(result);

	}

	/**
	 * @see javastrava.api.APIGetTest#validId()
	 */
	@Override
	protected Long validId() {
		return api().getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null).getUploadId();
	}

	/**
	 * @see javastrava.api.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return api().getActivity(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, null).getUploadId();
	}

}
