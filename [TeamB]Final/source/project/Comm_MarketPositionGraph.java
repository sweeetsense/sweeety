package project;

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
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import project.Customer_Main;
import project.Manager_Main;

public class Comm_MarketPositionGraph extends JPanel implements ActionListener {
	/**
	 * 주식시장 정보 그래프
	 */
	private static final long serialVersionUID = 1L;
	private Properties prpt = new Properties();
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private JButton btnSearch, btnClose;
	private JLabel lblPrice;
	private JPanel pn2 = new JPanel(new GridLayout(1, 2));

	private JComboBox<String> cb;
	private JFreeChart chart;
	private float avg = 0.0f;
	private int max = (int) avg, min = (int) avg;
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	public Comm_MarketPositionGraph() {
		accDb();
		layInit();
		btnSearch.doClick();
		setSize(530, 500);
	}

	private void layInit() {

		JPanel pn1 = new JPanel(new GridLayout(2, 1));
		setBackground(new Color(64, 64, 64));
		pn1.setBackground(new Color(64, 64, 64));
		pn2.setBackground(new Color(64, 64, 64));
		JPanel pn11 = new JPanel();
		pn11.setBackground(new Color(64, 64, 64));
		cb = new JComboBox<>();
		cb.setFont(new Font("Trajan Pro", Font.BOLD, 14));
		cb.setBackground(Color.WHITE);
		cb.setForeground(Color.BLACK);
		db_Market();
		btnSearch = new JButton("Search");
		btnSearch.setBackground(Color.BLACK);
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		pn11.add(cb);
		pn11.add(btnSearch);
		pn1.add("North",pn11);

		btnSearch.addActionListener(this);
		JPanel pn12 = new JPanel();
		pn12.setBackground(new Color(64, 64, 64));
		lblPrice = new JLabel("", 10);
		lblPrice.setBackground(new Color(64, 64, 64));
		lblPrice.setOpaque(true);
		lblPrice.setForeground(Color.WHITE);
		lblPrice.setFont(new Font("Trajan Pro", Font.BOLD, 20));
		lblPrice.setHorizontalAlignment(JLabel.CENTER);
		pn12.add(lblPrice);
		pn1.add(pn12);
		add("North", pn1);

		chart = ChartFactory.createLineChart("", "Time", "Price", createDataset(), PlotOrientation.VERTICAL,
				true, true, false);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		ChartPanel cpn = new ChartPanel(chart);
		cpn.setPreferredSize(new java.awt.Dimension(560, 367));

		add("Center", pn2);

		JPanel pn3 = new JPanel();
		pn3.setBackground(new Color(64, 64, 64));
		btnClose = new JButton("Close");
		btnClose.setBackground(Color.BLACK);
		btnClose.setForeground(Color.WHITE);
		btnClose.setFont(new Font("Trajan Pro", Font.BOLD, 12));
		pn3.add(btnClose);
		add("South", pn3);
		btnClose.addActionListener(this);
	}

	private void db_Market() {
		accDb();
		try {
			String sql = "select s_name from b_stock where s_code like ? order by s_code";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "A%"); // s_code가 A로 시작하는 레코드 조회
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 콤보박스에 종목이름 넣기
				cb.addItem(rs.getString("s_name"));
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
			}
		}
	}

	private DefaultCategoryDataset createDataset() {
		accDb();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			String sql = "select s_name, d_money, d_hour, avg(d_money) as avg from b_data join b_stock on d_code = s_code "
					+ " where s_name like ? and rownum <= 100 group by s_name, d_money, d_hour order by d_hour desc";
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, (String) cb.getSelectedItem());
			rs = pstmt.executeQuery();
			if (rs.first()) {
				// lblPrice.setText(rs.getString("s_name") + ": " +
				// rs.getFloat("d_money"));
				lblPrice.setText("   " + rs.getString("s_name") + ": " + rs.getFloat("d_money") + " ("
						+ rs.getString("d_hour").substring(0, 10) + ")");
				rs.last();
			}
			float a = 0, b = 10000000000000.0f;
			while (rs.previous()) {
				dataset.addValue(rs.getFloat("d_money"), rs.getString("s_name"), rs.getString("d_hour").substring(14));
				if (rs.getFloat("d_money") > a) {
					a = rs.getFloat("d_money");
				} else {
					b = rs.getFloat("d_money");
				}
				avg = rs.getFloat("avg");
			}
			if ((a - avg) > 20) {
				max = (int) a;
				min = (int) b;
			} else {
				max = (int) avg;
				min = (int) avg;
			}
		} catch (Exception e) {
			System.out.println("dataset err: " + e);
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
		lblPrice.setHorizontalAlignment(JLabel.CENTER);
		return dataset;
	}

	private void accDb() {
		try {
			prpt.load(new FileInputStream("C:/work/project.properties"));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));

		} catch (Exception e) {
			System.out.println("accDb err: " + e);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnClose) {
			try {
				Customer_Main.Market.setEnabled(true);
				Customer_Main.childMarket.dispose();	
			} catch (Exception e2) {
				Manager_Main.Market.setEnabled(true);
				Manager_Main.childMarket.dispose();
			}
		} else {
			pn2.removeAll();
			createDataset();
			chart = ChartFactory.createLineChart("", "Time", "Price", createDataset(), PlotOrientation.VERTICAL,
					true, true, false);
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setRange(min - 10, max + 10);

			ChartPanel cpn = new ChartPanel(chart);
			cpn.setPreferredSize(new java.awt.Dimension(560, 367));
			pn2.add(cpn);
		}
	}

	public static void main(String[] args) {
		Comm_MarketPositionGraph market = new Comm_MarketPositionGraph();
		JFrame frame = new JFrame("Market Graph");
		frame.getContentPane().add(market);
		frame.setBounds(220, 110, 580, 550);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}