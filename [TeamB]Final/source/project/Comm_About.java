package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Comm_About extends JPanel {
	/**
	 * About : 프로그램 정보
	 */
	private static final long serialVersionUID = 1L;
	public Comm_About() {
		setLayout(null);
		
		JTextArea area2 = new JTextArea();
		area2.setText("Version: AMS (1.0.1)");
		area2.setBounds(335, 570, 300, 24);
		area2.setFont(new Font("Trajan Pro", Font.PLAIN, 20));
		area2.setForeground(Color.GRAY);
		area2.setEditable(false);
		area2.setOpaque(false);
		add(area2);
		
		JTextArea area3 = new JTextArea();
		area3.setText("(c) Copyright Asset Management System");
		area3.setBounds(245, 590, 500, 24);
		area3.setFont(new Font("Trajan Pro", Font.PLAIN, 20));
		area3.setForeground(Color.GRAY);
		area3.setEditable(false);
		area3.setOpaque(false);
		add(area3);

		JTextArea area = new JTextArea();
		area.setText(Etc_Commons.ABOUT_TEXT);
		area.setFont(new Font("Cabin-Regular", Font.PLAIN, 30));
		area.setForeground(Color.WHITE);
		area.setBounds(0, 300, 794, 260);
		area.setEditable(false);
		area.setOpaque(false);
		add(area);
		
		JButton btn_close = new JButton("Close");
		btn_close.setBounds(380, 650, 90, 35);
		btn_close.setFont(new Font("Trajan Pro", Font.PLAIN, 14));
		btn_close.setBackground(Color.BLACK);
		btn_close.setForeground(Color.WHITE);
		add(btn_close);
		btn_close.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {	
				try {
					Customer_Main.About.setEnabled(true);
					Customer_Main.childAbout.dispose();					
				} catch (Exception e2) {
					Manager_Main.About.setEnabled(true);
					Manager_Main.childAbout.dispose();
				}
			}
		});
		
		JLabel label = new JLabel(new ImageIcon(Etc_Commons.ABOUT_IMG_PATH));
		label.setBounds(0, 0, 818, 727);
		add(label);
		label.setHorizontalAlignment(JLabel.CENTER);
		
	}

//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.setSize(818, 755);
//		frame.setVisible(true);
//	}
}
