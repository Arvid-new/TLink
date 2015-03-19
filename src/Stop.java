import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Stop {
	
	Connection con = OracleConnection.getInstance().getConnection();
	
	public Stop() {}
	
	public void insertStop(int stopNum, String stopName, String location) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO stop VALUES (?, ?, ?)");
			stmt.setInt(1, stopNum);
			stmt.setString(2, stopName);
			stmt.setString(3, location);
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
	
	public void deleteStop(int stopNum) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM stop WHERE stopNumber = " + stopNum);
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
	
	public ResultSet displayStops() {
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM stop");
			stmt.close();
			return rs;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}
}
