import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConnection {
	private static MySQLConnection connection = null;
	protected static Connection con = null;
	protected boolean driverLoaded = false;
	
	public MySQLConnection() {
		
		String url = "jdbc:mysql://localhost:3306/tlink";
		String username = "root";
		String password = "float"; // enter your own
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager
					.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			// TODO:
			System.out.println("JDBC drivers not found");
		} catch (SQLException e) {
			// TODO:
			System.out.println("Enter your password in MySQLConnection class");
		}	
	}
	
	public static MySQLConnection getInstance() {
		if (connection == null) {
			connection = new MySQLConnection();
		}
		
		return connection;
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public void setConnection(Connection con) {
		this.con = con;
	}
}
