package com.parkit.parkingsystem.integration.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.config.DataBaseConfig;

public class DataBaseTestConfig extends DataBaseConfig {

	private static final Logger logger = LogManager.getLogger("DataBaseTestConfig");

	Connection con = null;
//	PreparedStatement ps;
//	ResultSet rs;

	String queryGetNextParkingSpot = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = \"BIKE\"";
	String queryUpdateParkingSpot = "update parking set available = 1 where PARKING_NUMBER = 5";
	String querySaveTicket = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(5,110,2,\"2020-02-03 21:04:40\",\"2020-02-03 21:04:56\")";
	String queryUpdateTicket = "update ticket set PRICE=2, OUT_TIME=\"2020-02-03 21:04:56\" where ID=1";
	String queryGetTicket = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER= 110 and t.OUT_TIME IS NULL";
	String queryGetCount = "select count(*) from ticket where VEHICLE_REG_NUMBER= 110";

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		logger.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"root", "rootroot");

	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		con = getConnection();
	}

	@AfterEach
	private void closeConnectionTest() throws Exception {
		try {
			con.close();
			logger.info("Closing DB connection");
		} catch (SQLException e) {
			logger.error("Error while closing connection", e);
		}
	}

//	@AfterEach
//	private void closePreparedStatementTest() throws Exception {
//		if (ps != null) {
//			try {
//				ps.close();
//				logger.info("Closing Prepared Statement");
//			} catch (SQLException e) {
//				logger.error("Error while closing prepared statement", e);
//			}
//		}
//	}
//
//	@AfterEach
//	private void closeResultSetTest() throws Exception {
//		if (rs != null) {
//			try {
//				rs.close();
//				logger.info("Closing Result Set");
//			} catch (SQLException e) {
//				logger.error("Error while closing result set", e);
//			}
//		}
//	}

	@Test
	public void testqueryGetNextParkingSpot() {
		try {

			PreparedStatement ps = con.prepareStatement(queryGetNextParkingSpot);

			ResultSet rs = ps.executeQuery(queryGetNextParkingSpot);
			while (rs.next()) {
				int result = rs.getInt(1);
				System.out.println(result + "\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testqueryUpdateParkingSpot() {
		try {

			con.prepareStatement(queryUpdateParkingSpot).execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testqueryGetTicket() {
		try {
			PreparedStatement ps = con.prepareStatement(queryGetTicket);
			ResultSet rs = ps.executeQuery(queryGetTicket);

			while (rs.next()) {

				int tickID = rs.getInt(1);
				int tickParkSpot = rs.getInt(2);
				Double tickPrice = rs.getDouble(3);
				java.sql.Timestamp inTime = (rs.getTimestamp(4));
				java.sql.Timestamp OutTime = (rs.getTimestamp(5));

				System.out.println(tickParkSpot + "\t" + tickID + "\t" + tickPrice + "\t" + inTime + "\t" + OutTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testquerySaveTicket() {
		try {
			PreparedStatement ps = con.prepareStatement(querySaveTicket);
			ps.executeUpdate(querySaveTicket);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testqueryUpdateTicket() {
		try {
			PreparedStatement ps = con.prepareStatement(queryUpdateTicket);
			ps.executeUpdate(queryUpdateTicket);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testqueryCountTicket() {
		try {
			PreparedStatement ps = con.prepareStatement(queryGetCount);
			ResultSet rs = ps.executeQuery(queryGetCount);

			while (rs.next()) {

				int tickCount = rs.getInt(1);

				System.out.println(tickCount);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
