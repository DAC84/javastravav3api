package javastrava.api.route.async;

import javastrava.api.API;
import javastrava.model.StravaRoute;
import javastrava.api.callback.APIListCallback;
import javastrava.api.route.ListRoutesTest;

/**
 * <p>
 * Tests for {@link API#listAthleteRoutesAsync(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListRoutesTestAsync extends ListRoutesTest {
	@Override
	protected APIListCallback<StravaRoute, Integer> listCallback() {
		return ((api, id) -> api.listAthleteRoutesAsync(id, null, null).get());
	}

}
