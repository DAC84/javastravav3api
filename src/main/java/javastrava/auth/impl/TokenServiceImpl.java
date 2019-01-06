package javastrava.auth.impl;

import javastrava.api.API;
import javastrava.auth.TokenManager;
import javastrava.auth.TokenService;
import javastrava.auth.model.Token;
import javastrava.auth.model.TokenResponse;
import javastrava.service.StravaService;
import javastrava.service.exception.UnauthorizedException;
import javastrava.service.impl.StravaServiceImpl;

/**
 * @author Dan Shannon
 *
 */
public class TokenServiceImpl extends StravaServiceImpl implements TokenService {
	/**
	 * <p>
	 * Protected constructor prevents user from getting a javastrava.service instance
	 * without a valid token
	 * </p>
	 *
	 * @param api The access token to be used to authenticate to the Strava API
	 */
	public TokenServiceImpl(API api) {
		super(api);
	}

	/**
	 * @see javastrava.service.StravaService#clearCache()
	 */
	@Override
	public void clearCache() {
		// Nothing to do - there is no cache
	}

	/**
	 * @see TokenService#deauthorise(Token)
	 */
	@Override
	public TokenResponse deauthorise(final Token token) throws UnauthorizedException {
		final TokenResponse response = this.api.deauthoriseToken(token.getToken());
		//for (final StravaService service : this.api.getServices().values()) {
		//	service.clearCache();
		//}
		TokenManager.instance().revokeToken(token);
		return response;
	}

}
