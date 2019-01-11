/**
 *
 */
package javastrava.service.impl.webhookservice;

import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.webhook.StravaEventSubscription;
import javastrava.model.webhook.StravaEventSubscriptionTest;
import javastrava.service.Strava;
import javastrava.service.standardtests.ListMethodTest;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.data.AthleteDataUtils;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#listSubscriptions(Integer, String) methods}
 * </p>
 *
 * @author Dan Shannon
 */
public class ListSubscriptionsTest extends ListMethodTest<StravaEventSubscription, Integer> {
	private JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();

	@Override
	protected Class<StravaEventSubscription> classUnderTest() {
		return StravaEventSubscription.class;
	}

	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaEventSubscription, Integer> lister() {
		return ((strava, id) -> strava.listSubscriptions(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET));
	}

	@Override
	public void testInvalidId() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (javaStravaApplicationConfig.getAllowsWebhooks()) {

			super.testInvalidId();
		}
	}

	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (javaStravaApplicationConfig.getAllowsWebhooks()) {

			super.testPrivateBelongsToOtherUser();
		}
	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (javaStravaApplicationConfig.getAllowsWebhooks()) {

			super.testPrivateWithNoViewPrivateScope();
		}
	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (javaStravaApplicationConfig.getAllowsWebhooks()) {

			super.testPrivateWithViewPrivateScope();
		}
	}

	@Override
	public void testValidParentWithEntries() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (javaStravaApplicationConfig.getAllowsWebhooks()) {

			super.testValidParentWithEntries();
		}
	}

	@Override
	public void testValidParentWithNoEntries() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (javaStravaApplicationConfig.getAllowsWebhooks()) {

			super.testValidParentWithNoEntries();
		}
	}

	@Override
	protected void validate(StravaEventSubscription object) {
		StravaEventSubscriptionTest.validate(object);
	}

}
