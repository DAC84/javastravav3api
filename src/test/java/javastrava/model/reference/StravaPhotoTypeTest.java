package javastrava.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.model.reference.StravaPhotoType;

/**
 * @author Dan Shannon
 *
 */
public class StravaPhotoTypeTest {
	/**
	 * Test returning the description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final StravaPhotoType type : StravaPhotoType.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test returning the id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final StravaPhotoType type : StravaPhotoType.values()) {
			assertNotNull(type.getId());
			assertEquals(type, StravaPhotoType.create(type.getId()));
		}
	}

}
