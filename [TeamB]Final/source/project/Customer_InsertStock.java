package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Customer_InsertStock extends JDialog implements ActionListener {
	/**
	 * 고객전용 : 내 자산관리
	 */
	private static final long serialVersionUID = 1L;
	private String[][] datas = new String[0][4];
	private String[] title = { "주식종목", "평균주가" };
	private DefaultTableModel model;
	private JTable table;
	private String sql, sql2;
	private JButton btn, btn2;
	private JTextField txtC = new JTextField(20);
	private JTextField txtPc;
	private JTextField txtP = new JTextField("선택해주세요");
	private JLabel lbl = new JLabel();
	private JLabel lbl2 = new JLabel("선택 종목:현재 선택 종목이 없습니다.");
	private int newNum;
	private Properties prpt = new Properties();
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int code;

	public Customer_InsertStock(int code) {
		this.code = code;

		setTitle("추가");

		design();
		display();
		setModal(true);
		setBounds(300, 300, 400, 250);
		setVisible(true);

	}

	private void accDb() {
		try {
			prpt.load(new FileInputStream("C:/work/project.properties"));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));
			sql2 = "select * from b_vportfolio order by pf_code asc";
			pstmt = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.executeQuery();
			rs = pstmt.executeQuery();
			rs.last();
			newNum = new Integer(rs.getString("pf_code"));
		} catch (Exception e) {
			System.out.println("accDb 연결 실패:" + e);
		}
	}

	private void design() {
		model = new DefaultTableModel(datas, title) {
			/**
			 * table model : 테이블 내용 수정 불가
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		;
		table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
		add("Center", pane);
		JPanel pn1 = new JPanel();
		JPanel pn2 = new JPanel();
		JPanel pn3 = new JPanel();
		JPanel pn4 = new JPanel();
		
		setBackground(new Color(64, 64, 64));
		pn1.setBackground(new Color(64, 64, 64));
		pn2.setBackground(new Color(64, 64, 64));
		pn3.setBackground(new Color(64, 64, 64));
		pn4.setBackground(new Color(64, 64, 64));
		
		btn = new JButton("Search");
		pn1.add(txtC);
		pn1.add(btn);
		btn.setBackground(Color.BLACK);		
		btn.setForeground(Color.WHITE);	
		btn.setFont(new Font("Trajan Pro", Font.BOLD, 12));

		lbl2.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		lbl2.setForeground(Color.WHITE);
		lbl.setForeground(new Color(64, 64, 64));
		pn2.add(lbl2);
		pn2.add(lbl);

		JLabel lbl_stock = new JLabel("현재 주가:");
		lbl_stock.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		lbl_stock.setForeground(Color.WHITE);
		JLabel lbl_cnt = new JLabel("추가수량:");
		lbl_cnt.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		lbl_cnt.setForeground(Color.WHITE);
		
		txtPc = new JTextField(5);
		btn2 = new JButton("Add");
		btn2.setBackground(Color.BLACK);		
		btn2.setForeground(Color.WHITE);	
		btn2.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		pn3.add(lbl_stock);
		pn3.add(txtP);
		pn3.add(lbl_cnt);
		pn3.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		pn3.setForeground(Color.WHITE);
		pn3.add(txtPc);
		pn3.add(btn2);

		pn4.setLayout(new GridLayout(3, 1));
		pn4.add(pn1);
		pn4.add(pn2);
		pn4.add(pn3);
		add("North", pn4);

		btn.addActionListener(this);
		btn2.addActionListener(this);
		txtP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					return;
				}
			}
		});
		txtPc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					return;
				}
			}
		});
	}

	private void display() {
		accDb();
		try {
			DefaultTableCellRenderer dtcr_r = new DefaultTableCellRenderer();
			dtcr_r.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumnModel().getColumn(1).setCellRenderer(dtcr_r);
			sql = "select s_name,round(avg(d_money),2) as s_avg from b_vdata join b_vstock on d_code= s_code group by s_name";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String s_name = rs.getString("s_name");
				String s_avg = rs.getString("s_avg");
				String[] imsi = { s_name, s_avg + "원" };
				model.addRow(imsi);

			}
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						table = (JTable) e.getComponent();
						model = (DefaultTableModel) table.getModel();
						sql2 = "select d_code,s_name,round(d_money) as money from b_vdata join b_vstock on s_code = d_code where s_name = ?";
						pstmt = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						pstmt.setString(1, (String) model.getValueAt(table.getSelectedRow(), 0));
						rs = pstmt.executeQuery();
						rs.last();
						txtP.setText(rs.getString("money"));
						lbl2.setText("선택 종목:" + rs.getString("s_name"));
						lbl.setText(rs.getString("d_code"));
						// System.out.println(model.getValueAt(table.getSelectedRow(),
						// 0));

					} catch (Exception e2) {
						System.out.println("mouse : " + e2);
					}

				}
			});
		} catch (Exception e) {
			System.out.println("insert display : " + e);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btn)) {
			// System.out.println("확인"+newNum);
		} else if (e.getSource().equals(btn2)) {
			if (lbl.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "종목을 선택해주세요");
			} else {
				if (txtPc.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "수량을 입력해주세요");
					txtPc.requestFocus();
				} else {
					try {
						conn.setAutoCommit(false); // 트랜젝션 오토커밋 중지
						int ok = JOptionPane.showConfirmDialog(this, "추가 하시겠습니까?", "추가", JOptionPane.YES_OPTION);
						if (ok == JOptionPane.YES_OPTION) {
							try {
								sql = "insert into b_portfolio values(?,?,?,?,?)";
								pstmt = conn.prepareStatement(sql);
								pstmt.setInt(1, code);
								pstmt.setString(2, lbl.getText());
								pstmt.setInt(3, Integer.parseInt(txtPc.getText()));
								pstmt.setString(4, txtP.getText());
								pstmt.setInt(5, (newNum + 1));
								pstmt.executeUpdate();
								conn.commit(); // 데이터처리 완료시 커밋
								JOptionPane.showMessageDialog(this, "추가가 완료 되었습니다.");
								accDb();
							} catch (Exception e2) {
								System.out.println("insertStock actionPerformed : " + e2);
							}
						} else {
							return;
						}
					} catch (Exception e2) {
						try {
							if (conn != null)
								conn.rollback(); // 데이터처리 오류시 롤백
						} catch (Exception e3) {

						}
						System.out.println("actionPerformed : " + e2);
					} finally {
						try {
							conn.setAutoCommit(true); // 트랜잭션 처리를 기본상태로 되돌린다.
						} catch (Exception e3) {
							// TODO: handle exception
						}
					}
				}

			}
		}

	}
}
