package project;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Comm_PwSendMail extends JFrame {
	/**
	 * 회원정보 찾기 기능 클래스
	 */
	private static final long serialVersionUID = 1L;
	private CardLayout cards = new CardLayout();
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prpt = new Properties();
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private String date;
	private String sName, sId, sPw, sEmail, sQuestion, sAnwser;
	private JLabel lblQ,lblName;
	private JDialog dialog;

	public Comm_PwSendMail(JDialog dialog) {
		this.dialog = dialog;
		setSize(410, 270);
		final int X = (int) ((dim.getWidth() - this.getWidth()) / 2);
		final int Y = (int) ((dim.getHeight() - this.getHeight()) / 2);
		setLocation(X, Y);
		setTitle("ID/PW 찾기");
		setResizable(false);
		init();
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dialog.setVisible(true);
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
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

	private void init() {
		getContentPane().setLayout(cards);
		getContentPane().add("1", new P_Name(this));
		getContentPane().add("2", new P_QnA(this));
	}

	public void changePanel() {
		cards.next(this.getContentPane());
	}

	class P_Name extends JPanel { 
		/**
		 *  첫번째 카드 레이아웃(이름,생년월일)
		 */
		private static final long serialVersionUID = 1L;
		private JTextField tf_name;
		private JComboBox<String> cbYear, cbMonth, cbDay;
		private JButton btn_Ok;

		public P_Name(JFrame f) {
			// 고객정보 확인
			setLayout(new GridLayout(3, 1));
			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//			setBorder(new EmptyBorder(5, 5, 5, 5));
			setBackground(new Color(64, 64, 64));
//			setLayout(null);

			JPanel pnl1 = new JPanel();
			pnl1.setLayout(null);
			pnl1.setBackground(new Color(64, 64, 64));
			
			lblName = new JLabel(" N A  M E ");
			lblName.setBounds(115,5,300,40);
			lblName.setFont(new Font("Trajan Pro", Font.BOLD, 30));
			lblName.setForeground(Color.gray);
			pnl1.add(lblName);
			
			
			
			tf_name = new JTextField(20);
			tf_name.setBounds(20, 0, 320, 50);
//			tf_name.setText("N A M E ");
			tf_name.setForeground(Color.gray);
			tf_name.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
			tf_name.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			tf_name.setText(" ");
//			tf_name.addKeyListener(new KeyAdapter() {
//				@Override
//				public void keyPressed(KeyEvent e) {
//					if (tf_name.getText().equals("N A M E ")) {
//						tf_name.setText("");
//						return;
//					}
//				}
//			});
//			tf_name.keybor
			pnl1.add(tf_name);
			
			JPanel pnl2 = new JPanel();
			pnl2.setBackground(new Color(64, 64, 64));
			// 생년월일
			JLabel lbl2 = new JLabel("");
			pnl2.setLayout(null);
			pnl2.add(lbl2);
			// 콤보박스-생년월일-년
			cbYear = new JComboBox<String>();
			cbYear.setBackground(Color.WHITE);
			cbYear.setForeground(Color.gray);
			cbYear.setBounds(20, 0, 110, 50);
			cbYear.setFont(new Font("Trajan Pro", Font.PLAIN, 20));
			pnl2.add(cbYear);
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			cbYear.addItem("YEAR");
			for (int i = year - 1; i > 1900; i--) {
				cbYear.addItem(i + "");
			}
			// 콤보박스-생년월일-월
			cbMonth = new JComboBox<String>();
			cbMonth.setBackground(Color.WHITE);
			cbMonth.setForeground(Color.gray);
			cbMonth.setBounds(130, 0, 105, 50);
			cbMonth.setFont(new Font("Trajan Pro", Font.PLAIN, 20));
			pnl2.add(cbMonth);
			cbMonth.addItem("MONTH");
			for (int i = 1; i <= 12; i++) {
				cbMonth.addItem(i + "");
			}
			// 콤보박스-생년월일-일
			cbDay = new JComboBox<String>();
			cbDay.setBackground(Color.WHITE);
			cbDay.setForeground(Color.gray);
			cbDay.setBounds(235, 0, 105, 50);
			cbDay.setFont(new Font("Trajan Pro", Font.PLAIN, 20));
			pnl2.add(cbDay);
			cbDay.addItem("DAY");
			for (int i = 1; i <= 31; i++) {
				cbDay.addItem(i + "");
			}

			JPanel pnl3 = new JPanel();
			pnl3.setLayout(null);
			btn_Ok = new JButton("NEXT");
			pnl3.setBackground(new Color(64, 64, 64));
			btn_Ok.setBackground(Color.BLACK);
			btn_Ok.setForeground(Color.WHITE);
			btn_Ok.setBounds(20, 0, 320, 50);
			btn_Ok.setFont(new Font("Trajan Pro", Font.PLAIN, 20));
			pnl3.add(btn_Ok);

			// 일 범위 입력
			cbDay.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					int[] day = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
					String sYear = (String) cbYear.getSelectedItem();
					int iDay = day[cbMonth.getSelectedIndex()];
					if ((cbMonth.getSelectedIndex() == 1)
							&& ((Integer.parseInt(sYear) % 4 == 0 && Integer.parseInt(sYear) % 100 != 0)
									|| Integer.parseInt(sYear) % 400 == 0)) {
						iDay = 29;
					}
					cbDay.removeAllItems();
					for (int i = 1; i <= iDay; i++) {
						cbDay.addItem(i + "");
					}
				}
			});

			btn_Ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String year = null, month = null, day = null;

					if (tf_name.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(f, "성명을 입력하세요.");
						return;
					}

					year = (String) cbYear.getSelectedItem();
					year = year.substring(2, 4);
					if (cbMonth.getSelectedIndex() < 10) { // 날짜 형식 맞추기
						month = "0" + (String) cbMonth.getSelectedItem();
					} else {
						month = (String) cbMonth.getSelectedItem();
					}
					if (cbDay.getSelectedIndex() < 10) { // 날짜 형식 맞추기
						day = "0" + (String) cbDay.getSelectedItem();
					} else {
						day = (String) cbDay.getSelectedItem();
					}
					// 날짜 DB형식으로 맞추기
					date = year + "/" + month + "/" + day;

					accDb(); // DB연결
					try {
						// 회원 개인정보 일치여부 확인 & 가져오기
						sName = "";
						sId = "";
						sPw = "";
						sEmail = "";
						sQuestion = "";
						sAnwser = "";
						String sql = " select c_name, c_id, c_pw, c_email, q_text, c_answer from b_vCustomer "
								+ " join b_vQuestion on c_queno=q_no where c_name = ? and c_birth = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, tf_name.getText().trim());
						pstmt.setString(2, date);
						rs = pstmt.executeQuery();
						if (rs.next()) {
							sName = rs.getString("c_name");
							sId = rs.getString("c_id");
							sPw = rs.getString("c_pw");
							sEmail = rs.getString("c_email");
							sQuestion = rs.getString("q_text");
							sAnwser = rs.getString("c_answer");
							lblQ.setText(sQuestion); // 다음 cardLayout에 질문설정
							((Comm_PwSendMail) f).changePanel(); // 다음 cardLayout
															// 불러오기
						} else {
							JOptionPane.showMessageDialog(f, "등록된 회원이 아닙니다.");
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(f, "날짜 형식이 맞지않습니다.");
					} finally {
						try {
							if (rs != null)
								rs.close();
							if (pstmt != null)
								pstmt.close();
							if (conn != null)
								conn.close();
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				}
			});

			// Panel Setting
			add(pnl1);
//			pnl1.setBounds(0, 0, 400, 50);
			add(pnl2);
			add(pnl3);

			setVisible(true);
		}
	}

	class P_QnA extends JPanel {
		/**
		 * 두번째 카드 레이아웃(Q&A)
		 */
		private static final long serialVersionUID = 1L;
		private JTextField tf_answer;
		private JButton btn_Ok;

		public P_QnA(JFrame f) {
			setLayout(new GridLayout(3, 1));
			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			setBackground(new Color(64, 64, 64));

			JPanel pnl1 = new JPanel();
			pnl1.setLayout(null);
			pnl1.setBounds(0, 0, 390, 50);
			lblQ = new JLabel("");
			lblQ.setBounds(0,0,370,50);
			lblQ.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			lblQ.setForeground(Color.WHITE);
			lblQ.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			pnl1.setBackground(new Color(64, 64, 64));
			pnl1.add(lblQ);

			JPanel pnl2 = new JPanel();
			pnl2.setBackground(new Color(64, 64, 64));
			pnl2.setLayout(null);
			pnl2.setBounds(0, 0, 410, 50);
			tf_answer = new JTextField(25);
			tf_answer.setBounds(20,0,325, 50);
			tf_answer.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			tf_answer.setForeground(Color.GRAY);
			pnl2.add(tf_answer);

			JPanel pnl3 = new JPanel();
			pnl3.setBackground(new Color(64, 64, 64));
			pnl3.setLayout(null);
			pnl3.setBounds(0, 0, 410, 50);
			btn_Ok = new JButton("SEND");
			btn_Ok.setBounds(20,0,325, 50);
			btn_Ok.setFont(new Font("Trajan Pro", Font.PLAIN, 20));
			btn_Ok.setForeground(Color.WHITE);
			btn_Ok.setBackground(Color.BLACK);
			pnl3.add(btn_Ok);

			btn_Ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(sAnwser);
					if (sAnwser.equals(tf_answer.getText().trim())) {
						// email 전송
						send(sEmail);
					} else {
						JOptionPane.showMessageDialog(f, "질문에 대한 답변이 일치하지 않습니다.");
					}
				}
			});

			// Panel Setting
			add(pnl1);
			add(pnl2);
			add(pnl3);

			setVisible(true);
		}
	}

	private void send(String toMail) {
		final String fromName = "Stock Manager Program 관리자";
		final String subject = "회원정보 찾기에 대한 답변";
		String content = sName + "님의 회원정보입니다.\nID : " + sId + "\nPW : " + sPw;

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
			msg.setHeader("content-type", "text/plain; charset=utf-8");
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail, fromName, "utf-8"));

			// 보낼 때
			msg.setSubject(subject);
			msg.setContent(content, "text/plain; charset=utf-8");
			msg.setSentDate(new Date());

			Transport.send(msg); // 메일 전송
			JOptionPane.showMessageDialog(this, "가입시 등록한 e-mail(" + sEmail + ")로\n메일이 전송되었습니다.");
			dialog.setVisible(true);
			dispose();
		} catch (Exception e) {
			System.out.println("sendMail error : " + e);
		}
	}
}