package javastrava.model;

import javastrava.model.StravaComment;
import javastrava.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaCommentTest extends BeanTest<StravaComment> {

	@Override
	protected Class<StravaComment> getClassUnderTest() {
		return StravaComment.class;
	}
}
