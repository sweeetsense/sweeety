package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Manager_Update extends JPanel implements ActionListener {
	/**
	 * 관리자 전용 : 회원정보 수정
	 */
	private static final long serialVersionUID = 1L;
	JTextField txtId, txtName, txtEmail, txtAsk, txtAnswer;
	JPasswordField txtPw, txtPwCon;
	JLabel lbl, lbl2, lblPwCon;
	JButton btnSave, btnCan;
	String sql;
	JComboBox<String> cbYear, cbMonth, cbDay, cbPurpose, cbArea, cbQuestion;
	String pw, pwCon;

	private Properties prpt = new Properties();
	private Connection conn;
	private PreparedStatement pstmt, pstmt1, pstmt2;
	private ResultSet rs, rs2;
	private int str;

	public Manager_Update(int str) {
		this.str = str;
		design();
		display();
		setVisible(true);
	}

	// 디자인=============================
	private void design() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(64, 64, 64));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);

		// 아이디 행
		lbl = new JLabel("ID");
		lbl.setBounds(25, 0, 185, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lbl.setForeground(Color.WHITE);
		lbl.requestFocus();
		panel.add(lbl);

		txtId = new JTextField();
		txtId.setBounds(25, 25, 185, 30);
		txtId.setFont(new Font("맑은 고딕", Font.BOLD, 18));		
		txtId.setEditable(false);
		panel.add(txtId);

		// 패스워드 행
		lbl = new JLabel("PASSWORD");
		lbl.setBounds(25, 65, 285, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lbl.setForeground(Color.WHITE);
		lbl.requestFocus();
		panel.add(lbl);

		txtPw = new JPasswordField();
		txtPw.setBounds(25, 90, 285, 30);
		txtPw.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(txtPw);

		// 패스워드 점검
		lblPwCon = new JLabel(" ");
		lblPwCon.setBounds(190, 105, 150, 60);
		panel.add(lblPwCon);
		txtPwCon = new JPasswordField();
		txtPwCon.setBounds(25, 120, 285, 30);
		txtPwCon.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(txtPwCon);
		
		txtPwCon.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pw = new String(txtPw.getPassword());
				pwCon = new String(txtPwCon.getPassword());
				if (pwCon.equals("")) {
					lblPwCon.setText("비번을 입력하세요!");
					lblPwCon.setForeground(Color.RED);
				} else {
					if (pw.equals(pwCon)) {
						lblPwCon.setText("비밀번호 일치");
						lblPwCon.setFont(new Font("맑은 고딕", Font.BOLD, 15));
						lblPwCon.setForeground(Color.BLUE);
						btnSave.setEnabled(true);
					} else {
						lblPwCon.setText("비밀번호 불일치");
						lblPwCon.setFont(new Font("맑은 고딕", Font.BOLD, 15));
						lblPwCon.setForeground(Color.RED);
					}

				}
			}
		});

		// 이름 행
		lbl = new JLabel("NAME");
		lbl.setBounds(25, 155, 285, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lbl.setForeground(Color.WHITE);
		panel.add(lbl);
		
		txtName = new JTextField();
		txtName.setBounds(25, 180, 285, 30);
		txtName.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		txtName.setEditable(false);
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

		// E-Mail
		lbl = new JLabel("E-Mail");
		lbl.setBounds(240, 280, 350, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl.setForeground(Color.GRAY);
		panel.add(lbl);

		txtEmail = new JTextField();
		txtEmail.setBounds(25, 280, 285, 30);
		txtEmail.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panel.add(txtEmail);

		// 비밀번호 찾기
		lbl = new JLabel("▼ 비밀번호를 잊어버렸을 때에 필요한 정보");
		lbl.setBounds(30, 320, 285, 30);
		lbl.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lbl.setForeground(Color.LIGHT_GRAY);
		panel.add(lbl);

		// 질문 콤보박스
		cbQuestion = new JComboBox<>();
		cbQuestion.addItem("--Choose Security Question--");
		ComQ();
		cbQuestion.setBounds(25, 350, 285, 30);
		cbQuestion.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(cbQuestion);

		// 비밀번호 찾기 답변
		lbl = new JLabel("Answer");
		lbl.setBounds(230, 377, 110, 30);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl.setForeground(Color.GRAY);
		panel.add(lbl);

		txtAnswer = new JTextField();
		txtAnswer.setBounds(25, 380, 285, 30);
		txtAnswer.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panel.add(txtAnswer);

		// 마지막 버튼
		btnSave = new JButton("SAVE");
		btnSave.setBounds(25, 440, 142, 40);
		btnSave.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnSave.setForeground(Color.WHITE);
		btnSave.setBackground(Color.BLACK);
		panel.add(btnSave);
		btnSave.setEnabled(false);

		btnCan = new JButton("CANCEL");
		btnCan.setBounds(167, 440, 142, 40);
		btnCan.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnCan.setForeground(Color.WHITE);
		btnCan.setBackground(Color.BLACK);
		panel.add(btnCan);

		listener();
	}

	private void accDb() {
		try {
			prpt.load(new FileInputStream(Etc_Commons.DB_PROPERTIES));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));
		} catch (Exception e) {
			System.out.println("accDB Err : " + e);
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
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				System.out.println("ComQ finally err" + e2);
			}
		}

	}

	private void Save() {
		accDb();
		if (pwCon.equals("")) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하셔야 합니다.");
			lblPwCon.setText("비번을 입력하세요!");
			lblPwCon.setForeground(Color.RED);
			txtPwCon.requestFocus();
			return;
		} else {
			if (pw.equals(pwCon)) {
				try {
					// 비밀번호 가져오기
					String passwd = new String(txtPw.getPassword());
					// 생년월일 가져와서 처리하기
					String y = (String) cbYear.getSelectedItem();
					String m = (String) cbMonth.getSelectedItem();
					String d = (String) cbDay.getSelectedItem();
					String birth = y + "-" + m + "-" + d;

					// DB update구문
					pstmt1 = conn.prepareStatement(
							"update b_vmanager set m_pw = ?, m_birth =?, m_email=? , m_queno=?, m_answer =?  where m_code=?");

					pstmt1.setString(1, passwd);
					pstmt1.setString(2, birth);
					pstmt1.setString(3, txtEmail.getText());
					pstmt1.setInt(4, (cbQuestion.getSelectedIndex() + 1));
					pstmt1.setString(5, txtAnswer.getText());
					pstmt1.setInt(6, str);

					pstmt1.executeUpdate();

					JOptionPane.showMessageDialog(this, "정보가 수정되었습니다.");
					Manager_Main.childMyUpdate.dispose();
					Manager_Main.MyUpdate.setEnabled(true);	
				} catch (Exception e) {
					System.out.println("정보수정 오류" + e);
				} finally {
					try {
						if (rs != null)
							rs.close();
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (Exception e2) {
						System.out.println("정보수정 finally err" + e2);
					}
				}
			} else if (pw.equals(null)) {
				JOptionPane.showMessageDialog(this, "패스워드를 입력하세요!");
			} else {
				JOptionPane.showMessageDialog(this, "패스워드가 일치하지 않습니다!");
				txtPwCon.requestFocus();
			}
		}
	}

	private void display() {

		try {
			accDb();
			sql = "select * from b_vmanager join b_vquestion on m_queno = q_no where m_code =?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, str);
			rs2 = pstmt2.executeQuery();
			rs2.next();
			// 아이디 보이기
			txtId.setText(rs2.getString("m_id"));
			// 이름 보이기
			txtName.setText(rs2.getString("m_name"));
			// 생일년도 선택
			String Year = (rs2.getString("m_birth").substring(0, 4));
			cbYear.setSelectedItem(Year);
			// 생일 월 선택
			int Month = Integer.parseInt(rs2.getString("m_birth").substring(5, 7));
			String Mo = Integer.toString(Month);
			cbMonth.setSelectedItem(Mo);
			// 생일 일 선택
			int Day = Integer.parseInt(rs2.getString("m_birth").substring(8, 10));
			String DDD = Integer.toString(Day);
			cbDay.setSelectedItem(DDD);
			// 이메일 주소
			txtEmail.setText(rs2.getString("m_email"));
			// 비밀번호 찾기용 질문
			int Qno = Integer.parseInt(rs2.getString("m_queno"));
			Qno = Qno - 1;
			cbQuestion.setSelectedIndex(Qno);
			// 비밀번호 찾기용 답변
			txtAnswer.setText(rs2.getString("m_answer"));

		} catch (SQLException e) {
			System.out.println("display : " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				System.out.println("정보보기 finally err" + e2);
			}
		}
	}

	private void listener() {

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
				for (int i = 1; i <= iDay; i++) {
					cbDay.addItem(i + "");
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave) {
			Save();
		} else if (e.getSource() == btnCan) {
			Manager_Main.childMyUpdate.dispose();
			Manager_Main.MyUpdate.setEnabled(true);			
		}
	}
//
//	public static void main(String[] args) {
//		Manager_Update UpManager = new Manager_Update(1);
//
//		JFrame frame = new JFrame("내 정보수정");
//		frame.getContentPane().add(UpManager);
//		frame.setBounds(440, 110, 340, 550);
//		frame.setVisible(true);
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}