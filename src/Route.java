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
	
	public void insertRoute(int routeNum, String rname, Time stop, Time start) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO route VALUES (?, ?, ?, ?)");
			stmt.setInt(1, routeNum);
			stmt.setString(2, rname);
			stmt.setTime(3, stop);
			stmt.setTime(4, start);
			stmt.executeUpdate();
			stmt.close();
		}
		catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				//TODO
			}
		}
	}
	
	public void deleteRoute(int routeNum) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM route WHERE routeNumber = " + routeNum);
			stmt.close();
		}
		catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				//TODO
			}
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
}
