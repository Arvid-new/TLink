import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Vehicle {
	
	Connection con = MySQLConnection.getInstance().getConnection();
	
	public Vehicle() {}
	
	// Inserts a tuple into vehicle table
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
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}
	
	// Removes the tuple with the vehicleNumber from vehicle table
	public boolean deleteVehicle(int vehicleNumber) {
		try {
			Statement stmt = con.createStatement();
			int rows = stmt.executeUpdate("DELETE FROM vehicle WHERE vehicleNumber = " + vehicleNumber);
			stmt.close();
			return (rows != 0) ? true : false;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}
	
	// Displays all vehicles from vehicle table
	public ResultTableModel displayVehicles() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}
	
	// Displays the vehicle with the given vehicleNumber from vehicle table
	public ResultTableModel searchVehicles(int vehicleNumber) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle WHERE vehicleNumber = " + vehicleNumber);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}	

	// The number of times a vehicle was driven in any given month
	public ResultTableModel generateVehicleReport(int month) {
		
		try	{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT vehicleNumber, "
					+ "COUNT(vehicleNumber) AS 'Number of times driven' "
					+ "FROM driven_by WHERE MONTH(fromDate) = " + month + " GROUP BY vehicleNumber");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}
}
