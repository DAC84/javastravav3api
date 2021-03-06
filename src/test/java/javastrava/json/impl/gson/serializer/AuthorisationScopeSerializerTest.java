package javastrava.json.impl.gson.serializer;

import javastrava.auth.ref.AuthorisationScope;

/**
 * @author Dan Shannon
 *
 */
public class AuthorisationScopeSerializerTest extends EnumSerializerTest<AuthorisationScope> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<AuthorisationScope> getClassUnderTest() {
		return AuthorisationScope.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected AuthorisationScope getUnknownValue() {
		return AuthorisationScope.UNKNOWN;
	}
}
