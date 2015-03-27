import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;


public class Route {
	Connection con = MySQLConnection.getInstance().getConnection();
	
	public Route() {}
	
	public boolean insertRoute(int routeNum, String rname, Time stop, Time start) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO route VALUES (?, ?, ?, ?)");
			stmt.setInt(1, routeNum);
			stmt.setString(2, rname);
			stmt.setTime(3, stop);
			stmt.setTime(4, start);
			stmt.executeUpdate();
			stmt.close();
			return true;
		}
		catch (SQLException ex) {
			return false;
		}
	}
	
	public boolean deleteRoute(int routeNum) {
		try {
			Statement stmt = con.createStatement();
			int rows = stmt.executeUpdate("DELETE FROM route WHERE routeNumber = " + routeNum);
			stmt.close();
			return (rows != 0) ? true : false;
		}
		catch (SQLException ex) {
			return false;
		}
	}
	
	public ResultTableModel displayRoutes() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT routeNumber, routeName, startTime, stopTime FROM route");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}
	
	public ResultTableModel getAllStops(int routeNum) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT s.stopNumber, stopName, location FROM has h NATURAL JOIN stop s WHERE h.routeNumber = " + routeNum);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			// TODO
			return null;
		}
	}
	
	public ResultTableModel searchRoutes(String routeName) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM route WHERE routeName LIKE '%" + routeName + "%'");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			// TODO
			return null;
		}
	}
	
	public ResultTableModel searchRoutes(int rid) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM route WHERE routeNumber = " + rid);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			// TODO
			return null;
		}
	}
	
	public ResultTableModel customersPerRoute() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT routeNumber, COUNT(cid) AS 'Number of customers'"
					+ "FROM follows f, access a "
					+ "WHERE f.vehicleNumber = a.vehicleNumber "
					+ "GROUP BY routeNumber "
					+ "ORDER BY routeNumber");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			// TODO
			return null;
		}
	}
	
	public ResultTableModel busiestRoute() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT routeNumber, counts.n AS 'Number of customers' "
					+ "FROM follows f, (SELECT vehicleNumber, count(cid) AS 'n' FROM access GROUP BY vehicleNumber) counts "
					+ "WHERE f.vehicleNumber = counts.vehicleNumber "
					+ "AND counts.n = (SELECT MAX(counts2.n2) FROM "
					+ 					"(SELECT routeNumber, count(cid) AS 'n2' "
					+ 					"FROM follows f2, access a2 "
					+ 					"WHERE f2.vehicleNumber = a2.vehicleNumber GROUP BY routeNumber) counts2) "
					+ "ORDER BY routeNumber");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			// TODO
			return null;
		}
	}
	
	public ResultTableModel quietestRoute() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT routeNumber, counts.n AS 'Number of customers' "
					+ "FROM follows f, (SELECT vehicleNumber, count(cid) AS 'n' FROM access GROUP BY vehicleNumber) counts "
					+ "WHERE f.vehicleNumber = counts.vehicleNumber "
					+ "AND counts.n = (SELECT MIN(counts2.n2) FROM "
					+ 					"(SELECT routeNumber, count(cid) AS 'n2' "
					+ 					"FROM follows f2, access a2 "
					+ 					"WHERE f2.vehicleNumber = a2.vehicleNumber GROUP BY routeNumber) counts2) "
					+ "ORDER BY routeNumber");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			// TODO
			return null;
		}
	}
}
