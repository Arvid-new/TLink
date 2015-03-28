import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driver {

	Connection con = MySQLConnection.getInstance().getConnection();

	public Driver() {}

	public boolean insertDriver(int empId, String name, String phoneNum, String address) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO driver VALUES (?, ?, ?, ?)");
			stmt.setInt(1, empId);
			stmt.setString(2, name);
			stmt.setString(3, address);
			stmt.setString(4, phoneNum);
			stmt.executeUpdate();
			stmt.close();
			return true;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}

	public boolean deleteDriver(int empId) {
		try {
			Statement stmt = con.createStatement();
			int rows = stmt.executeUpdate("DELETE FROM driver WHERE empId = " + empId);
			stmt.close();
			return (rows != 0) ? true : false;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}

	public ResultTableModel displayDrivers() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM driver");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}

	public ResultTableModel viewDriverInfo(int empId) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM driver WHERE empId = " + empId);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}

	// Updates the driver's address
	// no constraints on the address format....
	public void updateAddress(int empId, String address) {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE driver SET address = ? WHERE empId =" + empId);
			ps.setString(1, address);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				System.out.println("SQLException thrown in Driver.updateAddress()");
			}
		}
	}

	// Updates the driver's phone number
	// phoneNum must be 6041234567 (no spaces or - )
	public void updatePhoneNum(int empId, String phoneNum) {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE driver SET phoneNumber = ? WHERE empId =" + empId);
			ps.setString(1, phoneNum);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				System.out.println("SQLException thrown in Driver.updatePhoneNumber()");
			}
		}
	}

	// Returns all the tuples from driven_by with the given employee ID
	// date parameter is in YYYY-MM-DD format
	public ResultTableModel viewAllShifts(int empId) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM driven_by WHERE empId = " + empId
					+ " ORDER BY fromDate");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}		
	}

	// Returns the tuple from driven_by, the shift of the given employee ID and given date
	// pre-condition: date parameter is in YYYY-MM-DD format
	public ResultTableModel viewShifts(int empId, String date) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM driven_by WHERE empId = " + empId + 
					" AND DATE_FORMAT(fromDate, '%Y-%m-%d') = '" + date + "' ORDER BY fromDate");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Ensure you entered date in format YYYY-MM-DD");
			return null;
		}		
	}

	// Used to check if the given employee ID (did) exists in the driver table
	public ResultTableModel login(int did) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM driver WHERE empID = " + did);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Invalid employee ID");
			return null;
		}
	}

	// Returns all tuples from driven_by that has the given employee ID, and has a fromDate in the current week
	public ResultTableModel viewWeekShifts(int empId) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM driven_by WHERE empId = " + empId + 
					" AND WEEK(fromDate, 1) = WEEK(CURRENT_DATE) ORDER BY fromDate");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Ensure you entered date in format YYYY-MM-DD");
			return null;
		}		
	}
}
