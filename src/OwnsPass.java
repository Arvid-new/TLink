import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class OwnsPass {
	Connection con = MySQLConnection.getInstance().getConnection();

	public void insertOwnsPass(int pid, int balance, int cid) {
			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO owns_pass VALUES (?, ?, ?)");
				stmt.setInt(1, pid);
				stmt.setInt(2, balance);
				stmt.setInt(3, cid);
				stmt.executeUpdate();
				stmt.close();
			}
			catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					System.out.println("Message: " + ex.getMessage());
				}
			}
		}
		
		public void deleteOwnsPass(int pid, int cid) {
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM owns_pass WHERE pid = " + pid + " AND cid = " + cid);
				stmt.close();
			}
			catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					System.out.println("Message: " + ex.getMessage());
				}
			}
		}
		
		// Displays all the tuples from the owns_pass table
		public ResultTableModel displayOwnsPass() {
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT C.cid, name, O.pid, balance FROM customer C NATURAL JOIN owns_pass O");
				ResultTableModel rtm = new ResultTableModel(rs);
				stmt.close();
				return rtm;
			}
			catch (SQLException ex) {
				System.out.println("Message: " + ex.getMessage());
				return null;
			}
		}
		
		// Queries the own_pass table for the given customer ID and returns the customer ID, along with their name
		public ResultTableModel searchOwnsPass(int cid) {
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT C.cid, name, O.pid, balance FROM customer C NATURAL JOIN owns_pass O WHERE C.cid = " + cid);
				ResultTableModel rtm = new ResultTableModel(rs);
				stmt.close();
				return rtm;
			}
			catch (SQLException ex) {
				System.out.println("Message: " + ex.getMessage());
				return null;
			}
		}
}
