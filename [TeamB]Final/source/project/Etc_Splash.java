package project;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Etc_Splash extends JFrame {
	/**
	 * 시작화면
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon im;

	public Etc_Splash() {
		im = new ImageIcon(Etc_Commons.SPLASH_IMG_PATH);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int X = (int) ((dim.getWidth() - this.getWidth()) / 2);
		final int Y = (int) ((dim.getHeight() - this.getHeight()) / 2);
		this.setIconImage(im.getImage());
		JPanel pn1 = new JPanel();
		JLabel lbl = new JLabel(im);
		setBounds(500, 300, X, Y);
		setLayout(null);
		lbl.setBounds(0, 0, X, Y);
		pn1.add(lbl);
		add("Center", lbl);
		setUndecorated(true);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Etc_Splash();
	}
}