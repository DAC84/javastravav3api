package javastrava.service.impl;

import java.util.concurrent.CompletableFuture;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.cache.StravaCache;
import javastrava.cache.impl.StravaCacheImpl;
import javastrava.model.StravaSegment;
import javastrava.model.StravaSegmentEffort;
import javastrava.model.reference.StravaResourceState;
import javastrava.service.SegmentEffortService;
import javastrava.service.SegmentService;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import javastrava.util.PrivacyUtils;

/**
 * @author Dan Shannon
 */
public class SegmentEffortServiceImpl extends StravaServiceImpl implements SegmentEffortService {


    /**
     * Cache of segment efforts
     */
    private final StravaCache<StravaSegmentEffort, Long> effortCache;
    private final SegmentService segmentService;

    /**
     * <p>
     * Private constructor ensures that the only way to get an instance is by using {@link #instance(Token)} with a valid access token.
     * </p>
     *
     * @param token The access token to be used for authentication to the Strava API
     */
    public SegmentEffortServiceImpl(final API api) {
        super(api);
        this.effortCache = new StravaCacheImpl<>(StravaSegmentEffort.class, api.getToken());
        this.segmentService = new SegmentServiceImpl(api);
    }

    /**
     * @see javastrava.service.StravaService#clearCache()
     */
    @Override
    public void clearCache() {
        this.effortCache.removeAll();
    }

    /**
     * @see javastrava.service.SegmentEffortService#getSegmentEffort(Long)
     */
    @Override
    public StravaSegmentEffort getSegmentEffort(final Long segmentEffortId) {
        // If id is null, return null
        if (segmentEffortId == null) {
            return null;
        }

        // Try to get the effort from cache
        StravaSegmentEffort effort = this.effortCache.get(segmentEffortId);
        if ((effort != null) && (effort.getResourceState() != StravaResourceState.META)) {
            return effort;
        }

        // If it wasn't in cache, get it from the API
        try {
            effort = this.api.getSegmentEffort(segmentEffortId);
        } catch (final NotFoundException e) {
            // Segment effort doesn't exist
            return null;
        } catch (final UnauthorizedException e) {
            effort = PrivacyUtils.privateSegmentEffort(segmentEffortId);
        }

        // TODO This is a workaround for issue javastrava-api #78
        // See https://github.com/danshannon/javastravav3api/issues/78
        if (effort.getResourceState() == StravaResourceState.DETAILED) {
            final StravaSegment segment = segmentService.getSegment(effort.getSegment().getId());
            if (segment.getResourceState() == StravaResourceState.PRIVATE) {
                effort = PrivacyUtils.privateSegmentEffort(segmentEffortId);
            }
        }
        // End of workaround

        // Put the effort into cache and return it
        this.effortCache.put(effort);
        return effort;
    }

    /**
     * @see javastrava.service.SegmentEffortService#getSegmentEffortAsync(java.lang.Long)
     */
    @Override
    public CompletableFuture<StravaSegmentEffort> getSegmentEffortAsync(final Long segmentEffortId) {
        return StravaServiceImpl.future(() -> {
            return getSegmentEffort(segmentEffortId);
        });
    }

}
