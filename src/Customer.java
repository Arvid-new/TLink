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
	
	public void deleteCustomer(int cid) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM customer WHERE cid = " + cid);
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
	
	// Customer "login"
	public ResultTableModel login(String cid) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer, pass WHERE cid = " + cid);
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
	public ResultTableModel updateBalance(int cid, int add) {
		
		try {
			Statement stmt = con.createStatement();
			String selectBalQueryStr = "SELECT balance FROM owns_pass WHERE cid =" + cid;
			ResultSet initResult = stmt.executeQuery(selectBalQueryStr);
			int initBal = initResult.getInt("balance");
			int newBal = initBal + add;
			String newBalStr = String.valueOf(newBal);
			String updateStr = "UPDATE owns_pass SET balance = " + newBalStr;
			stmt.executeUpdate(updateStr);
			ResultSet rs = stmt.executeQuery(selectBalQueryStr);
			ResultTableModel rtm = new ResultTableModel(rs);
			return rtm;
		}
		catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO
			}
			return null;
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
