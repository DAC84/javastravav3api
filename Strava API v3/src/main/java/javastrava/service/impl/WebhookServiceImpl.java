package javastrava.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.model.webhook.StravaEventSubscription;
import javastrava.service.WebhookService;

/**
 * @author Dan Shannon
 *
 */
public class WebhookServiceImpl extends StravaServiceImpl implements WebhookService {

	/**
	 * @param token
	 *            The access token to use in calls to the API
	 */
	public WebhookServiceImpl(final API api){
		super(api);
	}

	@Override
	public void clearCache() {
		// There is no cache

	}

	@Override
	public StravaEventSubscription createSubscription(Integer clientId, String clientSecret, final StravaEventSubscription subscription, final String verifyToken) {
		return this.api.createSubscription(clientId, clientSecret, subscription.getObjectType(), subscription.getAspectType(), subscription.getCallbackURL(), verifyToken);
	}

	@Override
	public CompletableFuture<StravaEventSubscription> createSubscriptionAsync(Integer clientId, String clientSecret, final StravaEventSubscription subscription, final String verifyToken) {
		return StravaServiceImpl.future(() -> {
			return this.api.createSubscription(clientId, clientSecret, subscription.getObjectType(), subscription.getAspectType(), subscription.getCallbackURL(), verifyToken);
		});
	}

	@Override
	public void deleteSubscription(final Integer clientId, final String clientSecret, final Integer subscriptionId) {
		this.api.deleteSubscription(subscriptionId, clientId, clientSecret);
		return;

	}

	@Override
	public CompletableFuture<Void> deleteSubscriptionAsync(final Integer clientId, final String clientSecret, final Integer subscriptionId) {
		return StravaServiceImpl.future(() -> {
			this.api.deleteSubscription(subscriptionId, clientId, clientSecret);
			return null;
		});
	}

	@Override
	public List<StravaEventSubscription> listSubscriptions(final Integer clientId, final String clientSecret) {
		return Arrays.asList(this.api.listSubscriptions(clientId, clientSecret));
	}

	@Override
	public CompletableFuture<List<StravaEventSubscription>> listSubscriptionsAsync(final Integer clientId, final String clientSecret) {
		return StravaServiceImpl.future(() -> {
			return Arrays.asList(this.api.listSubscriptions(clientId, clientSecret));
		});
	}

}
