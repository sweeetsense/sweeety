package project;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class Etc_TableColorCellRenderer extends DefaultTableCellRenderer {
	/**
	 * 차익 계산에 따른 폰트색상 변경을 위한 클래스
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (!isSelected) {
			String sValue = (String) value;
			sValue = sValue.replace(",", "").replace("+", "");
			
			Double dValue = Double.parseDouble(sValue);
			cell.setForeground(Color.black);
			if (dValue > 0) {
				cell.setForeground(Color.BLUE);
				
			} else if (dValue < 0) {
				cell.setForeground(Color.RED);
			}
			this.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return cell;
	}
}