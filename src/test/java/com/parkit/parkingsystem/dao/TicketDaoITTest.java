package com.parkit.parkingsystem.dao;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

public class TicketDaoITTest {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;

	@BeforeEach
	private void setUpTest() throws Exception {
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@AfterEach
	private void tearDown() {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	public void saveTicketTest() {
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		ticket.setInTime(inTime);
		ticket.setPrice(1.5);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");

		ticketDAO.saveTicket(ticket);

		Assertions.assertFalse(ticketDAO.saveTicket(ticket));
	}

	@Test
	public void updateTicketTest() {
		Ticket ticket = new Ticket();
		Date inTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0);
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		ticket.setInTime(inTime);
		ticketDAO.saveTicket(ticket);

		Date outTime = new Date();
		ticket.setOutTime(outTime);
		ticket.setPrice(1.5);
		ticketDAO.updateTicket(ticket);

		Assertions.assertNotEquals(ticketDAO.updateTicket(ticket), ticketDAO.saveTicket(ticket));
	}

	@Test
	public void getTicketTest() {
		Ticket ticket = new Ticket();
		Date inTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0);
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		ticket.setInTime(inTime);
		ticketDAO.saveTicket(ticket);

		Ticket ticket2 = ticketDAO.getTicket("ABCDEF");

		Assertions.assertEquals(ticket.getVehicleRegNumber(), ticket2.getVehicleRegNumber());
	}

	@Test
	public void getTicketOccurrencesTest() {
		Ticket ticket = new Ticket();
		Date inTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0);
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		ticket.setInTime(inTime);
		ticketDAO.saveTicket(ticket);

		Ticket ticket2 = ticketDAO.getTicket("ABCDEF");
		ticketDAO.saveTicket(ticket2);

		Assertions.assertEquals(ticketDAO.getTicketOccurrences("ABCDEF"), 2);
	}
}
