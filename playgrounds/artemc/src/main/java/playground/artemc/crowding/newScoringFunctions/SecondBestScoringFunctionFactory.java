/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2013 by the members listed in the COPYING,        *
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

package playground.artemc.crowding.newScoringFunctions;

import org.matsim.api.core.v01.population.Person;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.controler.MatsimServices;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;

/**
 * The CrowdedScoringFunctionFactory added disutilities for crowdedness
 * in PT-Vehicle on top of the ScoringFunctions generated by a delegate
 * ScoringFunctionFactory.
 * 
 * The disutilities are calculated from PersonCrowdednessEvents that are
 * generated by the CrowdednessObserver. If a EventsManager is passed to
 * the constructor, CrowdedPenaltyEvents will be pushed to the 
 * EventManager whenever penalties are calculated. These events are not
 * necessary, but puts information on the penalties in the event log.
 * 
 * @author pcbouman
 *
 */

public class SecondBestScoringFunctionFactory implements ScoringFunctionFactory
{

	private ScoringFunctionFactory delegate;
	private EventsManager events;
	private ScoreTracker scoreTracker;
	private MatsimServices controler;
	
	/**
	 * Constructs a ScoringFunctionFactory that adds disutilities for
	 * crowdedness on top of the ScoringFunctions generated by the
	 * delegate ScoringFunction. 
	 * @param delegate
	 */
	
	public SecondBestScoringFunctionFactory(ScoringFunctionFactory delegate)
	{
		this.delegate = delegate;
	}
	
	/**
	 * Constructs a ScoringFunctionFactory that adds disutilities for
	 * crowdedness on top of the ScoringFunctions generated by the
	 * delegate ScoringFunction. 
	 * If an EventManager is supplied, CrowdedPenaltyEvents will be
	 * passed to the EventManager, which can be useful to find the
	 * penalties for crowdedness afterwards.
	 * @param delegate
	 */
	
	public SecondBestScoringFunctionFactory(ScoringFunctionFactory delegate, EventsManager events, ScoreTracker scoreTracker, MatsimServices controler)
	{
		this.delegate = delegate;
		this.events = events;
		this.scoreTracker = scoreTracker;
		this.controler = controler;
	}

	@Override
	public ScoringFunction createNewScoringFunction(Person person)
	{
		return new SecondBestScoringFunction(delegate.createNewScoringFunction(person), events,  scoreTracker, controler);
	}

}

