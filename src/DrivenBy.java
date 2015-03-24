import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DrivenBy {
	Connection con = MySQLConnection.getInstance().getConnection();

	public void insertDrivenBy(int vehicleNumber, Date from, Date to, int empId) {
			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO driven_by VALUES (?, ?, ?, ?)");
				stmt.setInt(1, vehicleNumber);
				stmt.setDate(2, from);
				stmt.setDate(3, to);
				stmt.setInt(4, empId);
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
		
		public void deleteDrivenBy(int vehicleNumber, Date from, Date to, int empId) {
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM driveable WHERE vehicleNumber = " + vehicleNumber + " AND from = " + from
						+ " AND to = " + to + " AND empId = " + empId);
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
		
		public ResultTableModel displayDrivenBy() {
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM driven_by");
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
