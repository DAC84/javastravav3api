package api.apicheck;

import org.junit.Test;

import javastrava.api.API;
import javastrava.api.SegmentEffortAPI;
import javastrava.model.StravaSegmentEffort;
import retrofit.client.Response;
import javastrava.service.standardtests.data.SegmentEffortDataUtils;
import javastrava.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class SegmentEffortAPITest {
	private static SegmentEffortAPI api() {
		return API.instance(SegmentEffortAPI.class, TestUtils.getValidToken());
	}

	/**
	 * Test for {@link API#getSegmentEffort(Long)}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAPI_getSegmentEffort() throws Exception {
		final Response response = api().getSegmentEffortRaw(SegmentEffortDataUtils.SEGMENT_EFFORT_VALID_ID);
		ResponseValidator.validate(response, StravaSegmentEffort.class, "getSegmentEffort"); //$NON-NLS-1$
	}

}
