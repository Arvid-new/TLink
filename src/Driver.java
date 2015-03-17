import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driver {
	
	Connection con = OracleConnection.getInstance().getConnection();
	
	public Driver() {}
	
	public void insertDriver(int empId, String name, String address, String phoneNum) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO driver VALUES (?, ?, ?, ?)");
			stmt.setInt(1, empId);
			stmt.setString(2, name);
			stmt.setString(3, address);
			stmt.setString(4, phoneNum);
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
	
	public void deleteDriver(int empId) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM driver WHERE empId = " + empId);
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
	
	public void displayDrivers() {
		int empId;
		String name;
		String address;
		String phoneNum;
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM driver");
			
			while (rs.next()) {
				empId = rs.getInt("empId");
				name = rs.getString("name");
				address = rs.getString("address");
				phoneNum = rs.getString("phoneNum");
			}
			stmt.close();
		}
		catch (SQLException ex) {
			//TODO
		}
	}
	
	public void updateAddress(int empId, String address) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE driver SET address = '" + address + "' WHERE empId = " + empId);
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
	
	public void updatePhoneNum(int empId, String phoneNum) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE driver SET phoneNum = '" + phoneNum + "' WHERE empId = " + empId);
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
	
	// date parameter is in YYYY-MM-DD format
	public void viewShifts(int empId, String date) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("SELECT * FROM driven_by WHERE empId = " + empId + "to_char(from, 'YYYY-MM-DD') =" + date);
			stmt.close();
		}
		catch (SQLException ex) {
			//TODO
		}
	}
}
