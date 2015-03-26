import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driverless extends Vehicle {
	
	Connection con = MySQLConnection.getInstance().getConnection();
	
	public Driverless() {};
	
	@Override
	public boolean insertVehicle(int vehicleNumber, int age, int capacity) {
		super.insertVehicle(vehicleNumber, age, capacity);
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO driverless VALUES (?)");
			stmt.setInt(1, vehicleNumber);
			stmt.executeUpdate();
			stmt.close();
			return true;
		}
		catch (SQLException ex) {
			return false;
		}
	}
	
	@Override
	public boolean deleteVehicle(int vehicleNumber) {
		try {
			Statement stmt = con.createStatement();
			int rows = stmt.executeUpdate("DELETE FROM vehicle WHERE vehicleNumber IN (SELECT vehicleNumber FROM Driverless) AND vehicleNumber = " + vehicleNumber);
			stmt.close();
			return (rows != 0) ? true : false;
		}
		catch (SQLException ex) {
			return false;
		}
	}

	@Override
	public ResultTableModel searchVehicles(int vehicleNumber) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM driverless WHERE vehicleNumber = " + vehicleNumber);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}
	
	@Override
	public ResultTableModel displayVehicles() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT D.vehicleNumber, V.age, V.capacity FROM driverless D INNER JOIN vehicle V ON D.vehicleNumber = V.vehicleNumber");
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
