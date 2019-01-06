package javastrava.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.cache.impl.StravaCacheImpl;
import javastrava.model.StravaRunningRace;
import javastrava.service.RunningRaceService;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;

/**
 * <p>
 * Implementation of the running race javastrava.service
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RunningRaceServiceImpl extends StravaServiceImpl implements RunningRaceService {


	private final StravaCacheImpl<StravaRunningRace, Integer> runningRaceCache;

	/**
	 * <p>
	 * Private constructor requires a valid access token
	 * </p>
	 *
	 * @param token
	 *            Access token from Strava OAuth process
	 */
	public RunningRaceServiceImpl(final API api){
		super(api);
		this.runningRaceCache = new StravaCacheImpl<StravaRunningRace, Integer>(StravaRunningRace.class, api.getToken());
	}

	@Override
	public void clearCache() {
		this.runningRaceCache.removeAll();
	}

	@Override
	public StravaRunningRace getRace(Integer id) {
		// If the id is null, return null
		if (id == null) {
			return null;
		}

		try {
			return this.api.getRace(id);
		} catch (final NotFoundException e) {
			return null;
		}
	}

	@Override
	public CompletableFuture<StravaRunningRace> getRaceAsync(Integer id) {
		return StravaServiceImpl.future(() -> {
			return getRace(id);
		});
	}

	@Override
	public List<StravaRunningRace> listRaces(Integer year) {
		try {
			return Arrays.asList(this.api.listRaces(year));
		} catch (final NotFoundException e) {
			return null;
		} catch (final UnauthorizedException e) {
			return new ArrayList<StravaRunningRace>();
		}
	}

	@Override
	public CompletableFuture<List<StravaRunningRace>> listRacesAsync(Integer year) {
		return StravaServiceImpl.future(() -> {
			return listRaces(year);
		});
	}

}
