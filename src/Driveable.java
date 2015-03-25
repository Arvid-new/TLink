import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driveable extends Vehicle {
	
	Connection con = MySQLConnection.getInstance().getConnection();
	
	public Driveable() {};
	
	public void insertVehicle(int vehicleNumber, int age, int capacity, String type) {
		super.insertVehicle(vehicleNumber, age, capacity);
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

	@Override
	public void deleteVehicle(int vehicleNumber) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM vehicle WHERE vehicleNumber IN (SELECT vehicleNumber FROM driveable) AND vehicleNumber = " + vehicleNumber);
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
