import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Customer {

	Connection con = MySQLConnection.getInstance().getConnection();

	public Customer () {}

	public boolean insertCustomer(int cid, String name) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO customer VALUES (?, ?)");
			stmt.setInt(1, cid);
			stmt.setString(2, name);
			stmt.executeUpdate();
			stmt.close();
			return true;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}

	public boolean deleteCustomer(int cid) {
		try {
			Statement stmt = con.createStatement();
			int rows = stmt.executeUpdate("DELETE FROM customer WHERE cid = " + cid);
			stmt.close();
			return (rows != 0) ? true: false;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}

	public ResultTableModel displayCustomers() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}

	// Returns true if the given customer ID has accessed every vehicle; false otherwise.
	public boolean accessedAllVehicles(int cid){
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT cid\n" +
					"FROM owns_pass O\n" +
					"WHERE cid = " + cid + " AND NOT EXISTS(\n" +
					"\tSELECT V.vehicleNumber\n" +
					"\tFROM vehicle V\n" +
					"\tWHERE NOT EXISTS\n" +
					"\t(SELECT A.vehicleNumber\n" +
					"\tFROM access A\n" +
					"\tWHERE V.vehicleNumber = A.vehicleNumber\n" +
					"\tAND A.cid = O.cid));");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return !rtm.empty;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}

	// Search for a customer with the given customer ID
	public ResultTableModel searchCustomers(int cid) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer WHERE cid = " + cid);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}

	// Checks if the given customer ID is in the customer table
	public ResultTableModel login(int cid) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name, C.cid, O.pid, balance FROM customer C, owns_pass O WHERE O.cid = C.cid and C.cid = " + cid);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}

	// adds the given amount to the current balance
	public void updateBalance(int cid, int add) {

		try {
			PreparedStatement ps = con.prepareStatement("UPDATE owns_pass SET balance = balance + ? WHERE cid =" + cid);
			ps.setInt(1, add);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				System.out.println("SQLException thrown in Customer.updateBalance()");
			}
		}
	}

	// For operator to update the name of the customer
	public void updateName(int cid, String name) {

		try {
			PreparedStatement ps = con.prepareStatement("UPDATE customer SET name = ? WHERE cid =" + cid);
			ps.setString(1, name);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				System.out.println("SQLException thrown in Customer.updateName()");
			}
		}
	}

	public ResultTableModel displayPassId(int cid) {

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT pid FROM owns_pass WHERE cid =" + cid);
			ResultTableModel rtm = new ResultTableModel(rs);
			return rtm;
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}
}
