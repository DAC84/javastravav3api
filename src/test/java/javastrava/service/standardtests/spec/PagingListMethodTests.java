/**
 *
 */
package javastrava.service.standardtests.spec;

import org.junit.Test;

import javastrava.service.exception.UnauthorizedException;

/**
 * <p>
 * Standard set of tests to be implemented for list methods that include a paging parameter
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface PagingListMethodTests {

	/**
	 * <p>
	 * Test paging (page number and page size).
	 * </p>
	 *
	 * <p>
	 * To test this we get 2 entities from the service, then ask for the first page only and check that it's the same as the first entity, then ask for the second page and check that it's the same as
	 * the second entity
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	void testPageNumberAndSize() throws Exception;

	/**
	 * <p>
	 * Test paging (page size only)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	void testPageSize() throws Exception;

	/**
	 * <p>
	 * Test paging with a page size that is too large for Strava
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	void testPageSizeTooLargeForStrava() throws Exception;

	/**
	 * <p>
	 * Test paging with ignore first n results parameter set
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	void testPagingIgnoreFirstN() throws Exception;

	/**
	 * <p>
	 * Test paging with ignore last n results parameter set
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	void testPagingIgnoreLastN() throws Exception;

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too high)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	void testPagingOutOfRangeHigh() throws Exception;

	/**
	 * <p>
	 * Test paging for paging parameters that can't return values (i.e. are out of range - too low)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	void testPagingOutOfRangeLow() throws Exception;

}