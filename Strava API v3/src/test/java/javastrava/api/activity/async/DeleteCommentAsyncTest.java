package javastrava.api.activity.async;

import javastrava.model.StravaComment;
import javastrava.api.activity.DeleteCommentTest;
import javastrava.api.callback.APIDeleteCallback;
import javastrava.service.standardtests.data.ActivityDataUtils;
import javastrava.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class DeleteCommentAsyncTest extends DeleteCommentTest {
	@Override
	protected String classUnderTest() {
		return this.getClass().getName();
	}

	@Override
	public APIDeleteCallback<StravaComment> deleter() {
		return ((api, comment) -> {
			api.deleteCommentAsync(comment.getActivityId(), comment.getId()).get();
			return comment;
		});
	}

	@Override
	public void testDeleteComment_byIds() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = apiWithWriteAccess().createComment(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, "DeleteCommentAsyncTest - please ignore"); //$NON-NLS-1$
			apiWithWriteAccess().deleteCommentAsync(comment.getActivityId(), comment.getId()).get();
		});
	}

}
