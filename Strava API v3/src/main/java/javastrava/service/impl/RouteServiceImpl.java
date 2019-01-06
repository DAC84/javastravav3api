package javastrava.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.model.StravaRoute;
import javastrava.service.RouteService;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;

/**
 * <p>
 * Routes are manually-created paths made up of sections called legs. Currently it is only possible to create routes using the Routebuilder web interface.
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RouteServiceImpl extends StravaServiceImpl implements RouteService {


	/**
	 * <p>
	 * Private constructor prevents anyone from getting an instance without a valid access token
	 * </p>
	 *
	 * @param token
	 *            The access token to be used to authenticate to the Strava API
	 */
	public RouteServiceImpl(final API api){
		super(api);
	}

	@Override
	public void clearCache() {
		// Nothing to do - not cached

	}

	@Override
	public StravaRoute getRoute(Integer routeId) {
		if (routeId == null) {
			return null;
		}

		try {
			return this.api.getRoute(routeId);
		} catch (final NotFoundException e) {
			return null;
		}
	}

	@Override
	public CompletableFuture<StravaRoute> getRouteAsync(Integer routeId) {
		return StravaServiceImpl.future(() -> getRoute(routeId));
	}

	@Override
	public List<StravaRoute> listAthleteRoutes(Integer id) {
		try {
			return Arrays.asList(this.api.listAthleteRoutes(id, null, null));
		} catch (final NotFoundException e) {
			return null;
		} catch (final UnauthorizedException e) {
			return new ArrayList<StravaRoute>();
		}
	}

	@Override
	public CompletableFuture<List<StravaRoute>> listAthleteRoutesAsync(Integer id) {
		return StravaServiceImpl.future(() -> listAthleteRoutes(id));
	}

}
