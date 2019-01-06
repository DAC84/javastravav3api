package javastrava.model;

import javastrava.model.StravaClubMembershipResponse;
import javastrava.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaClubMembershipResponseTest extends BeanTest<StravaClubMembershipResponse> {

	@Override
	protected Class<StravaClubMembershipResponse> getClassUnderTest() {
		return StravaClubMembershipResponse.class;
	}

}
