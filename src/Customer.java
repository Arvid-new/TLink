import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Customer {

	Connection con = MySQLConnection.getInstance().getConnection();

	public Customer () {}

	public void insertCustomer(int cid, String name) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO customer VALUES (?, ?)");
			stmt.setInt(1, cid);
			stmt.setString(2, name);
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

	public void deleteCustomer(int cid) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM customer WHERE cid = " + cid);
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

	public ResultTableModel displayCustomers() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}

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
			//TODO
			return false;
		}
	}

	public ResultTableModel searchCustomers(int cid) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer WHERE cid = " + cid);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}

	// Customer "login"
	public ResultTableModel login(int cid) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name, C.cid, O.pid, balance FROM customer C, owns_pass O WHERE O.cid = C.cid and C.cid = " + cid);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			//TODO
			return null;
		}
	}

	// adds to current balance
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

	public ResultTableModel displayBalance(int cid) {

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT balance FROM owns_pass WHERE cid =" + cid);
			ResultTableModel rtm = new ResultTableModel(rs);
			stmt.close();
			return rtm;
		}
		catch (SQLException ex) {
			//TODO
			return null;
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
			//TODO
			return null;
		}
	}
}
