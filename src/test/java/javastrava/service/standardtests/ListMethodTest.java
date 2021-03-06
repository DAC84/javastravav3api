package javastrava.service.standardtests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaEntity;
import javastrava.service.standardtests.callbacks.ListCallback;
import javastrava.service.standardtests.spec.ListMethodTests;
import javastrava.utils.RateLimitedTestRunner;
import javastrava.utils.TestUtils;

/**
 * <p>
 * Common tests for methods that return lists
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            Type of Strava object being listed
 * @param <U>
 *            Type of the id of the parent object
 *
 */
public abstract class ListMethodTest<T extends StravaEntity, U> implements ListMethodTests {
	/**
	 * @return The class under test
	 */
	protected abstract Class<T> classUnderTest();

	/**
	 * @return Id of a parent object that doesn't exist
	 */
	protected abstract U idInvalid();

	/**
	 * @return Id of a parent object that exists, belongs to the authenticated user, and is flagged as private
	 */
	protected abstract U idPrivate();

	/**
	 * @return Id of a parent object that exists, belongs to someone other than the authenticated user, and is flagged as private
	 */
	protected abstract U idPrivateBelongsToOtherUser();

	/**
	 * @return Id of a parent object that exists, belongs to the authenticated user, and has entries in the list
	 */
	protected abstract U idValidWithEntries();

	/**
	 * @return Id of a parent object that exists, belongs to the authenticated user, and has NO entries in the list
	 */
	protected abstract U idValidWithoutEntries();

	/**
	 * @return Callback used to get the list
	 */
	protected abstract ListCallback<T, U> lister();

	@Override
	@Test
	public void testInvalidId() throws Exception {
		// Don't run if there's no id to run against
		if (idInvalid() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idInvalid());

			// If we get here, we got a list
			assertNull("Succeeded in getting list of " + getClass().getName() + " for an invalid parent with id " + idInvalid() + "!", list); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		});
	}

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Don't run if there's no id
		if (idPrivateBelongsToOtherUser() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.stravaWithViewPrivate(), idPrivateBelongsToOtherUser());

			// Private parent should result in an empty list
			assertTrue("Parent with id " + idPrivateBelongsToOtherUser() + "is private and belongs to a user other than the authenticated one but returned a list of " + getClass().getName() //$NON-NLS-1$ //$NON-NLS-2$
					+ " which is not empty", list.isEmpty()); //$NON-NLS-1$
		});

	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Don't run if there's no id to run against
		if (idPrivate() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> results = lister().getList(TestUtils.strava(), idPrivate());

			// List method should return an empty list if the entity is private
			assertTrue(
					"Parent with id " + idPrivateBelongsToOtherUser() + " is private and token does not have view_private scope, but returned a list of " + getClass().getName() //$NON-NLS-1$ //$NON-NLS-2$
							+ " which is not empty", //$NON-NLS-1$
					results.isEmpty());

		});

	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Don't run if there's no id to run against
		if (idPrivate() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.stravaWithViewPrivate(), idPrivate());
			assertNotNull(list);
			for (final T object : list) {
				validate(object);
			}
		});

	}

	@Override
	@Test
	public void testValidParentWithEntries() throws Exception {
		// Don't run if there's no id to run against
		if (idValidWithEntries() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idValidWithEntries());

			// If we get here, we got a list; check it is not null has >0 entries
			assertNotNull("List returned but was null!", list); //$NON-NLS-1$
			assertTrue("List returned but contains no entries!", (list.size() > 0)); //$NON-NLS-1$

			// Check the contents of the list
			for (final T object : list) {
				validate(object);
			}
		});
	}

	@Override
	@Test
	public void testValidParentWithNoEntries() throws Exception {
		// Don't run if there's no id to run against
		if (idValidWithoutEntries() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idValidWithoutEntries());

			// If we get here, we got a list; check it has >0 entries
			assertNotNull("List returned but was null!", list); //$NON-NLS-1$
			assertTrue("List returned but contains entries!", (list.size() == 0)); //$NON-NLS-1$
		});
	}

	/**
	 * Validate the structure and content of the object
	 *
	 * @param object
	 *            The object to be validated
	 */
	protected abstract void validate(T object);

	/**
	 * Validate a list of objects
	 *
	 * @param list
	 *            The list of objects to be validated
	 */
	protected void validateList(List<T> list) {
		for (final T object : list) {
			validate(object);
		}

	}

}
