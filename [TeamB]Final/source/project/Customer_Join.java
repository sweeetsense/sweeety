package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Customer_Join extends JDialog implements ActionListener {
	/**
	 * Join : 고객 전용
	 */
	private static final long serialVersionUID = 1L;
	JDialog dialog;
	JTextField txtId, txtName, txtEmail, txtAnswer;
	JPasswordField txtPw, txtPwCon;
	JLabel lbl, lbl2, lblPwCon;
	JButton btnSave, btnCan, btnJoong;
	String sql;
	JComboBox<String> cbYear, cbMonth, cbDay, cbPurpose, cbArea, cbQuestion;
	String pw, pwCon;
	boolean Joong, Pass;

	private Properties prpt = new Properties();
	private Connection conn;
	private PreparedStatement pstmt, pstmt1;
	private ResultSet rs;

	public Customer_Join(JDialog dialog) { // 회원 가입
		this.dialog = dialog;
		Joong = false;
		Pass = false;		
		design();
		setTitle("Client SignUp");
		setSize(335, 700);
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void design() {
		JPanel panel = new JPanel(); // 디자인
		add(panel);
		panel.setBackground(new Color(64, 64, 64));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		setContentPane(panel);

		// 아이디 행
		lbl = new JLabel("ID");
		lbl.setBounds(180, 25, 90, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl.setForeground(Color.GRAY);
		lbl.requestFocus();
		panel.add(lbl);

		txtId = new JTextField();
		txtId.setBounds(25, 25, 185, 30);
		txtId.setFont(new Font("맑은 고딕", Font.BOLD, 18));		
		panel.add(txtId);

		btnJoong = new JButton("AVAILABILITY");
		btnJoong.setBounds(210, 25, 100, 29);
		btnJoong.setBackground(Color.BLACK);
		btnJoong.setForeground(Color.WHITE);
		btnJoong.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		btnJoong.requestFocus();
		panel.add(btnJoong);

		// 예제
		lbl = new JLabel("예) 시작은 영문으로만, '_'를 제외한 특수문자 안되며");
		lbl.setBounds(25, 50, 350, 30);
		lbl.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lbl.setForeground(Color.WHITE);
		panel.add(lbl);
		lbl = new JLabel("영문, 숫자, '_'으로만 이루어진 5 ~ 12자 이하");
		lbl.setBounds(25, 65, 350, 30);
		lbl.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lbl.setForeground(Color.WHITE);
		panel.add(lbl);

		// 패스워드 행
		lbl = new JLabel("PASSWORD");
		lbl.setBounds(200, 75, 150, 60);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl.setForeground(Color.GRAY);
		lbl.requestFocus();
		panel.add(lbl);

		txtPw = new JPasswordField();
		txtPw.setBounds(25, 90, 285, 30);
		txtPw.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		// txtPw.setBackground(new Color(255, 0, 0, 0));
		panel.add(txtPw);

		// 패스워드 점검행
		lbl2 = new JLabel("PW CHECK");
		lbl2.setBounds(200, 105, 150, 60);
		lbl2.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl2.setForeground(Color.GRAY);
		panel.add(lbl2);

		lblPwCon = new JLabel(" ");
		lblPwCon.setBounds(190, 105, 150, 60);
		panel.add(lblPwCon);
		txtPwCon = new JPasswordField();
		txtPwCon.setBounds(25, 120, 285, 30);
		txtPwCon.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(txtPwCon);

		// 비번검사
		txtPwCon.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pw = new String(txtPw.getPassword());
				pwCon = new String(txtPwCon.getPassword());
				if (pw.equals("")) {
					lbl2.setVisible(false);
					lblPwCon.setText("비밀번호를 입력");
					lblPwCon.setFont(new Font("맑은 고딕", Font.BOLD, 15));
					lblPwCon.setForeground(Color.RED);
					Pass = false;
				} else {
					if (pw.equals(pwCon)) {
						lbl.setVisible(false);
						lbl2.setVisible(false);
						lblPwCon.setText("비밀번호 일치함");
						lblPwCon.setFont(new Font("맑은 고딕", Font.BOLD, 15));
						lblPwCon.setForeground(Color.BLUE);
						Pass = true;
						if (Joong && Pass) {
							btnSave.setEnabled(true);
						}
					} else {
						lbl.setVisible(false);
						lbl2.setVisible(false);
						lblPwCon.setText("비밀번호 불일치");
						lblPwCon.setFont(new Font("맑은 고딕", Font.BOLD, 15));
						lblPwCon.setForeground(Color.RED);
						Pass = false;
					}
				}
			}
		});

		// 이름 행
		lbl = new JLabel("NAME");
		lbl.setBounds(245, 180, 90, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl.setForeground(Color.GRAY);
		panel.add(lbl);

		txtName = new JTextField();
		txtName.setBounds(25, 180, 285, 30);
		txtName.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panel.add(txtName);

		// 생년월일
		lbl = new JLabel("<html><body>BIRTH<br>DATE</body></html>");
		lbl.setBounds(25 , 227, 120, 38);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lbl.setForeground(Color.WHITE);
		panel.add(lbl);

		// 콤보박스-생년월일-년
		cbYear = new JComboBox<String>();
		cbYear.addItem("YEAR");
		cbYear.setBounds(100, 230, 80, 30);
		cbYear.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(cbYear);
		for (int i = Etc_Commons.YEAR - 1; i > 1900; i--) {
			cbYear.addItem(i + "");
		}

		// 콤보박스-생년월일-월
		cbMonth = new JComboBox<String>();
		cbMonth.addItem("MO");
		cbMonth.setBounds(180, 230, 65, 30);
		cbMonth.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(cbMonth);
		for (int i = 1; i <= 12; i++) {
			cbMonth.addItem(i + "");
		}

		// 콤보박스-생년월일-일
		cbDay = new JComboBox<String>();
		cbDay.addItem("DAY");
		cbDay.setBounds(245, 230, 65, 30);
		cbDay.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(cbDay);
		for (int i = 1; i <= 31; i++) {
			cbDay.addItem(i + "");
		}
 
		// 콤보박스-지역
		cbArea = new JComboBox<>();
		cbArea.addItem("--Select Residential District--");
		ComA();
		cbArea.setBounds(25, 280, 285, 30);
		cbArea.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(cbArea);

		// E-Mail
		lbl = new JLabel("E-Mail");
		lbl.setBounds(240, 330, 350, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl.setForeground(Color.GRAY);
		panel.add(lbl);

		txtEmail = new JTextField();
		txtEmail.setBounds(25, 330, 285, 30);
		txtEmail.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panel.add(txtEmail);

		// 비밀번호 찾기
		lbl = new JLabel("▼ 비밀번호를 잊어버렸을 때에 필요한 정보");
		lbl.setBounds(30, 380, 285, 30);
		lbl.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lbl.setForeground(Color.LIGHT_GRAY);
		panel.add(lbl);

		// 질문 콤보박스
		cbQuestion = new JComboBox<>();
		cbQuestion.addItem("--Choose Security Question--");
		ComQ();
		cbQuestion.setBounds(25, 410, 285, 30);
		cbQuestion.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(cbQuestion);

		// 비밀번호 찾기 답변
		lbl = new JLabel("Answer");
		lbl.setBounds(230, 437, 110, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl.setForeground(Color.GRAY);
		panel.add(lbl);

		txtAnswer = new JTextField();
		txtAnswer.setBounds(25, 440, 285, 30);
		txtAnswer.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panel.add(txtAnswer);

		// 콤보박스-목적
		cbPurpose = new JComboBox<>();
		cbPurpose.addItem("-Purpose of Your Investment-");
		ComPurp();
		cbPurpose.setBounds(25, 500, 285, 30); 
		cbPurpose.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(cbPurpose);

		// 마지막 버튼
		btnSave = new JButton("SAVE");
		btnSave.setBounds(25, 580, 142, 40);
		btnSave.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnSave.setForeground(Color.WHITE);
		btnSave.setBackground(Color.BLACK);
		panel.add(btnSave);
		btnSave.setEnabled(false);

		btnCan = new JButton("CANCEL");
		btnCan.setBounds(167, 580, 142, 40);
		btnCan.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnCan.setForeground(Color.WHITE);
		btnCan.setBackground(Color.BLACK);
		panel.add(btnCan);

		listener();

	}

	private void accDb() {
		try {
			prpt.load(new FileInputStream("C:/work/project.properties"));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));
		} catch (Exception e) {
			System.out.println("accDb 연결 실패:" + e);
		}
	}

	private void ComA() {
		accDb();
		try {
			pstmt = conn.prepareStatement("select * from b_varea");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cbArea.addItem(rs.getString("a_name"));
			}
		} catch (Exception e) {
			System.out.println("ComA err :" + e);
		}
	}

	private void ComPurp() {
		accDb();
		try {

			pstmt = conn.prepareStatement("select * from b_vpurpose");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cbPurpose.addItem(rs.getString("p_name"));
			}
		} catch (Exception e) {
			System.out.println("Comb err :" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				System.out.println("Comb Finally err :" + e2);
			}
		}
	}

	private void ComQ() {
		accDb();
		try {
			pstmt = conn.prepareStatement("select * from b_vquestion");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cbQuestion.addItem(rs.getString("q_text"));
			}
		} catch (Exception e) {
			System.out.println("ComQ err :" + e);
		}
	}

	private void repeated() {
		try {
			if (txtId.getText().trim().equals("")) {
				txtId.requestFocus();
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
				btnSave.setEnabled(false);
				return;
			} else {
				boolean regex = Pattern.matches(Etc_Commons.PATTERN_ID, txtId.getText()); // 아이디
				// 형식
				if (regex == false) {
					JOptionPane.showMessageDialog(this, "ID형식이 맞지않습니다.", "ID형식 오류", JOptionPane.WARNING_MESSAGE);
				} else {
					accDb();
					pstmt = conn.prepareStatement("select * from b_vcustomer where c_id=?");
					pstmt.setString(1, txtId.getText());
					rs = pstmt.executeQuery();

					if (rs.next()) {
						JOptionPane.showMessageDialog(this, "이미 사용중인 아이디입니다.");
						txtId.requestFocus();
						btnSave.setEnabled(false);
					} else {
						JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.");
						txtPw.requestFocus();
						Joong = true;
						if (Joong && Pass) {
							btnSave.setEnabled(true);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("repeated arr:" + e);
		}

	}

	private void Save() {
		int newNumber = 0;
		pwCon = new String(txtPwCon.getPassword());
		if (pwCon.equals("")) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하셔야 합니다.");
			lblPwCon.setText("비번을 입력하세요!");
			lblPwCon.setForeground(Color.RED);
			txtPwCon.requestFocus();
			return;
		} else {
			if (pw.equals(pwCon)) {
				try {
					sql = "select c_code from b_vcustomer order by c_code asc";
					pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					pstmt.executeQuery();
					rs = pstmt.executeQuery();
					rs.last();
					newNumber = rs.getRow() + 1;

				} catch (Exception e1) {
					System.out.println("새번호지정 오류" + e1);
				}
				try {

					String passwd = new String(txtPw.getPassword());

					String y = (String) cbYear.getSelectedItem();
					String m = (String) cbMonth.getSelectedItem();
					String d = (String) cbDay.getSelectedItem();

					String birth = y + "-" + m + "-" + d;
					pstmt1 = conn.prepareStatement("insert into b_vcustomer values (?,?,?,?,?,?,?,?,?,?,?)");
					pstmt1.setInt(1, newNumber);
					pstmt1.setString(2, null);
					pstmt1.setString(3, txtId.getText());
					pstmt1.setString(4, passwd);
					pstmt1.setString(5, txtName.getText());
					pstmt1.setString(6, birth);
					pstmt1.setString(7, txtEmail.getText());
					pstmt1.setInt(8, (cbQuestion.getSelectedIndex()));
					pstmt1.setString(9, txtAnswer.getText());
					pstmt1.setInt(10, cbPurpose.getSelectedIndex());
					pstmt1.setInt(11, (cbArea.getSelectedIndex()) * 10);
					pstmt1.executeQuery();

				} catch (Exception e) {
					System.out.println("회원가입 오류" + e);
				}
			} else if (pw.equals(null)) {
				JOptionPane.showMessageDialog(this, "패스워드를 입력하세요!");
			} else {
				JOptionPane.showMessageDialog(this, "패스워드가 일치하지 않습니다!");
				txtPwCon.requestFocus();
			}
		}
	}



	private void listener() {
		btnJoong.addActionListener(this);
		btnSave.addActionListener(this);
		btnCan.addActionListener(this);
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
				cbDay.addItem("일");
				for (int i = 1; i <= iDay; i++) {
					cbDay.addItem(i + "");
				}
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCan) {
			dialog.setVisible(true);
			dispose();
		} else if (e.getSource() == btnJoong) {
			repeated();
		} else if (e.getSource() == btnSave) {
			if (txtName.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "이름을 입력해주세요");
				txtName.requestFocus();
				return;
			} else if (cbYear.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "태어난 해를 선택해 주세요");
				cbYear.requestFocus();
				return;
			} else if (cbMonth.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "생일 월을 선택해 주세요");
				cbMonth.requestFocus();
				return;
			} else if (cbDay.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "생일 일을 선택해 주세요");
				cbDay.requestFocus();
				return;
			} else if (cbArea.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "거주지역을 선택해주세요");
				cbArea.requestFocus();
				return;
			} else if (txtEmail.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Email 주소를 입력해주세요");
				txtEmail.requestFocus();
				return;
			} else if (cbQuestion.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "비밀번호 찾기 질문을 선택해주세요");
				cbQuestion.requestFocus();
				return;
			} else if (txtAnswer.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "비밀번호 찾기 질문을 입력해주세요");
				txtAnswer.requestFocus();
				return;
			} else if (cbPurpose.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "투자목적을 선택해주세요");
				cbPurpose.requestFocus();
				return;
			}
			boolean b = Pattern.matches(Etc_Commons.PATTERN_EMAIL, txtEmail.getText());
			String passwd = new String(txtPw.getPassword());
			boolean ppww = Pattern.matches(Etc_Commons.PATTERN_PW, passwd);
			boolean nam = Pattern.matches(Etc_Commons.PATTERN_NAME, txtName.getText());
			if (ppww == false) {
				JOptionPane.showMessageDialog(this, "패스워드는 숫자,문자로만 이루어진\n 6~12자리까지만 입력 가능합니다. ", "비밀번호 형식 오류",
						JOptionPane.WARNING_MESSAGE);
				txtPw.setText(null);
				txtPwCon.setText(null);
				txtPw.requestFocus();
				return;
			} else if (nam == false) {
				JOptionPane.showMessageDialog(this, "이름은 한글만 입력 가능합니다.", "이름 형식 오류", JOptionPane.WARNING_MESSAGE);
				txtName.requestFocus();
				return;
			} else if (b == false) {
				JOptionPane.showMessageDialog(this, "E-mail형식이 맞지않습니다.", "E-mail 주소 오류", JOptionPane.WARNING_MESSAGE);
				txtEmail.requestFocus();
				return;
			}
			Save();
			JOptionPane.showMessageDialog(this, "축하합니다. 회원가입이 완료되었습니다.");
			dialog.setVisible(true);
			dispose();
		}
	}
}
