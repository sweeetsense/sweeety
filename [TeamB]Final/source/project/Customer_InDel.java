package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Customer_InDel extends JPanel implements ActionListener{
	/**
	 * 고객 전용 : 자산현황 관리
	 */
	private static final long serialVersionUID = 1L;
	private Object [][] datas = new String[0][9];
	private String [] title = {"code","종목이름","현재가격", "매수가격","보유수","총매수가격","총평가가격","평가손익"};
	private DefaultTableModel model;	
	private JTable table;
	private String sql,sql2,port_code;
	private JButton btn1,btn2,btn3,btn4;
	private Properties prpt = new Properties();
	private Connection conn;
	private PreparedStatement pstmt, pstmt2;
	private ResultSet rs, rs2;
	private int code;

	
	public Customer_InDel(int code) {
		this.code = code;
//		setTitle("내 자산현황");
		setSize(600,550);
		design();
		display();
		setBackground(new Color(64, 64, 64));
		setVisible(true);
	}
	
	private void accDb() {
		try {
			prpt.load(new FileInputStream(Etc_Commons.DB_PROPERTIES));
	         Class.forName(prpt.getProperty("driver"));
	         conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
	               prpt.getProperty("passwd"));

		} catch (Exception e) {
			System.out.println("accDb 연결 실패:" + e);
		}
	}
	
	private void design(){
		model = new DefaultTableModel(datas, title) { 
	         /**
			 * table model : 테이블 내용 수정 불가
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
	            return false;
	         }
	      };;
		table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
	
		DefaultTableCellRenderer dtcr_r = new DefaultTableCellRenderer();
		dtcr_r.setHorizontalAlignment(SwingConstants.RIGHT);
		table.getColumnModel().getColumn(2).setCellRenderer(dtcr_r);
		table.getColumnModel().getColumn(3).setCellRenderer(dtcr_r);
		table.getColumnModel().getColumn(4).setCellRenderer(dtcr_r);
		table.getColumnModel().getColumn(5).setCellRenderer(dtcr_r);
		table.getColumnModel().getColumn(6).setCellRenderer(dtcr_r);
		
		add("Center",pane);
		btn1 = new JButton("Add");
		btn1.setBackground(Color.BLACK);		
		btn1.setForeground(Color.WHITE);	
		btn1.setFont(new Font("Trajan Pro", Font.BOLD, 14));
		btn2 = new JButton("Delete");
		btn2.setBackground(Color.BLACK);		
		btn2.setForeground(Color.WHITE);	
		btn2.setFont(new Font("Trajan Pro", Font.BOLD, 14));
		btn3 = new JButton("Reflash");
		btn3.setBackground(Color.BLACK);		
		btn3.setForeground(Color.WHITE);	
		btn3.setFont(new Font("Trajan Pro", Font.BOLD, 14));
		btn4 = new JButton("Close");
		btn4.setBackground(Color.BLACK);		
		btn4.setForeground(Color.WHITE);	
		btn4.setFont(new Font("Trajan Pro", Font.BOLD, 14));
		
		JPanel pn1 = new JPanel();
		pn1.setBackground(new Color(64, 64, 64));
		pn1.add(btn1);
		pn1.add(btn2);
		pn1.add(btn3);
		pn1.add(btn4);
		add("South", pn1);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
	}
	
	private void display(){
		accDb();
		DecimalFormat df = new DecimalFormat("#,###");
		try {
			model.setNumRows(0);
			table.getColumn("code").setWidth(0);
			table.getColumn("code").setMinWidth(0);
			table.getColumn("code").setMaxWidth(0);

			sql = "select s_code,s_name,round(pf_buyprice) as pf_buy,pf_number,pf_code from b_vportfolio join b_vstock on s_code = pf_scode where pf_ccode = ?";
			sql2 = "select round(d_money) as money from b_vdata where d_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();
			while(rs.next()){
				pstmt2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				pstmt2.setString(1, rs.getString("s_code"));
				rs2 = pstmt2.executeQuery();
				rs2.last();
				String pf_code = rs.getString("pf_code");
				String s_name = rs.getString("s_name");
				int d_money = Integer.parseInt(rs2.getString("money"));
				int pf_buyprice = Integer.parseInt(rs.getString("pf_buy"));
				String pf_number = rs.getString("pf_number");
				int pb = pf_buyprice;
				int pn = Integer.parseInt(pf_number);				
				int re = (pb*pn);
				int now = (d_money * pn);
				String result =df.format(Math.abs(now - re));
				if(now - re < 0){
					result = "-" + result;
				}else if(now - re > 0){
					result = "+" + result;					
				}
				String[] imsi = {pf_code,s_name,df.format(d_money)+"원",df.format(pf_buyprice)+"원",pf_number+"주",
						df.format(re)+"원",df.format(now)+"원",result};
				model.addRow(imsi);
			}
			TableCellRenderer renderer = new Etc_TableColorCellRenderer();
			table.getColumnModel().getColumn(7).setCellRenderer(renderer);
			delete();
		} catch (Exception e) {
			System.out.println("InDel display : " + e);
		}
	}

	private void delete(){
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					e.getSource();
				
					table = (JTable) e.getComponent();
					model = (DefaultTableModel) table.getModel();
					port_code = (String)model.getValueAt(table.getSelectedRow(), 0);
				
					
				} catch (Exception e2) {
					System.out.println("mouse : " + e2);
				}

			}
			
			
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btn1)){ //추가
			new Customer_InsertStock(code);
		}else if(e.getSource().equals(btn2)){ //삭제
			if(port_code==null){
				JOptionPane.showMessageDialog(this, "삭제할 주식종목을 선택하세요");
				return;
			}
			int oo = JOptionPane.showConfirmDialog(this, "정말 삭제 하시겠습니까?","삭제", JOptionPane.YES_NO_OPTION);
			if(oo == JOptionPane.YES_OPTION){
				try {
					conn.setAutoCommit(false);
					sql = "delete b_vportfolio where pf_code = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, port_code);
					pstmt.executeUpdate();
					conn.commit();
					JOptionPane.showMessageDialog(this, "삭제가 완료되었습니다.");
					model.setRowCount(0);
					display();
				} catch (Exception e2) {
					System.out.println("delete : " + e2);
					try {
						conn.rollback();
					} catch (Exception e3) {
						// TODO: handle exception
					}
				} finally {
					try {
						conn.setAutoCommit(true);						
					} catch (Exception e3) {
						// TODO: handle exception
					}
				}
			}else{
				return;
			}

		}else if(e.getSource().equals(btn3)){ //새로고침
			model.setRowCount(0);
			display();
		}else if(e.getSource().equals(btn4)){ //닫기
			Customer_Main.AssetStatus.setEnabled(true);
			Customer_Main.childAssetStatus.dispose();
		}
	}

}
