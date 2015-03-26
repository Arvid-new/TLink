import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driveable extends Vehicle {
	
	Connection con = MySQLConnection.getInstance().getConnection();
	
	public Driveable() {};
	
	public boolean insertVehicle(int vehicleNumber, int age, int capacity, String type) {
		super.insertVehicle(vehicleNumber, age, capacity);
		try {			
			PreparedStatement stmt = con.prepareStatement("INSERT INTO driveable VALUES (?, ?)");
			stmt.setInt(1, vehicleNumber);
			stmt.setString(2, type);
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
			int rows = stmt.executeUpdate("DELETE FROM vehicle WHERE vehicleNumber IN (SELECT vehicleNumber FROM driveable) AND vehicleNumber = " + vehicleNumber);
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM driveable WHERE vehicleNumber = " + vehicleNumber);
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
			ResultSet rs = stmt.executeQuery("SELECT D.vehicleNumber, D.type, V.age, V.capacity FROM driveable D INNER JOIN vehicle V ON D.vehicleNumber = V.vehicleNumber");
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
