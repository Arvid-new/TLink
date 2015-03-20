import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Vehicle {
	
	Connection con = OracleConnection.getInstance().getConnection();
	
	public Vehicle() {}
	
	public void insertVehicle(int vehicleNumber, int age, int capacity) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO vehicle VALUES (?, ?, ?)");
			stmt.setInt(1, vehicleNumber);
			stmt.setInt(2, age);
			stmt.setInt(3, capacity);
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
	
	public void deleteVehicle(int vehicleNumber) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM vehicle WHERE vehicleNumber = " + vehicleNumber);
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
	
	public ResultSet displayVehicles() {
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle");
			stmt.close();
			return rs;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}
	
	public ResultSet generateVehicleReport() {
		
		try	{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT vehicleNumber, COUNT(vehicleNumber) "
					+ "FROM driven_by WHERE MONTH(from) = MONTH(CURRENT_DATE)");
			stmt.close();
			return rs;
		}
		catch (SQLException ex) {
			//TODO 
			return null;
		}
	}
	
	
}
