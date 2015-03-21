import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ResultTableModel extends AbstractTableModel {
	
	public boolean empty = false;

	private int numCols;
	private String[] columnNames;
	private ResultSetMetaData metadata;
	private ArrayList<Object[]> table;

	public ResultTableModel(ResultSet rs) {
		
		try {
			this.metadata = rs.getMetaData();
			this.numCols = metadata.getColumnCount(); 
			this.columnNames = new String[numCols];

			if(rs.isBeforeFirst()) {
				for(int i = 0; i < numCols; i++) {
					columnNames[i] = metadata.getColumnName(i + 1);
				}
			}
			
			formTable(rs);

		} catch (SQLException e) {
			// TODO
		}
	}
	
	private void formTable(ResultSet rs) throws SQLException {
		
		table = new ArrayList<Object[]>();

		while(rs.next() || rs.isBeforeFirst()) {

			Object[] row = new Object[numCols];
			Object val = new Object();
			
			for(int i = 0; i < numCols; i++) {
			
				int columnType = metadata.getColumnType(i + 1);
				
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
				case Types.NUMERIC:
					val = rs.getInt(i + 1);
					break;
				case Types.DATE:
					val = rs.getDate(i + 1);
					break;
				case Types.TIME:
					val = rs.getTime(i + 1);
					break;
				}

				row[i] = val;
			}

			table.add(row);
		}
		if (table.isEmpty()) {
			empty = true;
		}
	}
	
	@Override
	public int getColumnCount() {
		return numCols;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
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
