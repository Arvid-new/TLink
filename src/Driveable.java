import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driveable {
	
	Connection con = MySQLConnection.getInstance().getConnection();

	public void insertDriveable(int vehicleNumber, String type) {
			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO driveable VALUES (?, ?)");
				stmt.setInt(1, vehicleNumber);
				stmt.setString(2, type);
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
		
		public void deleteDriveable(int vehicleNumber) {
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM driveable WHERE vehicleNumber = " + vehicleNumber);
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
		
		public ResultTableModel displayDriveable() {
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM driveable");
				ResultTableModel rtm = new ResultTableModel(rs);
				stmt.close();
				return rtm;
			}
			catch (SQLException ex) {
				//TODO
				return null;
			}
		}
	
}
