package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFareWithOccurrences(Ticket ticket, int recurrentUser) {
		if (recurrentUser >= Fare.MINIMUM_NUMBER_RECCURRENT_USER) {
			ticket.setPrice(ticket.getPrice() * Fare.DISCOUNT_AMOUNT);
		}
	}

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long inHour = ticket.getInTime().getTime();
		long outHour = ticket.getOutTime().getTime();

		// TODO: Some tests are failing here. Need to check if this logic is correct
		double duration = outHour - inHour;
		duration = duration / (3600 * 1000);

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			if (duration <= Fare.CAR_RATE_FREE_DURATION) {
				ticket.setPrice(0);
			} else {
				ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
			}
			break;
		}
		case BIKE: {
			if (duration <= Fare.BIKE_RATE_FREE_DURATION) {
				ticket.setPrice(0);
			} else {
				ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
	}
}