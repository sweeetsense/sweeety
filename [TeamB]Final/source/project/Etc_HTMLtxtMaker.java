package project;

import java.text.DecimalFormat;

/**
 * 
 * @author Seol
 * 포트폴리오 전송시 HTML양식 만드는 클래스
 *
 */
public class Etc_HTMLtxtMaker {
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	public String makeHTMLtxt(String cName, Object[][] cStock_info, String mName, String mEmail) {
		String sHtml_top = "<html><body>";
		String sHtml_mid = "";
		String sHtml_mid1 = "";
		String sHtml_mid2 = "";
		String sHtml_bot = "</body></html>";

		sHtml_mid = "<p><span style=\"font-family:Arial,Helvetica,sans-serif\">" + cName + "님의 " + Etc_Commons.YEAR + "년 "
				+ Etc_Commons.MONTH + "월 " + Etc_Commons.DAY + "일 자산가치 변동 현황리포트</span></p>"
				+ "<table border=\"1\" cellpadding=\"0\" cellspacing=\"1\" style=\"width:800px\">" 
				+ "<thead>" 
				+ "<tr>"
				+ "<th scope=\"col\">" 
				+ "<p><span style=\"font-family:Arial,Helvetica,sans-serif\">번호</span></p>"
				+ "</th>" 
				+ "<th scope=\"col\"><span style=\"font-family:Arial,Helvetica,sans-serif\">종목</span></th>"
				+ "<th scope=\"col\"><span style=\"font-family:Arial,Helvetica,sans-serif\">현재가</span></th>"
				+ "<th scope=\"col\"><span style=\"font-family:Arial,Helvetica,sans-serif\">보유량</span></th>"
				+ "<th scope=\"col\"><span style=\"font-family:Arial,Helvetica,sans-serif\">종목변동</span></th>"
				+ "<th scope=\"col\"><span style=\"font-family:Arial,Helvetica,sans-serif\">자산변동</span></th>"
				+ "<th scope=\"col\"><span style=\"font-family:Arial,Helvetica,sans-serif\">자산 총평가</span></th>"
				+ "</tr>" 
				+ "</thead>" 
				+ "<tbody>";
		for (int i = 0; i < cStock_info.length; i++) {
			sHtml_mid1 += "<tr><td style=\"text-align: center;\">" + cStock_info[i][0] + "</td>" 
						+ "<td>" + cStock_info[i][1] + "</td>" 
						+ "<td style=\"text-align: right;\">" + df.format(cStock_info[i][2]) + "원</td>"
						+ "<td style=\"text-align: center;\">" + cStock_info[i][3] + "</td>"
						+ "<td style=\"text-align: right;\">" + df.format(cStock_info[i][4]) + "원</td>"
						+ "<td style=\"text-align: right;\">" + df.format(cStock_info[i][5]) + "원</td>"
						+ "<td style=\"text-align: right;\">" + df.format(cStock_info[i][6]) + "원</td></tr>";
		}
		sHtml_mid2 = "</tbody></table>"
				+ "<p>상담이 필요하시면 <a href=\"mailto:" + mEmail + "\">" + mEmail + "</a>으로 문의주시기 바랍니다.</p>"
				+ "<p><span style=\"font-family:Arial,Helvetica,sans-serif\">자산관리사 " + mName + " 드림.</span></p>"
				+ "<p>&nbsp;</p>";

		return sHtml_top + sHtml_mid + sHtml_mid1 + sHtml_mid2 + sHtml_bot;

	}
}
