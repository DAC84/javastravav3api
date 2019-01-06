package javastrava.service.impl;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.config.Messages;
import javastrava.model.StravaUploadResponse;
import javastrava.model.reference.StravaActivityType;
import javastrava.service.UploadService;
import javastrava.service.exception.BadRequestException;
import javastrava.service.exception.UnauthorizedException;
import retrofit.mime.TypedFile;

/**
 * <p>
 * Implementation of {@link UploadService}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class UploadServiceImpl extends StravaServiceImpl implements UploadService {


	/**
	 * <p>
	 * Private constructor prevents anyone getting an instance without going via {@link #instance(Token)}
	 * </p>
	 *
	 * @param token
	 *            The access token used to authenticate to the Strava API
	 */
	public UploadServiceImpl(final API api){
		super(api);
	}

	/**
	 * @see javastrava.service.UploadService#checkUploadStatus(java.lang.Long)
	 */
	@Override
	public StravaUploadResponse checkUploadStatus(final Long id) {
		return this.api.checkUploadStatus(id);
	}

	/**
	 * @see javastrava.service.UploadService#checkUploadStatusAsync(java.lang.Long)
	 */
	@Override
	public CompletableFuture<StravaUploadResponse> checkUploadStatusAsync(final Long uploadId) throws UnauthorizedException {
		return StravaServiceImpl.future(() -> {
			return checkUploadStatus(uploadId);
		});
	}

	/**
	 * @see javastrava.service.StravaService#clearCache()
	 */
	@Override
	public void clearCache() {
		// Nothing to do - there is no cache
	}

	/**
	 * @see javastrava.service.UploadService#upload(javastrava.model.reference.StravaActivityType, java.lang.String,
	 *      java.lang.String, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, java.lang.String, java.lang.String,
	 *      java.io.File)
	 */
	@Override
	public StravaUploadResponse upload(final StravaActivityType activityType, final String name, final String description,
			final Boolean _private, final Boolean trainer, final Boolean commute, final String dataType, final String externalId,
			final File file) {
		if (file == null) {
			throw new IllegalArgumentException(Messages.string("UploadServiceImpl.cannotUploadNullFile")); //$NON-NLS-1$
		}
		if (!file.exists() || file.isDirectory()) {
			throw new IllegalArgumentException(
					String.format(Messages.string("UploadServiceImpl.fileDoesNotExist"), file.getName())); //$NON-NLS-1$
		}
		try {
			return this.api.upload(activityType, name, description, _private, trainer, commute, dataType, externalId,
					new TypedFile("text/xml", file)); //$NON-NLS-1$
		} catch (final BadRequestException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * @see javastrava.service.UploadService#uploadAsync(javastrava.model.reference.StravaActivityType,
	 *      java.lang.String, java.lang.String, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, java.lang.String,
	 *      java.lang.String, java.io.File)
	 */
	@Override
	public CompletableFuture<StravaUploadResponse> uploadAsync(final StravaActivityType activityType, final String name,
			final String description, final Boolean _private, final Boolean trainer, final Boolean commute, final String dataType,
			final String externalId, final File file) {
		return StravaServiceImpl.future(() -> {
			return upload(activityType, name, description, _private, trainer, commute, dataType, externalId, file);
		});
	}

}
