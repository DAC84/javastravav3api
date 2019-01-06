package javastrava.api.route.async;

import javastrava.api.API;
import javastrava.model.StravaRoute;
import javastrava.api.callback.APIGetCallback;
import javastrava.api.route.GetRouteTest;

/**
 * <p>
 * Test for {@link API#getRouteAsync(Integer)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetRouteAsyncTest extends GetRouteTest {

	@Override
	protected APIGetCallback<StravaRoute, Integer> getter() {
		return ((api, id) -> api.getRouteAsync(id).get());
	}

}
