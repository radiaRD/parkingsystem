package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {

		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long duration = ((ticket.getOutTime().getTime() - ticket.getInTime().getTime()) / (60 * 1000));

		if (duration <= 30) {
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(duration * 0);
				break;
			}
			case BIKE: {
				ticket.setPrice(duration * 0);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}

		}

		else {

			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(duration * (Fare.CAR_RATE_PER_HOUR) / 60);
				break;
			}
			case BIKE: {
				ticket.setPrice(duration * (Fare.BIKE_RATE_PER_HOUR) / 60);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		}

	}

	public void calculateFivePercentReductionRecurringCustomers(Ticket ticket) {

		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long duration = ((ticket.getOutTime().getTime() - ticket.getInTime().getTime()) / (60 * 1000));

		if (duration <= 30) {
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(duration * 0);
				break;
			}
			case BIKE: {
				ticket.setPrice(duration * 0);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}

		}

		else {

			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice((duration * ((Fare.CAR_RATE_PER_HOUR) / 60)) * 0.95); // calculate 5% reduction
				break;
			}
			case BIKE: {
				ticket.setPrice((duration * ((Fare.BIKE_RATE_PER_HOUR) / 60)) * 0.95);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
	}

}