import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ResultTableModel extends AbstractTableModel {

	private ResultSetMetaData metadata;
	private int numCols;

	public ResultTableModel(ResultSet rs) {
		
		try {
			this.metadata = rs.getMetaData();
			numCols = metadata.getColumnCount(); 
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		}

	}
	
	@Override
	public int getColumnCount() {
		return numCols;
	}

	@Override
	public int getRowCount() {
		// TODO
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO
		return null;
	}

}
