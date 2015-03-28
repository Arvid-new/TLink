import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Has {
	Connection con = MySQLConnection.getInstance().getConnection();

	public void insertHas(int routeNum, int stopNum) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO has VALUES (?, ?)");
			stmt.setInt(1, routeNum);
			stmt.setInt(2, stopNum);
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

	public void deleteHas(int routeNum, int stopNum) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM has WHERE stopNumber = " + stopNum + " AND routeNumber = " + routeNum);
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

	public ResultTableModel displayHas() {

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM has");
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
