package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Manager_AssetsSendMail extends JPanel implements ActionListener {
	/**
	 * 자산관리사가 고객에게 포트폴리오(메일) 전송하는 클래스
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private PreparedStatement pstmt, pstmt1;
	private ResultSet rs, rs1;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private DefaultTableModel model;
	private JTable table;
	private JButton btn_send, btn_close;
	private JCheckBox box = new JCheckBox();
	private Properties prpt = new Properties();
	private int check_cnt = 0, mail_cnt = 0;
	private int m_code; // 자산관리사 고유 번호

	public Manager_AssetsSendMail(int m_code) {
		this.m_code = m_code;
//		setSize(500, 300);
		init();
		db_customInfo();
		// table에 체크박스 지정
		table.getColumnModel().getColumn(0).setCellRenderer(dcr);
		box.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(box));
	}
	private void init() {
		setLayout(new BorderLayout());
		// table 설정
		Object[][] data = new Object[0][7];
		Object[] cols = { "선택", "번호", "성명", "현재자산가치", "초기자산가치", "차익" };

		model = new DefaultTableModel(data, cols) { // 테이블 내용 수정 불가
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				if (mColIndex == 0) // 체크박스는 수정 가능
					return true;
				else
					return false;
			}
		};
		table = new JTable(model);

		// table 컬럼 너비 지정
		table.getColumnModel().getColumn(0).setPreferredWidth(5);
		table.getColumnModel().getColumn(1).setPreferredWidth(5);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);

		DefaultTableCellRenderer dtcr_c = new DefaultTableCellRenderer();
		DefaultTableCellRenderer dtcr_r = new DefaultTableCellRenderer();
		dtcr_c.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러 정렬 : CENTER
		table.getColumnModel().getColumn(1).setCellRenderer(dtcr_c);
		table.getColumnModel().getColumn(2).setCellRenderer(dtcr_c);
		dtcr_r.setHorizontalAlignment(SwingConstants.RIGHT); // 렌더러 정렬 : RIGHT
		table.getColumnModel().getColumn(3).setCellRenderer(dtcr_r);
		table.getColumnModel().getColumn(4).setCellRenderer(dtcr_r);

		JPanel pnl = new JPanel();
		btn_send = new JButton("Send Mail");
		btn_close = new JButton("Close");
		pnl.add(btn_send);
		pnl.add(btn_close);

		setBackground(new Color(64, 64, 64));
		pnl.setBackground(new Color(64, 64, 64));
		btn_send.setBackground(Color.BLACK);		
		btn_send.setForeground(Color.WHITE);	
		btn_send.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		btn_close.setBackground(Color.BLACK);		
		btn_close.setForeground(Color.WHITE);	
		btn_close.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백지정

		this.add("Center", new JScrollPane(table));
		this.add("South", pnl);

		listener();
	}

	private void listener() {
		btn_send.addActionListener(this);
		btn_close.addActionListener(this);
	}

	private void accDB() {
		try {
			prpt.load(new FileInputStream(Etc_Commons.DB_PROPERTIES));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));
		} catch (Exception e) {
			System.out.println("accDb 연결 실패:" + e);
		}
	}

	private void db_customInfo() {
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
					Object[] imsi = { false, Integer.toString(no_custom), name, df.format(tot_current),
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

				if (size == rsCount) {
					Object[] imsi = { false, Integer.toString(no_custom), name, df.format(tot_current),
							df.format(tot_assets), df.format(tot_assets - tot_current) };
					model.addRow(imsi);
				}
			}
			// 차익 폰트색상 변경위한 renderer
			TableCellRenderer renderer = new Etc_TableColorCellRenderer();
			table.getColumnModel().getColumn(5).setCellRenderer(renderer);
		} catch (Exception e2) {
			System.out.println("db_customInfo Err : " + e2);
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
				System.out.println("db_customInfo db close Err : " + e3);
			}
		}
	}

	private void db_customMail() {
		accDB();
		String sql = " select C.c_name, C.c_email, M.m_name, M.m_email, A.s_code, S.s_name, A.num from b_vCustomAssets A "
				+ " join B_VCUSTOMER C on A.c_code = C.c_code " + " join B_VMANAGER M on A.m_code = M.m_code "
				+ " join B_VSTOCK S on A.s_code = S.s_code where C.c_code = ? ";
		try {
			for (int i = 0; i < table.getRowCount(); i++) {
				Boolean check_send = ((Boolean) table.getValueAt(i, 0)).booleanValue();
				if (check_send == true) { // check box가 true인지 확인
					String cName = null, cMail = null, mName = null, mMail = null;
					String sMonth = null, sDay = null;
					float yesterdayLast = 0, todayLast = 0;
					int rowCnt = 0;
					pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					pstmt.setString(1, (String) table.getValueAt(i, 1)); // 고객
																			// 번호를
																			// 이용해
																			// e-mail주소
																			// 획득
					rs = pstmt.executeQuery();
					rs.last();
					int size = rs.getRow();
					rs.beforeFirst();
					Object[][] cStock_info = new Object[size][7];
					while (rs.next()) {
						cName = rs.getString("c_name");
						cMail = rs.getString("c_email");
						mName = rs.getString("m_name");
						mMail = rs.getString("m_email");
						try {
							String sql1 = " select d_money from b_vData where d_code = ? and d_hour = (select max(d_hour) from b_vData where d_code = ?) ";
							pstmt1 = conn.prepareStatement(sql1);
							pstmt1.setString(1, rs.getString("s_code"));
							pstmt1.setString(2, rs.getString("s_code"));
							rs1 = pstmt1.executeQuery();
							if (rs1.next()) {
								todayLast = rs1.getFloat("d_money");
							}
						} catch (Exception e) {
							System.out.println("db_customMail sql1 Err : " + e);
						} finally {
							try {
								if (rs1 != null)
									rs1.close();
								if (pstmt1 != null)
									pstmt1.close();
							} catch (Exception e3) {
								System.out.println("db_customMail sql1 close Err : " + e3);
							}
						}

						if (Etc_Commons.MONTH < 10){
							sMonth = "0" + Etc_Commons.MONTH; 
						}else{
							sMonth = Integer.toString(Etc_Commons.MONTH);
						}
						if (Etc_Commons.DAY - 1 < 10){
							sDay = "0" + (Etc_Commons.DAY -1); 
						}else{
							sDay = Integer.toString(Etc_Commons.DAY-1);
						}
						
						try {
							String sql2 = " select d_money from b_vData where d_code = ? and d_hour = (select max(d_hour) from b_vData where d_code = ? and d_hour like ?) ";
							pstmt1 = conn.prepareStatement(sql2);
							pstmt1.setString(1, rs.getString("s_code"));
							pstmt1.setString(2, rs.getString("s_code"));
							pstmt1.setString(3, Etc_Commons.YEAR + "-" + sMonth + "-" + sDay + "%");
							rs1 = pstmt1.executeQuery();
							if (rs1.next()) {
								yesterdayLast = rs1.getFloat("d_money");
							}
						} catch (Exception e) {
							System.out.println("db_customMail sql2 Err : " + e);
						} finally {
							try {
								if (rs1 != null)
									rs1.close();
								if (pstmt1 != null)
									pstmt1.close();
							} catch (Exception e3) {
								System.out.println("db_customMail sql2 close Err : " + e3);
							}
						}
						cStock_info[rowCnt][0] = (rowCnt + 1);
						cStock_info[rowCnt][1] = rs.getString("s_name"); // 종목명
						cStock_info[rowCnt][2] = todayLast; // 현재가
						cStock_info[rowCnt][3] = rs.getInt("num"); // 보유량
						cStock_info[rowCnt][4] = yesterdayLast - todayLast; // 종목변동
						cStock_info[rowCnt][5] = (yesterdayLast * rs.getInt("num")) - (todayLast * rs.getInt("num")); // 자산변동
						cStock_info[rowCnt][6] = todayLast * rs.getInt("num"); // 자산 총평가

						rowCnt++;
					}
					mail_cnt++;
					Etc_HTMLtxtMaker htmlTxt = new Etc_HTMLtxtMaker();
					send(cMail, htmlTxt.makeHTMLtxt(cName, cStock_info, mName, mMail));
				}
			}
		} catch (Exception e) {
			System.out.println("db_customMail db Err : " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e3) {
				System.out.println("db_customMail db close Err : " + e3);
			}
		}
	}

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		/**
		 * Table에 있는 Check Box Renderer
		 */
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent // 셀렌더러
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btn_send)) {
			check_cnt = 0;
			mail_cnt = 0;
			for (int i = 0; i < table.getRowCount(); i++) {
				if ((boolean) table.getValueAt(i, 0) == true) {
					check_cnt++;
				}
			}
			if (check_cnt == 0) {
				JOptionPane.showMessageDialog(this, "선택된 고객이 없습니다.");
				return;
			}
			db_customMail();
		} else if (e.getSource().equals(btn_close)) {
			Manager_Main.AssetSendMail.setEnabled(true);
			Manager_Main.childAssetSendMail.dispose();			
		}
	}

	private void checkAll_false() {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(false, i, 0);
		}
	}

	private void send(String toMail, String html) {
		final String fromName = "Stock Manager Program 관리자";
		final String subject = "[Stock Manager] 현재시간 고객님의 자산 포트폴리오";
		try {
			// 서버에 대한 설정
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.auth", "true");

			// 인증에 대한 부분
			Etc_MailAuth auth = new Etc_MailAuth(Etc_Commons.EmailID, Etc_Commons.EmailPW);
			Session session = Session.getDefaultInstance(props, auth);

			// 메시지 내용
			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("content-type", "text/HTML; charset=euc-kr");
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail, fromName, "euc-kr"));

			Multipart multipart = new MimeMultipart();

			// 메일 내용 Setting
			MimeBodyPart msgBodyPart = new MimeBodyPart();
			msgBodyPart.setContent(html, "text/HTML; charset=euc-kr"); // HTML
																		// setting
			multipart.addBodyPart(msgBodyPart);
			msg.setContent(multipart);

			// 보낼 때
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			Transport.send(msg); // 메일 전송

			if (check_cnt == mail_cnt) {
				JOptionPane.showMessageDialog(this, check_cnt + "건의 포트폴리오 전송이 완료되었습니다.");
				checkAll_false();
			}
		} catch (Exception e) {
			System.out.println("sendMail error : " + e);
		}
	}

//	public static void main(String[] args) {
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//
//		Manager_AssetsSendMail m_assetsSendMail = new Manager_AssetsSendMail(1);
//		JFrame frame = new JFrame("고객 포트폴리오 메일 전송");
//		frame.getContentPane().add(m_assetsSendMail);
//		frame.setResizable(false);
//		frame.setSize(500, 300);
//		final int X = (int) ((dim.getWidth() - frame.getWidth()) / 2);
//		final int Y = (int) ((dim.getHeight() - frame.getHeight()) / 2);
//		frame.setLocation(X, Y);
//		frame.setVisible(true);
//
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}