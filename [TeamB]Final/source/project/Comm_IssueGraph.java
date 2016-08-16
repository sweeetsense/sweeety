package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Comm_IssueGraph extends JPanel implements ActionListener {
	/**
	 * 주식종목 그래프
	 */
	private static final long serialVersionUID = 1L;
	private Properties prpt = new Properties();
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private JPanel pnl_chart;
	private JComboBox<String> tf_issue = new JComboBox<>();
	private JButton btn_search, btn_close;
	private JLabel lbl_issue;
	private JFreeChart chart;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int COUNT = 50;
	private final int RANGE = 1000;
	private int max = 0, min = 0; // 그래프 최대, 최소치 담기 위한 변수

	public Comm_IssueGraph() {
		setSize(600, 550);
		setBackground(new Color(64, 64, 64));
		init();
	}

	private void accDB() { // DB
		try {
			prpt.load(new FileInputStream(Etc_Commons.DB_PROPERTIES));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));
		} catch (Exception e) {
			System.out.println("accDB Err : " + e);
		}
	}

	private void init() {
		// Top Panel(pnl1) : ComboBox, Button
		JPanel pnl1 = new JPanel();
		pnl1.setLayout(new GridLayout(2, 1));
		pnl1.setBackground(new Color(64, 64, 64));
		JPanel pnl = new JPanel();
		pnl.setBackground(new Color(64, 64, 64));
		tf_issue = new JComboBox<>();
		tf_issue.setBackground(Color.WHITE);
		tf_issue.setForeground(Color.GRAY);
		tf_issue.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		db_StockIssue(); // 주가종목
		btn_search = new JButton("Search");
		btn_search.setBackground(Color.BLACK);
		btn_search.setForeground(Color.WHITE);
		btn_search.setFont(new Font("Trajan Pro", Font.BOLD, 12));

		lbl_issue = new JLabel();
		lbl_issue.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		lbl_issue.setForeground(Color.WHITE);
		pnl.add(tf_issue);
		pnl.add(btn_search);
		pnl1.add(pnl);
		pnl1.add(lbl_issue);

		// Middle Panel(pnl2) : chart, pnl3
		JPanel pnl2 = new JPanel();
		pnl2.setBackground(new Color(64, 64, 64));
		pnl_chart = new JPanel();
		lbl_issue.setHorizontalAlignment(JLabel.CENTER);

		// -- chart
		chart = ChartFactory.createLineChart("", "Time", "Price", createDataset(), PlotOrientation.VERTICAL, true, true,
				false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setRange(min, max);

		pnl_chart.setLayout(new BorderLayout());
		pnl_chart.setPreferredSize(new java.awt.Dimension(500, 350));
		pnl_chart.add(new ChartPanel(chart));
		pnl2.add(pnl_chart);

		JPanel pnl3 = new JPanel();
		pnl3.setBackground(new Color(64, 64, 64));
		btn_close = new JButton("Close");
		btn_close.setFont(new Font("Trajan Pro", Font.PLAIN, 14));
		btn_close.setBackground(Color.BLACK);
		btn_close.setForeground(Color.WHITE);
		pnl3.add(btn_close);

		// Panel Merge : pnl1, pnl2
		add("North", pnl1);
		add("Center", pnl2);
		add("South", pnl3);

		listener(); // Listener 연결
	}

	private void listener() {
		btn_search.addActionListener(this);
		btn_close.addActionListener(this);
	}

	private DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		accDB(); // DB연결
		try {
			max = 0;
			min = 0;
			boolean isFirstData = true;
			String sql = " select rownum, s_name, d_money, d_hour "
					+ " from (select s_name, d_money, d_hour from b_vdata "
					+ " left outer join b_stock on d_code = s_code where s_name like ? order by d_hour desc) "
					+ " where rownum <= ?";
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, (String) tf_issue.getSelectedItem() + "%");
			pstmt.setInt(2, COUNT);
			rs = pstmt.executeQuery();
			rs.last();
			rs.next();
			while (rs.previous()) {
				if (isFirstData) { // 첫번째 데이터를 minmax의 기준값으로 설정
					max = rs.getInt("d_money");
					min = max;
					isFirstData = false;
				}
				if (min > rs.getInt("d_money")) {
					min = rs.getInt("d_money");
				}
				if (max < rs.getInt("d_money")) {
					max = rs.getInt("d_money");
				}
				dataset.addValue(rs.getFloat("d_money"), rs.getString("s_name"), rs.getString("d_hour").substring(14));
				lbl_issue.setText(" " + rs.getString("s_name") + " : " + df.format(rs.getFloat("d_money")) + " ("
						+ rs.getString("d_hour").substring(0, 10) + ")");
			}
			// 최대값 최소값 설정 (여유값으로 range 값 지정)
			if (min == max) {
				min = min - (RANGE * 2);
				max = max + (RANGE * 2);
			} else {
				max = max + RANGE;
				min = min - RANGE;
			}
			System.out.println("max : " + max + ", min : " + min);
		} catch (Exception e2) {
			System.out.println("createDataset() db Read Err : " + e2);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				System.out.println("createDataset() close Err : " + e2);
			}
		}
		return dataset;
	}

	private void db_StockIssue() {
		accDB(); // DB연결
		try {
			String sql = "select s_name from b_vstock where s_code like ? order by s_code";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "B%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tf_issue.addItem(rs.getString("s_name"));
			}
		} catch (Exception e) {
			System.out.println("db_StockIssue Err : " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				System.out.println("db_StockIssue close Err : " + e2);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_close) {
			try {
				Customer_Main.Issue.setEnabled(true);
				Customer_Main.childIssue.dispose();				
			} catch (Exception e2) {
				Manager_Main.Issue.setEnabled(true);
				Manager_Main.childIssue.dispose();
			}
		} else if (e.getSource() == btn_search) {
			pnl_chart.removeAll();
			createDataset();
			chart = ChartFactory.createLineChart("", "Time", "Price", createDataset(), PlotOrientation.VERTICAL, true,
					true, false);
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setRange(min, max);
			pnl_chart.add(new ChartPanel(chart));
		}
	}

	// public static void main(String[] args) {
	// Comm_IssueGraph Issue = new Comm_IssueGraph();
	//
	// JFrame frame = new JFrame("종목별 현황");
	// frame.getContentPane().add(Issue);
	//// frame.setBounds(440, 110,600, 500);
	// frame.setBounds(440, 110,1000, 420);
	// frame.setVisible(true);
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// }
}