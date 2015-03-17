import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Vehicle {
	
	Connection con = OracleConnection.getInstance().getConnection();
	
	public Vehicle() {}
	
	public void insertVehicle(int vehicleNum, int age, int capacity) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO vehicle VALUES (?, ?, ?)");
			stmt.setInt(1, vehicleNum);
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
	
	public void deleteVehicle(int vehicleNum) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM vehicle WHERE vehicleNum = " + vehicleNum);
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
	
	public void displayVehicles() {
		int vehicleNum;
		int age;
		int capacity;
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle");
			
			while (rs.next()) {
				vehicleNum = rs.getInt("vehicleNum");
				age = rs.getInt("age");
				capacity = rs.getInt("capacity");
			}
			stmt.close();
		}
		catch (SQLException ex) {
			//TODO
		}
	}
}
