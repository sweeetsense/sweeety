package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Comm_MainLogIn extends JDialog implements ActionListener {
	/**
	 * Mian Login 창
	 */
	private static final long serialVersionUID = 1L;
	JButton btnGogek, btnManager;
	JLabel lbl;
	JPanel panel;
	ImageIcon icon;
	
	public Comm_MainLogIn() {

		design();
		setTitle("Login");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(600,400);
		final int X = (int) ((dim.getWidth() - this.getWidth()) / 2);
		final int Y = (int) ((dim.getHeight() - this.getHeight()) / 2);
		setLocation(X, Y);
		setVisible(true);
		setResizable(false);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	}

	private void listener() {
		btnGogek.addActionListener(this);
		btnManager.addActionListener(this);
	}

	private void design() {
		 icon = new ImageIcon(Etc_Commons.LOGIN_IMG_PATH);
		panel = new JPanel() {
            /**
			 * 배경 이미지 담기 위한 Panel
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        
		add(panel);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		setContentPane(panel);

		btnGogek = new JButton();
		btnManager = new JButton();
		btnGogek.setBackground(Color.white);
		btnGogek.setOpaque(false);
		btnGogek.setContentAreaFilled(false);
		btnManager.setBackground(Color.white);
		btnManager.setOpaque(false);
		btnManager.setContentAreaFilled(false);
		
		panel.add(btnGogek);
		panel.add(btnManager);
		JButton btnClose = new JButton();

		setSize(350, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int X = (int) ((dim.getWidth() - this.getWidth()) / 2);
		final int Y = (int) ((dim.getHeight() - this.getHeight()) / 2);
		setLocation(X, Y);

		btnGogek.setBounds(0, 200, 298, 130);
		btnGogek.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		btnManager.setBounds(302, 200, 300, 130);
		btnManager.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		btnClose.setBounds(150, 200, 100, 30);

		listener();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnGogek) {
			 new Customer_LogIn(this);
		} else if (e.getSource() == btnManager) {
			 new Manager_LogIn(this);
		}
		setVisible(false);
	}

	public static void main(String[] args) {
	      try {
	         Etc_Splash splash = new Etc_Splash();
	         Thread.sleep(3000);
	         splash.dispose();
	      } catch (Exception e) {
	         // TODO: handle exception
	      }
	      
	      new Comm_MainLogIn();
	   }
}
