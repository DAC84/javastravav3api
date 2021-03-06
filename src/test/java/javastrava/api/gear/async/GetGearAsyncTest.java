package javastrava.api.gear.async;

import static org.junit.Assert.fail;

import javastrava.api.API;
import javastrava.model.StravaGear;
import javastrava.service.exception.NotFoundException;
import javastrava.api.callback.APIGetCallback;
import javastrava.api.gear.GetGearTest;
import javastrava.service.standardtests.data.GearDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for {@link API#getGearAsync(String)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetGearAsyncTest extends GetGearTest {
	@Override
	protected APIGetCallback<StravaGear, String> getter() {
		return ((api, id) -> api.getGearAsync(id).get());
	}

	@SuppressWarnings("nls")
	@Override
	public void testGetGear_otherAthlete() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getGearAsync(GearDataUtils.GEAR_OTHER_ATHLETE_ID).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got gear details for gear belonging to another athlete!");
		});
	}

}
