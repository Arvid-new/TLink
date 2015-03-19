import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ResultTableModel extends AbstractTableModel {

	private int numCols;
	private ResultSetMetaData metadata;
	private ArrayList<Object[]> table;

	public ResultTableModel(ResultSet rs) {
		
		try {
			this.metadata = rs.getMetaData();
			this.numCols = metadata.getColumnCount(); 
			formTable(rs);
		} catch (SQLException e) {
			// TODO
		}
	}
	
	private void formTable(ResultSet rs) throws SQLException {
		
		Object val = new Object();		
		Object[] row = new Object[numCols];

		while(rs.next()) {
			for(int i = 0; i < numCols; i++) {
				int columnType = metadata.getColumnType(i);
				
				switch(columnType) {
				case Types.CHAR:
					val = rs.getString(i + 1);
					break;
				case Types.VARCHAR:
					val = rs.getString(i + 1);
					break;
				case Types.INTEGER:
					val = rs.getInt(i + 1);	
					break;
				case Types.DOUBLE:
					val = rs.getDouble(i + 1);
					break;
				case Types.DATE:
					val = rs.getDate(i + 1);
					break;
				case Types.TIMESTAMP:
					val = rs.getDate(i + 1);
					break;
				}

				row[i] = val;
			}
			
			table.add(row);
		}
	}
	
	@Override
	public int getColumnCount() {
		return numCols;
	}

	@Override
	public int getRowCount() {
		return table.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return table.get(row)[col];
	}

}
