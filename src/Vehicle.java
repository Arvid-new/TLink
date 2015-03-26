import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Vehicle {
	
	Connection con = MySQLConnection.getInstance().getConnection();
	
	public Vehicle() {}
	
	public boolean insertVehicle(int vehicleNumber, int age, int capacity) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO vehicle VALUES (?, ?, ?)");
			stmt.setInt(1, vehicleNumber);
			stmt.setInt(2, age);
			stmt.setInt(3, capacity);
			stmt.executeUpdate();
			stmt.close();
			return true;
		}
		catch (SQLException ex) {
			return false;
		}
	}
	
	public boolean deleteVehicle(int vehicleNumber) {
		try {
			Statement stmt = con.createStatement();
			int rows = stmt.executeUpdate("DELETE FROM vehicle WHERE vehicleNumber = " + vehicleNumber);
			stmt.close();
			return (rows != 0) ? true : false;
		}
		catch (SQLException ex) {
			return false;
		}
	}
	
	public ResultTableModel displayVehicles() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}
	
	public ResultTableModel searchVehicles(int vehicleNumber) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle WHERE vehicleNumber = " + vehicleNumber);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}	
	
	public ResultTableModel generateVehicleReport(int month) {
		
		try	{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT vehicleNumber AS 'Vehicle Number', "
					+ "COUNT(vehicleNumber) AS 'Number of vehicles' "
					+ "FROM driven_by WHERE MONTH(fromDate) = " + month + " GROUP BY vehicleNumber");
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
