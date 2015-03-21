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
			con.commit();
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
			stmt.executeQuery("DELETE FROM route WHERE routeNumber = " + routeNum);
			con.commit();
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM route");
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
			ResultSet rs = stmt.executeQuery("SELECT s.stopNumber, stopName, location FROM has h, stop s WHERE h.routeNumber = " + routeNum);
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
}
