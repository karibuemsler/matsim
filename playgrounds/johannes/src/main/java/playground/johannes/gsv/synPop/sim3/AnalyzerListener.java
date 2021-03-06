/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2014 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package playground.johannes.gsv.synPop.sim3;

import playground.johannes.gsv.synPop.analysis.AnalyzerTask;
import playground.johannes.gsv.synPop.analysis.LegGeoDistanceTask;
import playground.johannes.gsv.synPop.analysis.ProxyAnalyzer;
import playground.johannes.synpop.data.Person;
import playground.johannes.synpop.data.PlainPerson;
import playground.johannes.synpop.gis.DataPool;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author johannes
 * 
 */
public class AnalyzerListener implements SamplerListener {

//	private final TrajectoryAnalyzerTaskComposite task;

	private final AnalyzerTask pTask;

	private final String rootDir;

	private final long interval;

	private final AtomicLong iters = new AtomicLong();

	public AnalyzerListener(DataPool dataPool, String rootDir, long interval) {
		this.rootDir = rootDir;
		this.interval = interval;

//		task = new TrajectoryAnalyzerTaskComposite();

//		FacilityData data = (FacilityData) dataPool.get(FacilityDataLoader.KEY);
		// task.addTask(new FacilityOccupancyTask(facilities));
//		task.addTask(new TripDistanceTask(data.getAll(), CartesianDistanceCalculator.getInstance()));

		pTask = new LegGeoDistanceTask("car");
		ProxyAnalyzer.setAppend(true);

	}

	@Override
	public void afterStep(Collection<? extends Person> population, Collection<? extends Person> mutations, boolean accepted) {
		// new CopyFacilityUserData().afterStep(population, mutations,
		// accepted);

		if (iters.get() % interval == 0) {
//			Set<Trajectory> trajectories = TrajectoryProxyBuilder.buildTrajectories(population);
			String output = String.format("%s/%s", rootDir, String.valueOf(iters));
			File file = new File(output);
			file.mkdirs();
			try {
//				TrajectoryAnalyzer.analyze(trajectories, task, file.getAbsolutePath());
				ProxyAnalyzer.analyze((Set<PlainPerson>)population, pTask, file.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		iters.incrementAndGet();
	}

}
