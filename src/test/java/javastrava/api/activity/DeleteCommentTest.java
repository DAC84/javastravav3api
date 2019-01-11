package javastrava.api.activity;

import api.issues.strava.Issue63;
import javastrava.api.API;
import javastrava.api.APIDeleteTest;
import javastrava.api.APITest;
import javastrava.api.callback.APIDeleteCallback;
import javastrava.config.JavaStravaApplicationConfig;
import javastrava.model.StravaComment;
import javastrava.service.exception.NotFoundException;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.service.standardtests.data.CommentDataUtils;
import javastrava.utils.RateLimitedTestRunner;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

/**
 * <p>
 * Tests for {@link API#deleteComment(Long, Integer)} method
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class DeleteCommentTest extends APIDeleteTest<StravaComment, Long> {

	JavaStravaApplicationConfig javaStravaApplicationConfig = new JavaStravaApplicationConfig();

	@Override
	protected String classUnderTest() {
		return this.getClass().getName();
	}

	@Override
	protected StravaComment createObject(String name) {
		return apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, name + " - test data only - please ignore"); //$NON-NLS-1$
	}

	@Override
	protected StravaComment createPrivateObject(String name) {
		// No such thing!
		return null;
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void delete_invalidParent() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		assumeTrue(javaStravaApplicationConfig.getAllowsComment());

		try {
			apiWithFullAccess().deleteComment(ActivityDataUtils.ACTIVITY_INVALID, 0);
		} catch (final NotFoundException e) {
			// Expected
			return;
		}
		fail("Attempt to delete a non-existent comment with id = 0 appears to have worked successfully!"); //$NON-NLS-1$

	}

	@Override
	@Test
	public void delete_privateParentBelongsToOtherUser() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(javaStravaApplicationConfig.getAllowsActivityDelete());

		// There's no way to create data anyway
		return;
	}

	@Override
	@Test
	public void delete_privateParentWithoutViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(javaStravaApplicationConfig.getAllowsActivityDelete());

		super.delete_privateParentWithoutViewPrivate();
	}

	@Override
	@Test
	public void delete_privateParentWithViewPrivate() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(javaStravaApplicationConfig.getAllowsActivityDelete());

		super.delete_privateParentWithViewPrivate();
	}

	@Override
	@Test
	public void delete_valid() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (javaStravaApplicationConfig.getAllowsComment()) {
			super.delete_valid();
		}
	}

	@Override
	@Test
	public void delete_validParentNoWriteAccess() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to delete activities
		assumeTrue(javaStravaApplicationConfig.getAllowsActivityDelete());

		assumeFalse(new Issue63().isIssue());

		super.delete_validParentNoWriteAccess();
	}

	@Override
	public APIDeleteCallback<StravaComment> deleter() {
		return ((api, comment) -> {
			api.deleteComment(comment.getActivityId(), comment.getId());
			return comment;
		});
	}

	@Override
	protected boolean deleteReturnsNull() {
		return false;
	}

	@Override
	protected void forceDelete(final StravaComment comment) {
		APITest.forceDeleteComment(comment);

	}

	@Override
	protected Long invalidParentId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long privateParentId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long privateParentOtherUserId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * Test for the delete by ids version of the method
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDeleteComment_byIds() throws Exception {
		// Can't execute the test unless we have Strava's application-level permission to write comments
		if (javaStravaApplicationConfig.getAllowsComment()) {
			RateLimitedTestRunner.run(() -> {
				final StravaComment comment = apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, "DeleteCommentTest - please ignore"); //$NON-NLS-1$
				apiWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());
			});
		}
	}

	@Override
	protected void validate(final StravaComment comment) throws Exception {
		CommentDataUtils.validateComment(comment);

	}

	@Override
	protected Long validParentId() {
		return ActivityDataUtils.ACTIVITY_WITH_COMMENTS;
	}
}
