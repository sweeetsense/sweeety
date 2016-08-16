package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Manager_viewClientInfo extends JPanel implements ActionListener {
	/**
	 * 자산관리사의 고객 자산현황 확인
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private PreparedStatement pstmt, pstmt1;
	private ResultSet rs, rs1;
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private DefaultTableModel model;
	private JTable table;
	private JButton btn_refresh, btn_close;
	private Properties prpt = new Properties();
	private int m_code;

	public Manager_viewClientInfo(int m_code) {
		this.m_code = m_code;
		setBackground(new Color(64, 64, 64));
		init();
		btn_refresh.doClick();
	}

	private void init() {
		// table 설정
		Object[][] data = new String[0][5];
		String[] cols = { "번호", "성명", "현재자산가치", "초기자산가치", "차익" };
		model = new DefaultTableModel(data, cols) {
			/**
			 * 테이블 내용 수정 불가
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		table = new JTable(model);

		// table 컬럼 너비 지정
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);

		DefaultTableCellRenderer dtcr_c = new DefaultTableCellRenderer();
		DefaultTableCellRenderer dtcr_r = new DefaultTableCellRenderer();
		dtcr_c.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러 정렬 : CENTER
		table.getColumnModel().getColumn(0).setCellRenderer(dtcr_c);
		table.getColumnModel().getColumn(1).setCellRenderer(dtcr_c);
		dtcr_r.setHorizontalAlignment(SwingConstants.RIGHT); // 렌더러 정렬 : RIGHT
		table.getColumnModel().getColumn(2).setCellRenderer(dtcr_r);
		table.getColumnModel().getColumn(3).setCellRenderer(dtcr_r);

		btn_refresh = new JButton("REFRESH");
		btn_refresh.setBackground(Color.BLACK);
		btn_refresh.setForeground(Color.WHITE);

		btn_close = new JButton("CLOSE");
		btn_close.setBackground(Color.BLACK);
		btn_close.setForeground(Color.WHITE);

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백지정
		this.add("Center", new JScrollPane(table));
		this.add(btn_refresh);
		this.add(btn_close);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) { // double_click시_이벤트
																	// 발생
					e.consume();
					table = (JTable) e.getComponent();
					model = (DefaultTableModel) table.getModel();
					new AssetsInfo((String) model.getValueAt(table.getSelectedRow(), 0),
							(String) model.getValueAt(table.getSelectedRow(), 1));
				}
			}
		});
		listener(); // listener장착
	}

	private void accDB() { // DB연동
		try {
			prpt.load(new FileInputStream(Etc_Commons.DB_PROPERTIES));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));
		} catch (Exception e) {
			System.out.println("acc_DB Err : " + e);
		}
	}

	private void listener() {
		btn_refresh.addActionListener(this);
		btn_close.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_refresh) {
			model.setRowCount(0);
			accDB();
			try {
				String sql = " select c_code, c_name, s_code, buyprice from b_vCustomAssets where m_code = ? order by c_code ";
				pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				pstmt.setInt(1, m_code);
				rs = pstmt.executeQuery();
				String name = null;
				int rsCount = 0, no_custom = 0;
				float tot_assets = 0, tot_current = 0;
				rs.last();
				int size = rs.getRow();
				rs.beforeFirst();
				while (rs.next()) {
					rsCount++;
					String code_issue = null;
					float price_assets = 0, price_current = 0;
					if ((rsCount != 1) && (no_custom != rs.getInt("c_code"))) {
						String[] imsi = { Integer.toString(no_custom), name, df.format(tot_current),
								df.format(tot_assets), df.format(tot_assets - tot_current) };
						model.addRow(imsi);
						name = null;
						tot_assets = 0;
						tot_current = 0;
					}
					no_custom = rs.getInt("c_code");
					name = rs.getString("c_name");
					code_issue = rs.getString("s_code");
					price_assets = rs.getInt("buyprice");

					String sql1 = " select s_code, (num*d_money) as curr_price from b_vCustomAssets "
							+ " join b_vData on d_code = s_code and d_hour = (select max(d_hour) from b_vData where d_code = ?) "
							+ " where c_code = ? ";
					pstmt1 = conn.prepareStatement(sql1);
					pstmt1.setString(1, code_issue);
					pstmt1.setInt(2, no_custom);
					rs1 = pstmt1.executeQuery();
					if (rs1.next()) {
						price_current = rs1.getFloat("curr_price");
					}
					tot_assets += price_assets;
					tot_current += price_current;

					String result = df.format(Math.abs(tot_assets - tot_current));
					if (tot_assets - tot_current < 0) {
						result = "-" + result;
					} else if (tot_assets - tot_current > 0) {
						result = "+" + result;
					}

					if (size == rsCount) {
						String[] imsi = { Integer.toString(no_custom), name, df.format(tot_current),
								df.format(tot_assets), result };
						model.addRow(imsi);
					}
				}
				// 차익 폰트색상 변경위한 renderer
				TableCellRenderer renderer = new Etc_TableColorCellRenderer();
				table.getColumnModel().getColumn(4).setCellRenderer(renderer);
			} catch (Exception e2) {
				System.out.println("actionPerformed Err : " + e2);
			} finally {
				try {
					if (rs1 != null)
						rs1.close();
					if (pstmt1 != null)
						pstmt1.close();
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (Exception e3) {
					System.out.println("actionPerformed db close Err : " + e3);
				}
			}
		} else if (e.getSource() == btn_close) {
			Manager_Main.ClientStockInfo.setEnabled(true);
			Manager_Main.childClientStockInfo.dispose();
		}
	}

	class AssetsInfo extends JDialog {
		/**
		 * 고객 자산 상세보기
		 */
		private static final long serialVersionUID = 1L;
		private DefaultTableModel model1;
		private JTable table1;

		public AssetsInfo(String c_code, String c_name) {
			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			setTitle(c_name + "님의 자산 상세정보 보기");
			setModal(true);
			setSize(650, 300);
			int x = (int) ((dim.getWidth() - this.getWidth()) / 2);
			int y = (int) ((dim.getHeight() - this.getHeight()) / 2);
			setLocation(x, y);
			init_Popup();
			db_display(c_code);

			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}

		private void init_Popup() {
			String[][] data = new String[0][7];
			String[] cols = { "종목명", "주", "현재가격", "현재자산가치", "매입가격", "나의자산가치", "차익" };
			model1 = new DefaultTableModel(data, cols) {
				/**
				 * 테이블 내용 수정 불가
				 */
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int mColIndex) {
					return false;
				}
			};
			table1 = new JTable(model1);
			DefaultTableCellRenderer dtcr_c = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러
			DefaultTableCellRenderer dtcr_r = new DefaultTableCellRenderer();
			dtcr_c.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러 정렬 :
																	// CENTER
			table1.getColumnModel().getColumn(0).setCellRenderer(dtcr_c); // 종목명
			table1.getColumnModel().getColumn(1).setCellRenderer(dtcr_c); // 주
			dtcr_r.setHorizontalAlignment(SwingConstants.RIGHT); // 렌더러 정렬 :
																	// RIGHT
			table1.getColumnModel().getColumn(2).setCellRenderer(dtcr_r); // 현재가격
			table1.getColumnModel().getColumn(3).setCellRenderer(dtcr_r); // 현재자산가치
			table1.getColumnModel().getColumn(4).setCellRenderer(dtcr_r); // 매입가격
			table1.getColumnModel().getColumn(5).setCellRenderer(dtcr_r); // 나의자산가치

			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백지정
			add(new JScrollPane(table1));
		}

		private void db_display(String c_code) {
			model1.setRowCount(0);
			accDB();
			try {
				String sStockCode = null, sStockName = null;
				int iBuyCnt = 0, iBuyPrice = 0;
				String sql = " select A.s_code, s_name, num, buyprice from b_vCustomAssets A "
						+ " join B_VSTOCK B on A.s_code = B.s_code where c_code = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(c_code));
				rs = pstmt.executeQuery();
				while (rs.next()) {
					sStockCode = rs.getString("s_code");
					sStockName = rs.getString("s_name");
					iBuyCnt = rs.getInt("num");
					iBuyPrice = rs.getInt("buyprice");

					String sql1 = " select d_money from b_vData where d_hour = (select max(d_hour) from b_vData where d_code = ?) ";
					pstmt1 = conn.prepareStatement(sql1);
					pstmt1.setString(1, sStockCode);
					rs1 = pstmt1.executeQuery();
					if (rs1.next()) {

						String result = df.format(Math.abs(iBuyPrice - (rs1.getInt("d_money") * iBuyCnt)));
						if (iBuyPrice - (rs1.getInt("d_money") * iBuyCnt) < 0) {
							result = "-" + result;
						} else if (iBuyPrice - (rs1.getInt("d_money") * iBuyCnt) > 0) {
							result = "+" + result;
						}

						System.out.println("result : " + result);
						String[] imsi = { sStockName, Integer.toString(iBuyCnt), // 종목명,
																					// 주
								df.format(rs1.getInt("d_money")), df.format(rs1.getInt("d_money") * iBuyCnt), // 현재가격,
																												// 현재자산가치
								df.format(iBuyPrice / iBuyCnt), df.format(iBuyPrice), // 매입가격,
																						// 나의자산가치
								result }; // 차익
						model1.addRow(imsi);
					}
				}
				// 차익 폰트색상 변경위한 renderer
				TableCellRenderer renderer = new Etc_TableColorCellRenderer();
				table1.getColumnModel().getColumn(6).setCellRenderer(renderer);
			} catch (Exception e2) {
				System.out.println("db_display Err : " + e2);
			} finally {
				try {
					if (rs1 != null)
						rs1.close();
					if (pstmt1 != null)
						pstmt1.close();
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (Exception e3) {
					System.out.println("db_display db close Err : " + e3);
				}
			}
		}
	}

	// public static void main(String[] args) {
	// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	//
	// Manager_viewClientInfo m_clientValue = new Manager_viewClientInfo();
	// JFrame frame = new JFrame("고객 자산가치 확인");
	// frame.getContentPane().add(m_clientValue);
	// frame.setResizable(false);
	// frame.setSize(500, 520);
	// final int X = (int) ((dim.getWidth() - frame.getWidth()) / 2);
	// final int Y = (int) ((dim.getHeight() - frame.getHeight()) / 2);
	// frame.setLocation(X, Y);
	// frame.setVisible(true);
	//
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// }
}
