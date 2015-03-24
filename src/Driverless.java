import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driverless {
	Connection con = MySQLConnection.getInstance().getConnection();

	public void insertDriverless(int vehicleNumber) {
			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO driverless VALUES (?)");
				stmt.setInt(1, vehicleNumber);
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
		
		public void deleteDriverless(int vehicleNumber) {
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM driverless WHERE vehicleNumber = " + vehicleNumber);
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
		
		public ResultTableModel displayDriverless() {
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM driverless");
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
