package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Manager_LogIn extends JDialog implements ActionListener {
	/**
	 * Login창 : 자산관리자 전용
	 */
	private static final long serialVersionUID = 1L;
	JLabel lblId, lblPw;
	JTextField txtId;
	JPasswordField txtPw;
	JButton btnLogin, btnPw, btnJoin;
	String sql;

	private Properties prpt = new Properties();
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	public Manager_LogIn(JDialog dialog) {

		design();
		// setModal(true);
		setTitle("자산관리사 로그인");
		setSize(400, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int X = (int) ((dim.getWidth() - this.getWidth()) / 2);
		final int Y = (int) ((dim.getHeight() - this.getHeight()) / 2);
		setLocation(X, Y);
		setVisible(true);
		setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				dialog.setVisible(true);
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
	}

	// 디자인==========================
	private void design() {
		JPanel panel = new JPanel();
		add(panel);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBackground(new Color(64, 64, 64));
		panel.setLayout(null);
		setContentPane(panel);

		// 아이디 행
		lblId = new JLabel("I  D");
		lblId.setBounds(182, 40, 70, 30);
		lblId.setFont(new Font("Trajan Pro", Font.BOLD, 20));
		lblId.setForeground(Color.GRAY);
		panel.add(lblId);

		txtId = new JTextField();
		txtId.setBounds(28, 25, 340, 50);
		txtId.setFont(new Font("Trajan Pro", Font.PLAIN, 30));
		txtId.setHorizontalAlignment((int) CENTER_ALIGNMENT);

		panel.add(txtId);

		// 패스워드 행
		lblPw = new JLabel("P  W");
		lblPw.setBounds(177, 85, 70, 30);
		lblPw.setFont(new Font("Trajan Pro", Font.BOLD, 20));
		lblPw.setForeground(Color.GRAY);
		panel.add(lblPw);

		txtPw = new JPasswordField();
		txtPw.setBounds(28, 75, 340, 50);
		txtPw.setFont(new Font("Trajan Pro", Font.PLAIN, 30));
		txtPw.setHorizontalAlignment((int) CENTER_ALIGNMENT);

		panel.add(txtPw);

		// 버튼==============================================
		btnLogin = new JButton(" S I G N I N ");
		btnLogin.setBounds(28, 135, 340, 50);
		btnLogin.setFont(new Font("Trajan Pro", Font.BOLD, 20));
		btnLogin.setBackground(Color.BLACK);
		btnLogin.setForeground(Color.WHITE);
		panel.add(btnLogin);

		btnPw = new JButton("LOST");
		btnPw.setBounds(28, 200, 172, 25);
		btnPw.setFont(new Font("Trajan Pro", Font.BOLD, 10));
		btnPw.setBackground(Color.BLACK);
		btnPw.setForeground(Color.WHITE);
		panel.add(btnPw);

		btnJoin = new JButton("JOIN");
		btnJoin.setBounds(200, 200, 168, 25);
		btnJoin.setFont(new Font("Trajan Pro", Font.BOLD, 10));
		btnJoin.setBackground(Color.BLACK);
		btnJoin.setForeground(Color.WHITE);
		panel.add(btnJoin);
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		btnPw.addActionListener(this);
	}

	//
	public void accDb() {
		try {
			prpt.load(new FileInputStream(Etc_Commons.DB_PROPERTIES));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));

		} catch (Exception e) {
			System.out.println("accDb 연결 실패:" + e);
		}
	}

	private void logIn() {
		try {
			accDb();

			sql = "select m_code, m_name from b_vmanager where m_id = ? and m_pw = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, txtId.getText());
			String PW = new String(txtPw.getPassword());
			pstmt.setString(2, PW);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int str = rs.getInt("m_code");
				new Manager_Main(str);
				dispose();
			} else {
				UIManager.put("Button.background", Color.WHITE);
//				Font font = UIManager.getFont("OptionPane.font");
				UIManager.put("OptionPane.background", Color.white);
				UIManager.put("Panel.background", Color.white);
				UIManager.put("OptionPane.titlePane.background", Color.white);
				UIManager.put("Button.font", new Font("맑은 고딕 ", Font.BOLD, 13));
				UIManager.put("Label.font", new Font("Trajan Pro", Font.BOLD, 13));
				UIManager.put("activeCaption", new javax.swing.plaf.ColorUIResource(Color.WHITE));
				JDialog.setDefaultLookAndFeelDecorated(true);
				JOptionPane.showMessageDialog(this, "Please check your ID and/or Password!",
						"ID,Password 오류! " + "                                                ", -1);
				txtPw.requestFocus();
				return;
			}
		} catch (Exception e) {
			System.out.println("로그인 실패" + e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) { // 로그인
			logIn();
		} else if (e.getSource() == btnPw) { // 비밀번호 찾기
			new Comm_PwSendMail(this);
			setVisible(false);
		} else if (e.getSource() == btnJoin) { // 회원 가입
			new Customer_Join(this);
			setVisible(false);
		}

	}
}
