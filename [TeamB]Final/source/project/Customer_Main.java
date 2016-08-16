package project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

public class Customer_Main extends JFrame implements ActionListener {
	/**
	 * 고객전용 : 메인화면
	 */
	private static final long serialVersionUID = 1L;
	// private JMenu menu;
	public static JMenuItem AssetStatus, Issue, Market, LogOut, Exit, MyUpdate, Counseling, About;
	public static JInternalFrame childAssetStatus, childIssue, childMarket, childMyUpdate, childCounseling, childAbout;
	public JDesktopPane desktopPane;// = new JDesktopPane(); // frame 생성
	private BufferedImage img;

	public boolean logdial = false;
	private static int str = 0;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	Container cp = getContentPane();
	JPanel jpn = new JPanel();

	public Customer_Main(int str) {

		Customer_Main.str = str;
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		design();

		this.setLocation(0, 0);
		this.setSize(dim.width, dim.height-50);

		try {
			img = ImageIO.read(new File(Etc_Commons.MAIN_IMG_PATH));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// A specialized layered pane to be used with JInternalFrames
		desktopPane = new JDesktopPane() {
			/**
			 * background 이미지 삽입
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				grphcs.drawImage(img, 0, 0, (int) dim.getWidth(), (int) dim.getHeight(), null);
			}
		};
		// title bar icon 설정
		ImageIcon image = new ImageIcon(Etc_Commons.ICON_IMG_PATH);
		setIconImage(image.getImage());

		this.setContentPane(desktopPane);
		this.setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(desktopPane, "프로그램이 종료됩니다.");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

	private void design() {

		JMenuBar mbar = new JMenuBar(); // 메뉴바
		mbar.setBackground(Color.GRAY);
		mbar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		//첫번째 메뉴
		JMenu menu = new JMenu(Etc_Commons.Menu1);
		menu.setFont(new Font("Trajan Pro", Font.BOLD, 25));
		menu.setForeground(Color.WHITE);
		menu.setBorder(BorderFactory.createLineBorder(Color.white, 3));
		
		AssetStatus = new JMenuItem(Etc_Commons.MenuItem_ASSETINFO);
		AssetStatus.setFont(new Font("Trajan Pro", Font.PLAIN, 15));
		AssetStatus.setBackground(Color.LIGHT_GRAY);
		AssetStatus.setForeground(Color.WHITE);
		Issue = new JMenuItem("Issue Graph..");
		Issue.setFont(new Font("Trajan Pro", Font.PLAIN, 15));
		Issue.setBackground(Color.WHITE);
		Issue.setForeground(Color.GRAY);
		Market = new JMenuItem("Market Position..");
		Market.setFont(new Font("Trajan Pro", Font.PLAIN, 15));
		Market.setBackground(Color.LIGHT_GRAY);
		Market.setForeground(Color.WHITE);

		//두번째 메뉴
		JMenu menu2 = new JMenu(Etc_Commons.Menu2);
		menu2.setFont(new Font("Trajan Pro", Font.PLAIN, 25));
		menu2.setForeground(Color.WHITE);
		menu2.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		MyUpdate = new JMenuItem(Etc_Commons.MenuItem_MYINFO);
		MyUpdate.setFont(new Font("Trajan Pro", Font.PLAIN, 15));
		MyUpdate.setBackground(Color.LIGHT_GRAY);
		MyUpdate.setForeground(Color.WHITE);
		LogOut = new JMenuItem("Logout");
		LogOut.setFont(new Font("Trajan Pro", Font.PLAIN, 15));
		LogOut.setBackground(Color.WHITE);
		LogOut.setForeground(Color.GRAY);
		Exit = new JMenuItem("Exit");
		Exit.setFont(new Font("Trajan Pro", Font.PLAIN, 15));
		Exit.setBackground(Color.LIGHT_GRAY);
		Exit.setForeground(Color.WHITE);

		//세번째 메뉴
		JMenu menu3 = new JMenu(Etc_Commons.Menu3);
		menu3.setFont(new Font("Trajan Pro", Font.BOLD, 25));
		menu3.setForeground(Color.WHITE);
		menu3.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		About = new JMenuItem(Etc_Commons.MenuItem_ABOUT);
		About.setFont(new Font("Trajan Pro", Font.PLAIN, 15));
		About.setBackground(Color.LIGHT_GRAY);
		About.setForeground(Color.WHITE);

		//첫번째 메뉴
		menu.add(AssetStatus);
		menu.add(Issue);
		menu.add(Market);
		//두번째 메뉴
		menu2.add(MyUpdate);
		menu2.addSeparator();
		menu2.add(LogOut);
		menu2.add(Exit);
		//세번째 메뉴
		menu3.add(About);

		menu.addActionListener(this);
		AssetStatus.addActionListener(this);
		Issue.addActionListener(this);
		Market.addActionListener(this);
		LogOut.addActionListener(this);
		Exit.addActionListener(this);
		MyUpdate.addActionListener(this);
		About.addActionListener(this);

		mbar.add(menu);
		mbar.add(menu2);
		mbar.add(menu3);
		setJMenuBar(mbar); // frame에 메뉴바 장착
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 메뉴1(AssetManagement)
		if (e.getSource().equals(AssetStatus)) {
			createListion("My Asset Status", "AssetStatus", 600, 550);
			Customer_InDel inDel = new Customer_InDel(str);
			childAssetStatus.getContentPane().add(inDel);
			childAssetStatus.setLocation(10, 10);
			desktopPane.add(childAssetStatus);
			childAssetStatus.show();
			Customer_Main.AssetStatus.setEnabled(false);
		} else if (e.getSource().equals(Issue)) {
			createListion("Issue Graph", "Issue", 600, 550);
			Comm_IssueGraph issue = new Comm_IssueGraph();
			childIssue.getContentPane().add(issue);
			childIssue.setLocation(110, 60);
			desktopPane.add(childIssue);
			childIssue.show();
			Customer_Main.Issue.setEnabled(false);
		} else if (e.getSource().equals(Market)) {
			createListion("Market Graph", "Market", 600, 550);
			Comm_MarketPositionGraph market = new Comm_MarketPositionGraph();
			childMarket.getContentPane().add(market);
			childMarket.setLocation(210, 110);
			desktopPane.add(childMarket);
			childMarket.show();
			Customer_Main.Market.setEnabled(false);
		} else if (e.getSource().equals(LogOut)) {
			int lot = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "logout",
					JOptionPane.YES_NO_OPTION);
			if (lot == JOptionPane.YES_OPTION) {
				this.dispose();
				new Comm_MainLogIn();
			} else {
				return;
			}
		} else if (e.getSource().equals(Exit)) {
			int re = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "exit", JOptionPane.YES_NO_OPTION);
			if (re == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				return;
			}
		}
		// 메뉴2(MemberManagement)
		else if (e.getSource().equals(MyUpdate)) {
			createListion("MyUpdate", "MyUpdate", 340, 700);
			Customer_Update updateC = new Customer_Update(str);
			childMyUpdate.getContentPane().add(updateC);
			childMyUpdate.setLocation(310, 20);
			desktopPane.add(childMyUpdate);
			childMyUpdate.show();
			Customer_Main.MyUpdate.setEnabled(false);
		}
		// 메뉴3(Help)
		else if (e.getSource().equals(About)) {
			createListion("About", "About", 818, 755);
			Comm_About about = new Comm_About();
			childAbout.getContentPane().add(about);
			childAbout.setLocation(200, 80);
			desktopPane.add(childAbout);
			childAbout.show();
			Customer_Main.About.setEnabled(false);
		}
	}

	private void createListion(String title, String str, int w, int h) {
		if (str.equals("AssetStatus")) {
			childAssetStatus = new JInternalFrame(title, false, false, false, false);
			childAssetStatus.setSize(w, h);
		} else if (str.equals("Issue")) {
			childIssue = new JInternalFrame(title, false, false, false, false);
			childIssue.setSize(w, h);
		} else if (str.equals("Market")) {
			childMarket = new JInternalFrame(title, false, false, false, false);
			childMarket.setSize(w, h);
		} else if (str.equals("MyUpdate")) {
			childMyUpdate = new JInternalFrame(title, false, false, false, false);
			childMyUpdate.setSize(w, h);
		} else if (str.equals("About")) {
			childAbout = new JInternalFrame(title, false, false, false, false);
			childAbout.setSize(w, h);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Customer_Main(str);
			}
		});
	}
}
