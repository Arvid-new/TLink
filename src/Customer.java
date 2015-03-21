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
	
	
	// adds to current balance
	public void updateBalance(int cid, int add) {
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE owns_pass SET balance = balance + " + add + "WHERE cid = " + cid);
			con.commit();
			con.close();
		}
		catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				//TODO
			}
		}
	}
	
	public int displayBalance(int cid) {
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT balance FROM owns_pass WHERE cid =" + cid);
			stmt.close();
			return rs.getInt("balance");
		}
		catch (SQLException ex) {
			//TODO
			return 0;
		}
	}
	
	public int displayPassId(int cid) {
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT pid FROM owns_pass WHERE cid =" + cid);
			stmt.close();
			return rs.getInt("pid");
		}
		catch (SQLException ex) {
			//TODO
			return 0;
		}
	}
}
