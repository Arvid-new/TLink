import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class OracleConnection {

	private static OracleConnection connection = null;
	protected static Connection con = null;
	protected boolean driverLoaded = false;
	
	public OracleConnection() {
		try {
			
			String url = ""; 
			
			if (!driverLoaded) {
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				driverLoaded = true;
			}
			
			con = DriverManager.getConnection(url, "", "");
		}
		catch (SQLException ex) {
			// TODO
		}
	}
	
	public static OracleConnection getInstance() {
		if (connection == null) {
			connection = new OracleConnection();
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
