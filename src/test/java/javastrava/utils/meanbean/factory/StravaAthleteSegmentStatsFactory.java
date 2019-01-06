package javastrava.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.model.StravaAthleteSegmentStats;
import javastrava.service.standardtests.data.SegmentEffortDataUtils;

/**
 * Mean bean data factory
 *
 * @author Dan Shannon
 *
 */
public class StravaAthleteSegmentStatsFactory implements Factory<StravaAthleteSegmentStats> {

	@Override
	public StravaAthleteSegmentStats create() {
		return SegmentEffortDataUtils.testAthleteSegmentStats();
	}

}
