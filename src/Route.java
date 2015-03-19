import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Route {
	Connection con = OracleConnection.getInstance().getConnection();
	
	public Route() {}
	
	public void insertRoute(int routeNum, String rname, Date stop, Date start) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO route VALUES (?, ?, ?, ?)");
			stmt.setInt(1, routeNum);
			stmt.setString(2, rname);
			stmt.setDate(3, stop);
			stmt.setDate(4, start);
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
	
	public ResultSet displayRoutes() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM route");
			stmt.close();
			return rs;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}
}
