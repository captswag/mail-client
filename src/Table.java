import javax.swing.JTable;


public class Table extends JTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Table()
	{
		super();
	}
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

}
