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

/**
 * 
 */
package org.matsim.contrib.noise.handler;

import org.matsim.api.core.v01.events.PersonMoneyEvent;
import org.matsim.contrib.noise.data.NoiseContext;
import org.matsim.contrib.noise.events.NoiseEventCaused;
import org.matsim.contrib.noise.personLinkMoneyEvents.PersonLinkMoneyEvent;
import org.matsim.core.api.experimental.events.EventsManager;


/**
 * This handler calculates agent money events based on the noise damages an agent may cause (NoiseEventCaused).
 * 
 * @author ikaddoura
 *
 */
public class NoisePricingHandler implements NoiseEventCausedHandler {


	private final EventsManager events;
	private double amountSum = 0.;
	private final NoiseContext noiseContext;

	public NoisePricingHandler(EventsManager eventsManager, NoiseContext noiseContext) {
		this.events = eventsManager;
		this.noiseContext = noiseContext;
	}

	@Override
	public void reset(int iteration) {
		this.amountSum = 0.;
	}

	@Override
	public void handleEvent(NoiseEventCaused event) {
		
		// negative amount since from here the amount is interpreted as costs
		double amount = this.noiseContext.getNoiseParams().getNoiseTollFactor() * event.getAmount() * (-1);
		this.amountSum = this.amountSum + amount;
		
		PersonMoneyEvent moneyEvent = new PersonMoneyEvent(event.getTime(), event.getCausingAgentId(), amount);
		this.events.processEvent(moneyEvent);
		
		PersonLinkMoneyEvent linkMoneyEvent = new PersonLinkMoneyEvent(event.getTime(), event.getCausingAgentId(), event.getLinkId(), amount, event.getLinkEnteringTime());
		this.events.processEvent(linkMoneyEvent);
	}

	public double getAmountSum() {
		return amountSum;
	}
	
}