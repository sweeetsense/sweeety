package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Manager_MyCustomer extends JPanel implements ActionListener {
	/**
	 * 자산관리사 전용 : 고객 관리
	 */
	private static final long serialVersionUID = 1L;
	private String[][] datas = new String[0][5];
	private String[] title = { "고객번호", "고객명", "생년월일", "이메일", "현재관리자" };
	private DefaultTableModel myModel, allModel;
	private JTable myTable, allTable;
	private int code;
	private String sql, port_code, c_code;
	private JButton btn_Down, btn_Up, btn_close;

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prpt = new Properties();

	public Manager_MyCustomer(int code) {
		this.code = code;
//		setTitle("내 고객목록 관리");
		design();
		displayMy();
		displayAll();
		setBounds(600, 200, 636, 600);
		setVisible(true);
	}

	private void design() {
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(64,64,64));
		panel.setLayout(null);
		// 테이블 상 (내 고객)=======================================
		JLabel lbl_bigTitle = new JLabel("내 고객목록");
		lbl_bigTitle.setFont(new Font("고딕", Font.BOLD, 30));
		lbl_bigTitle.setForeground(Color.WHITE);
		lbl_bigTitle.setBounds(225, 10, 300, 40);
		panel.add(lbl_bigTitle);

		JLabel lbl_upTitle = new JLabel("현재 고객목록");
		lbl_upTitle.setFont(new Font("고딕", Font.BOLD, 18));
		lbl_upTitle.setForeground(Color.LIGHT_GRAY);
		lbl_upTitle.setBounds(250, 50, 300, 30);
		panel.add(lbl_upTitle);

		myModel = new DefaultTableModel(datas, title);
		myTable = new JTable(myModel);
		JScrollPane myScroll = new JScrollPane(myTable);
		myScroll.setBounds(10, 80, 600, 150);
		panel.add(myScroll);

		// 버튼
		btn_Up = new JButton("▲");
		btn_Down = new JButton("▼");
		btn_Up.setBounds(250, 250, 50, 30);
		btn_Down.setBounds(320, 250, 50, 30);
		btn_Up.setBackground(Color.BLACK);
		btn_Down.setBackground(Color.BLACK);
		btn_Up.setForeground(Color.WHITE);
		btn_Down.setForeground(Color.WHITE);
		panel.add(btn_Up);
		panel.add(btn_Down);

		// 전체고객 목록
		JLabel lbl_AllClient = new JLabel("전체고객 목록");
		lbl_AllClient.setFont(new Font("고딕", Font.BOLD, 18));
		lbl_AllClient.setForeground(Color.LIGHT_GRAY);
		lbl_AllClient.setBounds(250, 300, 300, 30);
		panel.add(lbl_AllClient);

		allModel = new DefaultTableModel(datas, title);
		allTable = new JTable(allModel);
		JScrollPane allScroll = new JScrollPane(allTable);
		allScroll.setBounds(10, 330, 600, 200);
		panel.add(allScroll);
		
		JPanel pn1 = new JPanel();
		pn1.setBackground(new Color(64,64,64));
		btn_close = new JButton("Close");
		btn_close.setBackground(Color.BLACK);
		btn_close.setForeground(Color.WHITE);
		pn1.add(btn_close);
		
		add("Center", panel);
		add("South", pn1);

		listener();
	}

	private void accDb() {
		try {
			prpt.load(new FileInputStream(Etc_Commons.DB_PROPERTIES));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));

		} catch (Exception e) {
			System.out.println("accDb 연결 실패:" + e);
		}
	}

	private void displayMy() {
		try {
			accDb();
			myModel.setNumRows(0);
			sql = "select c_code, c_name, c_birth, c_email, m_name"
					+ " from b_vcustomer left outer join b_vmanager on c_mcode = m_code" + " where m_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String c_code = rs.getString("c_code");
				String c_name = rs.getString("c_name");
				String c_birth = (rs.getString("c_birth")).substring(0, 10);
				String c_email = rs.getString("c_email");
				String m_name = rs.getString("m_name");

				String[] imsi = { c_code, c_name, c_birth, c_email, m_name };
				myModel.addRow(imsi);
			}

			customerIn();
		} catch (Exception e) {
			System.out.println("전체 고객 disp err" + e);
		}
	}

	private void displayAll() {
		try {
			accDb();
			allModel.setNumRows(0);
			sql = "select c_code, c_name, c_birth, c_email, m_name from b_vcustomer left outer join b_vmanager on c_mcode = m_code where m_code is null";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String c_code = rs.getString("c_code");
				String c_name = rs.getString("c_name");
				String c_birth = (rs.getString("c_birth")).substring(0, 10);
				String c_email = rs.getString("c_email");
				String m_name = "";
				if (rs.getString("m_name") == null) {
					m_name = "관리자 없음";
				} else {
					m_name = rs.getString("m_name");
				}
				String[] imsi = { c_code, c_name, c_birth, c_email, m_name };
				allModel.addRow(imsi);
			}
			customerOut();
		} catch (Exception e) {
			System.out.println("전체 고객 disp err" + e);
		}
	}

	private void listener() {
		btn_Down.addActionListener(this);
		btn_Up.addActionListener(this);
		btn_close.addActionListener(this);
	}

	private void customerIn() {
		myTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					e.getSource();

					myTable = (JTable) e.getComponent();
					myModel = (DefaultTableModel) myTable.getModel();

					port_code = (String) myModel.getValueAt(myTable.getSelectedRow(), 0);
					System.out.println(port_code);

				} catch (Exception e2) {
					System.out.println("customerIn : " + e2);
				}

			}

		});
	}

	private void customerOut() {
		allTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					e.getSource();

					allTable = (JTable) e.getComponent();
					allModel = (DefaultTableModel) allTable.getModel();

					c_code = (String) allModel.getValueAt(allTable.getSelectedRow(), 0);
					System.out.println(c_code);

				} catch (Exception e2) {
					System.out.println("customerOut : " + e2);
				}

			}

		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_close){
			Manager_Main.Customers.setEnabled(true);
			Manager_Main.childCustomers.dispose();		
		} else{
			accDb();
			try {
				if (e.getSource().equals(btn_Up)) {
					try {
						int up = JOptionPane.showConfirmDialog(this, "추가 하시겠습니까?", "추가", JOptionPane.YES_NO_OPTION);
						conn.setAutoCommit(false);
						if (up == JOptionPane.YES_OPTION) {
							displayAll();
							sql = "update b_vcustomer set c_mcode = ? where c_code = ?";
							pstmt = conn.prepareStatement(sql);
							pstmt.setInt(1, code);
							pstmt.setString(2, c_code);
							pstmt.executeUpdate();
							conn.commit();
							displayMy();
							displayAll();
						} else {
							return;
						}
					} catch (Exception e2) {
						conn.rollback();
					} finally {
						conn.setAutoCommit(true);
					}
				} else if (e.getSource().equals(btn_Down)) {
					try {
						int down = JOptionPane.showConfirmDialog(this, "삭제 하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
						conn.setAutoCommit(false);
						if (down == JOptionPane.YES_OPTION) {
							displayMy();
							sql = "update b_vcustomer set c_mcode = null where c_code = ?";
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, port_code);
							pstmt.executeUpdate();
							conn.commit();
							displayMy();
							displayAll();
						} else {
							return;
						}
						
					} catch (Exception e2) {
						System.out.println("btn_down : " + e2);
						conn.rollback();
					} finally {
						conn.setAutoCommit(true);
					}
				}
			} catch (Exception e2) {
				System.out.println("Button err:" + e2);
			}
		}
	}
}
