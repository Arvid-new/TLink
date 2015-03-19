import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Customer {

	Connection con = OracleConnection.getInstance().getConnection();

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
	
	public void displayCustomers() {
		int cid;
		String name;
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
			
			while(rs.next()) {
				cid = rs.getInt("cid");
				name = rs.getString("name");
			}

			stmt.close();
		}
		catch (SQLException ex) {
			//TODO
			System.out.println("EXCEPTION");
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
}
