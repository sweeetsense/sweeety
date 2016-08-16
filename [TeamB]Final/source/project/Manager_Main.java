package project;

import java.awt.Color;
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
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

public class Manager_Main extends JFrame implements ActionListener {
	/**
	 * 자산관리사 전용 : 메인화면
	 */
	private static final long serialVersionUID = 1L;

	public static JMenuItem Customers, AssetSendMail, ClientStockInfo, Issue, Market; // 첫번째 메뉴
	public static JMenuItem MyUpdate, LogOut, Exit; // 두번째 메뉴
	public static JMenuItem About; // 세번째 메뉴

	public static JInternalFrame childCustomers, childAssetSendMail, childClientStockInfo;
	public static JInternalFrame childMarket, childIssue, childMyUpdate, childAbout;
	// childLogout,childGraph
	public JDesktopPane desktopPane = new JDesktopPane(); // frame 생성

	private static int str = 0; // 관리자번호

	private BufferedImage img;

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	final int X = (int) ((dim.getWidth() - this.getWidth()) / 2);
	final int Y = (int) ((dim.getHeight() - this.getHeight()) / 2);

	public Manager_Main(int str) {
		Manager_Main.str = str;
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

		// 첫번째 메뉴
		JMenu menu = new JMenu(Etc_Commons.Menu1);
		menu.setFont(new Font("Trajan Pro", Font.BOLD, 25));
		menu.setForeground(Color.WHITE);
		menu.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		Customers = new JMenuItem(Etc_Commons.MenuItem_MYCUSTOMER);
		Customers.setFont(new Font("Serif", Font.BOLD, 15));
		Customers.setBackground(Color.LIGHT_GRAY);
		Customers.setForeground(Color.WHITE);
		AssetSendMail = new JMenuItem("Send Portfolio...");
		AssetSendMail.setFont(new Font("Serif", Font.BOLD, 15));
		AssetSendMail.setBackground(Color.WHITE);
		AssetSendMail.setForeground(Color.GRAY);
		ClientStockInfo = new JMenuItem("Client Stock Information...");
		ClientStockInfo.setFont(new Font("Serif", Font.BOLD, 15));
		ClientStockInfo.setBackground(Color.LIGHT_GRAY);
		ClientStockInfo.setForeground(Color.WHITE);
		// --------------------------------------------------------
		Issue = new JMenuItem("Issue Graph...");
		Issue.setFont(new Font("Serif", Font.BOLD, 15));
		Issue.setBackground(Color.WHITE);
		Issue.setForeground(Color.GRAY);
		Market = new JMenuItem("Market Position...");
		Market.setFont(new Font("Serif", Font.BOLD, 15));
		Market.setBackground(Color.LIGHT_GRAY);
		Market.setForeground(Color.WHITE);

		// 두번째 메뉴
		JMenu menu2 = new JMenu(Etc_Commons.Menu2);
		menu2.setFont(new Font("Trajan Pro", Font.BOLD, 25));
		menu2.setForeground(Color.WHITE);
		menu2.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		MyUpdate = new JMenuItem(Etc_Commons.MenuItem_MYINFO);
		MyUpdate.setFont(new Font("Serif", Font.BOLD, 15));
		MyUpdate.setBackground(Color.LIGHT_GRAY);
		MyUpdate.setForeground(Color.WHITE);
		// --------------------------------------------------------
		LogOut = new JMenuItem("Logout");
		LogOut.setFont(new Font("Serif", Font.BOLD, 15));
		LogOut.setBackground(Color.WHITE);
		LogOut.setForeground(Color.GRAY);

		Exit = new JMenuItem("Exit");
		Exit.setFont(new Font("Serif", Font.BOLD, 15));
		Exit.setBackground(Color.LIGHT_GRAY);
		Exit.setForeground(Color.WHITE);

		// 세번째 메뉴
		JMenu menu3 = new JMenu(Etc_Commons.Menu3);
		About = new JMenuItem(Etc_Commons.MenuItem_ABOUT);
		menu3.setFont(new Font("Trajan Pro", Font.BOLD, 25));
		menu3.setForeground(Color.WHITE);
		menu3.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		About = new JMenuItem("About...");
		About.setFont(new Font("Serif", Font.BOLD, 15));
		About.setBackground(Color.LIGHT_GRAY);
		About.setForeground(Color.WHITE);

		// 첫번째 메뉴
		menu.add(Customers);
		menu.add(AssetSendMail);
		menu.add(ClientStockInfo);
		menu.addSeparator();
		menu.add(Issue);
		menu.add(Market);
		// 두번째 메뉴
		menu2.add(MyUpdate);
		menu2.addSeparator();
		menu2.add(LogOut);
		menu2.add(Exit);
		// 세번째 메뉴
		menu3.add(About);

		// 첫번째 메뉴
		Customers.addActionListener(this);
		AssetSendMail.addActionListener(this);
		ClientStockInfo.addActionListener(this);
		// --------------------------------------------------------
		Issue.addActionListener(this);
		Market.addActionListener(this);
		// 두번째 메뉴
		MyUpdate.addActionListener(this);
		// --------------------------------------------------------
		LogOut.addActionListener(this);
		Exit.addActionListener(this);
		// 세번째 메뉴
		About.addActionListener(this);

		mbar.add(menu);
		mbar.add(menu2);
		mbar.add(menu3);
		setJMenuBar(mbar);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(0, 0);
		this.setSize(dimension.width, dimension.height);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/*
	 * ========================================================= childLogout,
	 * childGraph, childAssetStatus, childIssue, childMarket*, childMyUpdate*,
	 * childCounseling, childAbout*;
	 * 
	 * LogOut,CustomerView,Market,Issue, MyUpdate,Report,Counseling,About;
	 * =============================================
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// 첫번째 메뉴
		if (e.getSource().equals(Customers)) {
			createListen("나의 고객정보", 636, 600, "Customers");
			Manager_MyCustomer customer = new Manager_MyCustomer(str);
			childCustomers.getContentPane().add(customer);
			childCustomers.setLocation(310, 20);
			desktopPane.add(childCustomers);
			childCustomers.show();
			Manager_Main.Customers.setEnabled(false);
		} else if (e.getSource().equals(AssetSendMail)) {
			createListen("My Customer Asset Status(Mail)", 600, 300, "AssetSendMail");
			Manager_AssetsSendMail sendMail = new Manager_AssetsSendMail(str);
			childAssetSendMail.getContentPane().add(sendMail);
			childAssetSendMail.setLocation(90, 50);
			desktopPane.add(childAssetSendMail);
			childAssetSendMail.show();
			Manager_Main.AssetSendMail.setEnabled(false);
		} else if (e.getSource().equals(ClientStockInfo)) {
			createListen("My Customer Asset Information", 500, 520, "ClientStockInfo");
			Manager_viewClientInfo viewCustInfo = new Manager_viewClientInfo(str);
			childClientStockInfo.getContentPane().add(viewCustInfo);
			childClientStockInfo.setLocation(100, 50);
			desktopPane.add(childClientStockInfo);
			childClientStockInfo.show();
			Manager_Main.ClientStockInfo.setEnabled(false);
		} else if (e.getSource().equals(Issue)) {
			createListen("종목별 현황", 600, 550, "Issue");
			Comm_IssueGraph issue = new Comm_IssueGraph();
			childIssue.getContentPane().add(issue);
			childIssue.setLocation(110, 60);
			desktopPane.add(childIssue);
			childIssue.show();
			Issue.setEnabled(false);
		} else if (e.getSource().equals(Market)) {
			createListen("시장현황", 600, 550, "Market");
			Comm_MarketPositionGraph market = new Comm_MarketPositionGraph();
			childMarket.getContentPane().add(market);
			childMarket.setLocation(210, 110);
			desktopPane.add(childMarket);
			childMarket.show();
			Manager_Main.Market.setEnabled(false);
		}
		// 두번째 메뉴
		else if (e.getSource().equals(MyUpdate)) {
			createListen("내 정보수정", 340, 550, "Myupdate");
			Manager_Update Myupdate = new Manager_Update(str);
			childMyUpdate.getContentPane().add(Myupdate);
			childMyUpdate.setLocation(210, 110);
			desktopPane.add(childMyUpdate);
			childMyUpdate.show();
			MyUpdate.setEnabled(false);
		} else if (e.getSource().equals(LogOut)) {
			int re = JOptionPane.showConfirmDialog(this, "정말 로그아웃 하시겠습니까?", "선택", JOptionPane.YES_NO_OPTION);
			if (re == JOptionPane.NO_OPTION)
				return;
			dispose();
			new Comm_MainLogIn();
		} else if (e.getSource().equals(Exit)) {
			int re = JOptionPane.showConfirmDialog(this, "정말 종료할까요?", "선택", JOptionPane.YES_NO_OPTION);
			if (re == JOptionPane.NO_OPTION)
				return;
			System.exit(0);
		}
		// 세번째 메뉴
		else if (e.getSource().equals(About)) {
			createListen("About", 818, 755, "About");
			Comm_About about = new Comm_About();
			childAbout.getContentPane().add(about);
			childAbout.setLocation(200, 80);
			desktopPane.add(childAbout);
			childAbout.show();
			About.setEnabled(false);
		}
	}

	private void createListen(String title, int w, int h, String str) {
		if (str.equals("Customers")) {
			childCustomers = new JInternalFrame(title, false, false, false, false);
			childCustomers.setSize(w, h);
		} else if (str.equals("AssetSendMail")) {
			childAssetSendMail = new JInternalFrame(title, false, false, false, false);
			childAssetSendMail.setSize(w, h);
		} else if (str.equals("ClientStockInfo")) {
			childClientStockInfo = new JInternalFrame(title, false, false, false, false);
			childClientStockInfo.setSize(w, h);
		} else if (str.equals("Issue")) {
			childIssue = new JInternalFrame(title, false, false, false, true);
			childIssue.setSize(w, h);
		} else if (str.equals("Market")) {
			childMarket = new JInternalFrame(title, false, false, false, true);
			childMarket.setSize(w, h);
		} else if (str.equals("Myupdate")) {
			childMyUpdate = new JInternalFrame(title, false, false, false, true);
			childMyUpdate.setSize(w, h);
		} else if (str.equals("About")) {
			childAbout = new JInternalFrame(title, false, false, false, true);
			childAbout.setSize(w, h);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Manager_Main(str);
			}
		});
	}
}
